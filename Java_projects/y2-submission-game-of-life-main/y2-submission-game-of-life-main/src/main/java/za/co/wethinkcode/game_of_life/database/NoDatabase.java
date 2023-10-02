package za.co.wethinkcode.game_of_life.database;
import za.co.wethinkcode.game_of_life.domain.World;
import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class NoDatabase implements GameDB {

    @Override
    public World create(World world) throws SQLException {

        final String CONN = "jdbc:sqlite:GameNoLife.db";

        String sql2 = "INSERT INTO NoGame (\n" +
                "                      name,\n" +
                "                      size,\n" +
                "                      state\n" +
                "                          )\n" +
                "                   VALUES (\n" +
                "                         ?,\n" +
                "                         ?,\n" +
                "                         ?\n" +
                "                           )";

        try (Connection conn = connect();
             PreparedStatement preSTMT = conn.prepareStatement(sql2)) {
            preSTMT.setString(1, world.getWorldName());
            preSTMT.setInt(2, world.getWorldSize());
            preSTMT.setString(3, Arrays.deepToString(world.getState()));

            preSTMT.execute();
            System.out.println("The event now exists");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return world;
    }



    @Override
    public List<World> all() {
        return null;
    }

    private static final String CONN = "jdbc:sqlite:GameNoLife.db";

    private static Connection connect() {

        Connection conn = null;
        if (isSuitableDriverAvailable()) {
            try {
                conn = DriverManager.getConnection(CONN);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("The driver was not correctly loaded and execution was aborted");
        }
        return conn;
    }

    private static boolean isSuitableDriverAvailable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            return false;
        }

        return true;
    }

//    public static void create(World world) {
//
//        String sql2 = "INSERT INTO [world] (\n" +
//                "                      name,\n" +
//                "                      size,\n" +
//                "                      state,\n" +
//                "                          )\n" +
//                "                   VALUES (\n" +
//                "                         ?,\n" +
//                "                         ?,\n" +
//                "                         ?,\n" +
//                "                           );";
//
//        try (Connection conn = connect();
//             PreparedStatement preSTMT = conn.prepareStatement(sql2)) {
//            preSTMT.setString(1, world.getWorldName());
//            preSTMT.setInt(2, world.getWorldSize());
//            preSTMT.setString(3, Arrays.deepToString(world.getState()));
//
//            preSTMT.execute();
//            System.out.println("Message to scream help");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
    @Override
    public World get(Integer id) throws SQLException {
        World newID = new World();
        Connection connect = connect();
        Statement statement = null;
        String sql = "SELECT * FROM NoGame WHERE '" + id + "' = id";

        try {
            statement = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet results = statement.executeQuery(sql);

        while (results.next()) {
            System.out.println("Rummaging through the notes...");
            if (id == results.getInt("id")) {
                newID.setWorldSize(results.getInt("id"));
                newID.setArray(results.getInt("state"));
                newID.setWorldName(results.getString("name"));
                return newID;
            }
        }
        return newID;
    }
}