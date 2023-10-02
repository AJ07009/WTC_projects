package za.co.wethinkcode.linklists.words;

import java.sql.SQLException;
import java.util.ArrayList;

public class WordImp implements WordUrn{
    private ArrayList<String> words;
    public void Word(ArrayList<String> fixture){
        words = fixture;
    }

    @Override
    public WordsModel get(String id) throws SQLException {
        return null;
    }

    @Override
    public String giveWord() {
        if (words.size() > 0) {
            String top = words.get(words.size() - 1);
            words.remove(words.size() - 1);
            return top;
        }
        else return null;
    }
}
