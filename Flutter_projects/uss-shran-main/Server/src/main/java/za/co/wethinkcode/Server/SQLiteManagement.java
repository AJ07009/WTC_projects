package za.co.wethinkcode.Server;

import java.sql.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import za.co.wethinkcode.World;
import za.co.wethinkcode.Map.Obstacle;
import za.co.wethinkcode.Map.SquareObstacle;
import za.co.wethinkcode.Robot.Position;

public class SQLiteManagement {

    private static final String CONN = "jdbc:sqlite:RobotWorldsDB.db";

    private static Connection connect() {

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

    public static ResultSet resultSetWorldID(String name){
        ResultSet result = null;
        Connection conn = connect();
        try {
            Statement stmt = conn.createStatement();
            String getWorld = "SELECT * FROM 'Robot Worlds' WHERE WorldID='" + name + "'";
            ResultSet rs = stmt.executeQuery(getWorld);
            result = rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void insert(String worldID, String bottomRight, String topLeft, String maze, Integer maxShots, Integer maxShields, Integer reloadTime,Integer repairTime, Integer visibility, Integer mineSetTime) {

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
        try (Connection conn = connect();
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
            preSTMT.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }



    public static Boolean checkWorldID(String ID) {
        String sql = "SELECT * FROM [Robot Worlds]";
        List<String> worldList = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                worldList.add(rs.getString("WorldID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return worldList.contains(ID);
    }

    public static List<Map<String, String>>listWorlds(){
        String sql = "SELECT * FROM [Robot Worlds]";
        List<Map<String, String>> worldList = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                Map<String,String> worldData = new HashMap<>();
//                worldList.putIfAbsent(rs.getString("WorldID"), new HashMap<>());
                String bottomRight = rs.getString("BottomRight");
                String topLeft = rs.getString("TopLeft");
                int maxShields = rs.getInt("MaxShields");
                int maxShots = rs.getInt("MaxShots");
                int reloadTime = rs.getInt("ReloadTime");
                int repairTime = rs.getInt("RepairTime");
                int visibility = rs.getInt("Visibility");
                int mineSetTime = rs.getInt("MineSetTime");
                String maze = rs.getString("Maze");
                worldData.putIfAbsent("WorldID", rs.getString("WorldID"));
                worldData.putIfAbsent("BottomRight", bottomRight);
                worldData.putIfAbsent("TopLeft", topLeft);
                worldData.putIfAbsent("MaxShields", String.valueOf(maxShields));
                worldData.putIfAbsent("MaxShots", String.valueOf(maxShots));
                worldData.putIfAbsent("ReloadTime", String.valueOf(reloadTime));
                worldData.putIfAbsent("RepairTime", String.valueOf(repairTime));
                worldData.putIfAbsent("Visibility", String.valueOf(visibility));
                worldData.putIfAbsent("MineSetTime", String.valueOf(mineSetTime));
                worldData.putIfAbsent("Maze", maze);
                worldList.add(worldData);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return worldList;
    }

    private static boolean isSuitableDriverAvailable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException ex) {
            return false;
        }

        return true;
    }

    public static void restoreData(World world, String name) {
        try (
                Connection conn = connect();
                Statement stmt = conn.createStatement();
        ){
            if(checkWorldID(name)) {
                String getWorld = "SELECT * FROM 'Robot Worlds' WHERE WorldID='" + name + "'";
                ResultSet rs = stmt.executeQuery(getWorld);
                while(rs.next()) {
                    getAndSetData(rs, world);
                }
            } else {
                restoreData(world);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void restoreData(World world) {
        String defaultWorld = "SELECT * FROM 'Robot Worlds' WHERE WorldID='World01'";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(defaultWorld);
                ) {
            while (rs.next()) {
                getAndSetData(rs, world);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getAndSetData(ResultSet rs, World world) {
        JSONParser parser = new JSONParser();
        try {
            Position bottomRight = createPosition(rs.getString("BottomRight"));
            Position topLeft = createPosition(rs.getString("TopLeft"));
            int maxShields = rs.getInt("MaxShields");
            int maxShots = rs.getInt("MaxShots");
            int reloadTime = rs.getInt("ReloadTime");
            int repairTime = rs.getInt("RepairTime");
            int visibility = rs.getInt("Visibility");
            int mineSetTime = rs.getInt("MineSetTime");

            JSONObject maze = (JSONObject) parser.parse(rs.getString("Maze"));
            Vector<Obstacle> obstacleList = getObstacles((JSONArray) maze.get("obstaclesList"));

            world.changeWorldConfig(bottomRight, topLeft, maxShields, maxShots, reloadTime, repairTime, visibility,
                    mineSetTime, obstacleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Vector<Obstacle> getObstacles(JSONArray obstacleList) {
        Vector<Obstacle> obs = new Vector<>();
        for (int i = 0; i < obstacleList.size(); i++) {
            JSONObject obstacle = (JSONObject) obstacleList.get(i);
            Position obsPosition = new Position(((Long) obstacle.get("bottomLeftX")).intValue(),
                    ((Long) obstacle.get("bottomLeftY")).intValue());
            obs.add(new SquareObstacle(obsPosition.getX(), obsPosition.getY()));
        }
        return obs;
    }

    private static Position createPosition(String positionString) {
        return new Position(Integer.parseInt(positionString.split(",")[0]),
        Integer.parseInt(positionString.split(",")[1]));
    }
}