package za.co.wethinkcode.linklists;

import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.http.Context;
import za.co.wethinkcode.linklists.remix.Remixer;
import za.co.wethinkcode.linklists.words.RecieveWords;
import za.co.wethinkcode.linklists.words.WordImp;
import za.co.wethinkcode.linklists.words.WordUrn;
import za.co.wethinkcode.linklists.words.WordsModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ListApiHandler {
    private static final RecieveWords wordsdbSql = new RecieveWords();

    public static <Word> void remix(Context context) throws SQLException, ClassNotFoundException {
        ArrayList<String> wordList = wordsdbSql.getAll();
        WordImp wordUrn = new WordImp(wordList);
        Remixer remixer = new Remixer((WordUrn) wordUrn);

        JsonNode json = context.bodyAsClass(JsonNode.class);
        WordsModel link = WordsModel.create(json.get("long").textValue(),
                json.get("max_words").intValue());
        link.setShort_word(remixer.shorten(link.getLong_word(), link.getMax_words()));

        System.out.println(link.getLong_word());
        System.out.println(link.getShort_word());
        context.status(201);

        Map response = new HashMap();
        response.put("long", link.getLong_word());
        response.put("short", link.getShort_word());
        wordsdbSql.insert(link.getLong_word(), link.getShort_word());
        context.json(response);

    }
}
