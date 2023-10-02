package za.co.wethinkcode.game_of_life;

import io.javalin.http.Context;
import kong.unirest.HttpStatus;
import kong.unirest.json.JSONArray;
import za.co.wethinkcode.game_of_life.database.NoDatabase;
import za.co.wethinkcode.game_of_life.domain.AllGameAllLife;
import za.co.wethinkcode.game_of_life.domain.World;
import za.co.wethinkcode.game_of_life.http.requests.CreateRequest;
import za.co.wethinkcode.game_of_life.http.responses.WorldResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorldApiHandler {

    public void createNew(Context context) throws SQLException {
        CreateRequest createRequest = context.bodyAsClass(CreateRequest.class);
        World world = World.define(createRequest.getWorldName(), createRequest.getWorldSize(), createRequest.getWorldInitialState());

        NoDatabase noDatabase = new NoDatabase();
        context.status(HttpStatus.CREATED);
        noDatabase.create(world);
        context.json(new WorldResponse(1, world.getState()));
    }

    public void equation(Context context) throws SQLException {
        AllGameAllLife equa = context.bodyAsClass(AllGameAllLife.class);
        World world =
    }

    public int[][] setArray(String s){

        String[] rows = s.split("], \\[");
        for (int i = 0; i < rows.length; i++) {

            rows[i] = rows[i].replace("[[", "").replace("]]", "").replaceAll(" ", "");
        }


        int numberOfColumns = rows[0].split(",").length;


        String[][] matrix = new String[rows.length][numberOfColumns];


        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }

        int[][] stringToInt = new int[matrix.length][matrix[0].length];

        for (int a = 0; a < matrix.length; a++) {
            for (int b = 0; b < matrix[0].length; b++) {
                stringToInt[a][b] = Integer.parseInt(matrix[a][b]);
            }
        }

        return stringToInt;
    }

}
// <SOLUTION>
