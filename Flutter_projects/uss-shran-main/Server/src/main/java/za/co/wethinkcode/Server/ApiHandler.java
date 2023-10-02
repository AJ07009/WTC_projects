package za.co.wethinkcode.Server;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import za.co.wethinkcode.Commands.Command;
import za.co.wethinkcode.Map.Obstacle;
import za.co.wethinkcode.Map.SquareObstacle;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ApiHandler {

    public static void getOne(Context context, World world) {
        String id = context.pathParamAsClass("WorldID", String.class).get();
        if (!SQLiteManagement.checkWorldID(id)) {
            throw new NotFoundResponse("World not found: " + id);
        }
        Gson gson = new Gson();
        SQLiteManagement.restoreData(world, id);
        Map<String, List<String>> restoredWorld = new HashMap<>();

        restoredWorld.putIfAbsent("World Data", new ArrayList<>());
        restoredWorld.get("World Data").add(world.getTOP_LEFT().toString());
        restoredWorld.get("World Data").add(world.getBOTTOM_RIGHT().toString());
        restoredWorld.get("World Data").add(String.valueOf(gson.toJson(world.maze)));
        for (String name: world.getRobots().keySet()) {
            world.removeRobot(name);
        }
        context.json(restoredWorld);
    }

    public static void saveWorld(Context context, World world){
        Gson gson = new Gson();
        String worldID = context.body();
        worldID = worldID.replace("World", "");
        worldID = worldID.replace("\"", "");
        worldID = worldID.replace("{", "");
        worldID = worldID.replace("}", "");
        worldID = worldID.replace(":", "");
        worldID = worldID.replace(" ", "");

        SQLiteManagement.insert(
                worldID,
                world.BOTTOM_RIGHT.toString(),
                world.TOP_LEFT.toString(),
                String.valueOf(gson.toJson(world.maze)),
                world.getMAX_SHOTS(),
                world.getMAX_SHIELDS(),
                world.getRELOAD_TIME(),
                world.getREPAIR_TIME(),
                world.getVISIBILITY(),
                world.getMINE_SET_TIME());
    }

    public static void displayWorlds(Context context){
        List<Map<String, String>> databaseWorlds = SQLiteManagement.listWorlds();
        context.json(databaseWorlds);
    }

    public static void getWorld(Context context, World world) {
        Gson gson = new Gson();
        List<Map<String,Object>> worldProperties = new ArrayList<>();
        Map<String, Object> something = new HashMap<>();
        something.putIfAbsent("TopLeft", world.getTOP_LEFT().toString());
        something.putIfAbsent("BottomRight", world.getBOTTOM_RIGHT().toString());
        something.putIfAbsent("Maze", String.valueOf(gson.toJson(world.maze)));
        worldProperties.add(something);
        context.json(worldProperties);
    }

    public static void  purgeRobot(Context context, World world){
        System.out.println(context.body().getClass());
        System.out.println(context.body());
        String rob = context.body();
        String newrob = rob.replace("name", "");
        newrob = newrob.replace("\"", "");
        newrob = newrob.replace("{", "");
        newrob = newrob.replace("}", "");
        newrob = newrob.replace(":", "");
        newrob = newrob.replace(" ", "");

        System.out.println(newrob);
        System.out.println(world.getRobots());
        for (String name: world.getRobots().keySet()) {
            if (name.equals(newrob)){
                world.removeRobot(name);
            }
        }
        System.out.println(world.getRobots());
    }

    public static void getRobot(Context context, World world){
        String name = context.pathParamAsClass("name", String.class).get();
        Robot robot = world.getRobot(name);

        Map<String, Object> robotJson = new HashMap<>();
        robotJson.putIfAbsent("name", robot.getName());
        robotJson.putIfAbsent("position", robot.getPosition().getX() +"," + robot.getPosition().getY());
        robotJson.putIfAbsent("status", robot.getStatus());
        robotJson.putIfAbsent("direction", robot.getCurrentDirection().toString());
        context.json(robotJson);
    }

    public static void getRobots(Context context, World world) {
        // robots: {name: ,pos: ,status:}, {name: ,pos:, status:}\
        List<Map<String, Object>> robots = new ArrayList<>();
        for ( String key : world.getRobots().keySet() ) {
            Map<String, Object> something = new HashMap<>();
            something.putIfAbsent("name", world.getRobot(key).getName());
            something.putIfAbsent("position", world.getRobot(key).getPosition().toString());
            something.putIfAbsent("status", world.getRobot(key).getStatus());
            something.putIfAbsent("direction", world.getRobot(key).getCurrentDirection());
            robots.add(something);

        }
        System.out.println(robots);
        context.json(robots);
    }

    public static void command(Context context, World world) {
        System.out.println(context.body());
        ResponseBuilder response;
        String name = context.pathParamAsClass("name", String.class).get();
        System.out.println(name);
        
        if (!requestAndLinkName(context.body(),name)) {
            response = new ResponseBuilder();
            String robotName = getRobotName(context.body());
            JSONObject data = new JSONObject();
            data.put("message", "The request name: " + name + " and the body robot name: " + robotName + " do not match.");
            response.addData(data);
            response.add("result", "ERROR");
        } else if (world.getRobot(name) == null) {
            Robot robot = new Robot(name);
            response = handleMessageBeforeLaunch(context.body(), world, robot,false);
        } else {
            Robot robot = world.getRobot(name);
            response = handleClientMessage(context.body(), world, robot,true);
        }
        context.json(response.toString());
    }

    private static String getRobotName(String body) {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(body);
        return jsonMessage.get("robot").toString();
    }

    private static boolean requestAndLinkName(String body,String name) {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(body);
        return jsonMessage.get("robot").toString().equals(name);
    }

    private static ResponseBuilder handleMessageBeforeLaunch(String messageFromClient, World world, Robot robot, boolean robotExists) {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(messageFromClient);
        System.out.println(jsonMessage);
        ResponseBuilder response;

        response = new ResponseBuilder();

        try {
            Command command = Command.create(jsonMessage,robotExists);
            response = world.handleCommand(command, robot);
            if (world.getRobot(robot.getName()) != null) response.add("state", robot.getState());
        }catch (IllegalArgumentException e) {
            JSONObject data = new JSONObject();
            data.put("message", e.getMessage());
            response.addData(data);
            response.add("result","ERROR");
        }
        return response;
    }

    private static ResponseBuilder handleClientMessage(String messageFromClient,World world, Robot robot,Boolean robotExists) {
        JSONObject jsonMessage = (JSONObject)JSONValue.parse(messageFromClient);

        ResponseBuilder response;

        response = new ResponseBuilder();

        try {
            Command command = Command.create(jsonMessage,robotExists);
            response = world.handleCommand(command, robot);
            if (response.getValue("result").equals("OK")) response.add("state", robot.getState());
            // response.add("state", robot.getRobotResponse());
        } catch (IllegalArgumentException e) {
            JSONObject data = new JSONObject();
            data.put("message", e.getMessage());
            response.addData(data);
            response.add("result","ERROR");
        }

        return response;
    }
    public static void getObstacles(Context context, World world) {
        List<Map<String, Object>> obs_new = new ArrayList<>();
        for ( Obstacle ob : world.getObstacles()) {
            Map<String, Object> obstaclesMap = new HashMap<>();
            obstaclesMap.putIfAbsent("bottomLeftX", ob.getBottomLeftX());
            obstaclesMap.putIfAbsent("bottomLeftY", ob.getBottomLeftY());
            obs_new.add(obstaclesMap);
        }
        System.out.println(obs_new);
        context.json(obs_new);

    }
    public static void addObstacle(Context context, World world) {
        String rob = context.body();
        System.out.println(rob);
        String newrob = rob.replace("Obstacle", "");
        newrob = newrob.replace("\"", "");
        newrob = newrob.replace("{", "");
        newrob = newrob.replace("}", "");
        newrob = newrob.replace(":", "");
        newrob = newrob.replace(" ", "");
        newrob = newrob.replace("[", "");
        newrob = newrob.replace("]", "");
        String[] pos = newrob.split(",");
        System.out.println(world.maze.getObstacles());
        System.out.println(pos[0]);
        System.out.println(pos[1]);
        // // get the posX and posY
        int posX = Integer.parseInt(pos[0]);
        int posY = Integer.parseInt(pos[1]);// set to position Y
        // // make a new obstacle object
        world.maze.createObstacles(new Position(posX, posY));
        System.out.println(world.maze.getObstacles());
        // // and add it to the obs list
        
    }

    public static void removingObstacle(Context context, World world) {
        String rob = context.body();
        System.out.println(rob);
        String newrob = rob.replace("Obstacle", "");
        newrob = newrob.replace("\"", "");
        newrob = newrob.replace("{", "");
        newrob = newrob.replace("}", "");
        newrob = newrob.replace(":", "");
        newrob = newrob.replace(" ", "");
        newrob = newrob.replace("[", "");
        newrob = newrob.replace("]", "");
        String[] pos = newrob.split(",");
        System.out.println(world.maze.getObstacles());
        System.out.println(pos[0]);
        System.out.println(pos[1]);
        // // get the posX and posY
        int posX = Integer.parseInt(pos[0]);
        int posY = Integer.parseInt(pos[1]);// set to position Y
        // // make a new obstacle object
        SquareObstacle ob = new SquareObstacle(posX, posY);
        world.getMaze().removeObstacle(ob);
    }
}
