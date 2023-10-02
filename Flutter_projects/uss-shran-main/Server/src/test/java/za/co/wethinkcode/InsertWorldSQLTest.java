package za.co.wethinkcode;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsertWorldSQLTest {

    @Test
    public void newWorldTest(){
        insertTest("WorldTest01", "10,-10", "-10,10", "{\"upperX\":10,\"upperY\":10,\"lowerX\":-10,\"lowerY\":-10,\"obstaclesList\":[{\"bottomLeftX\":1,\"bottomLeftY\":1}],\"pitsList\":[],\"minesList\":[]}",5,5,10,10,10,10);
        assertTrue(checkWorldIDTest("WorldTest01"));
    }

    private static final String CONN = "jdbc:sqlite:../RobotWorldsDB.db";

    private static Connection connectTest() {

        Connection conn = null;
        if(isSuitableDriverAvailable()) {
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

    public static void insertTest(String worldID, String bottomRight, String topLeft, String maze, Integer maxShots, Integer maxShields, Integer reloadTime,Integer repairTime, Integer visibility, Integer mineSetTime) {

        String sql2 = "INSERT INTO [Robot Worlds] (\n" +
                "                               WorldID,\n" +
                "                               BottomRight,\n" +
                "                               TopLeft,\n" +
                "                               Maze,\n" +
                "                               MaxShots,\n" +
                "                               MaxShields,\n" +
                "                               ReloadTime,\n" +
                "                               RepairTime,\n" +
                "                               Visibility,\n" +
                "                               MineSetTime\n" +
                "                           )\n" +
                "                           VALUES (\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?,\n" +
                "                               ?\n" +
                "                           );";

        try (Connection conn = connectTest();
             PreparedStatement preSTMT = conn.prepareStatement(sql2)){
            preSTMT.setString(1, worldID);
            preSTMT.setString(2, bottomRight);
            preSTMT.setString(3, topLeft);
            preSTMT.setString(4, maze);
            preSTMT.setString(5, String.valueOf(maxShots));
            preSTMT.setString(6, String.valueOf(maxShields));
            preSTMT.setString(7, String.valueOf(reloadTime));
            preSTMT.setString(8, String.valueOf(repairTime));
            preSTMT.setString(9, String.valueOf(visibility));
            preSTMT.setString(10, String.valueOf(mineSetTime));
//            preSTMT.execute();
            preSTMT.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static Boolean checkWorldIDTest(String ID){
        String sql = "SELECT * FROM [Robot Worlds]";
        List<String> worldList = new ArrayList<>();

        try (Connection conn = connectTest();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
//                System.out.println(rs.getString("WorldID"));
                worldList.add(rs.getString("WorldID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return worldList.contains(ID);
    }

    private static boolean isSuitableDriverAvailable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException ex) {
            return false;
        }

        return true;
    }
}
