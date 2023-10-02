package za.co.wethinkcode.Commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ConfigReader;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class LaunchCommand extends Command{
    public static ConfigReader config = new ConfigReader();
    JSONArray args;
    public LaunchCommand(JSONArray args) {
        super("launch");
        this.args = args;
    }

    /**
     * Method will create a new robot in the world with the given data.
     * The method will first check if the robot does not already exist within the world and if so the method returns
     * an error to the client.
     * If the arguments are valid the method will proceed to find a random open spot in the world and create the new
     * robot at that location in the world and store it in the clients connected thread.
     * @param world of the server.
     * @param server associated with the client.
     */
    @Override
    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();
        // Checks if the Robot exists already, then returns error if it is.
        if (!doesRobotExist(world, server) || server.robot != null) {
            server.robotName = null;
            data.put("message", "Too many of you in this world");
            server.response.addData(data);
            server.response.add("result", "ERROR");
            return;
        } else {
            // Create
            server.robot = new Robot(server.robotName);
            world.addRobot(server.robot);
            int maxShield = Math.min(Integer.parseInt(args.get(1).toString()), world.MAX_SHIELDS);
            int maxShot = Math.min(Integer.parseInt(args.get(2).toString()), world.MAX_SHOTS);
            server.robot.setMaxes(maxShield, maxShot);
        }

        Random random = new Random();

        boolean positionSet = false;

        if(world.maze.blocksPosition(world.getRobots(), new Position(0, 0), server.robotName) == UpdateResponse.SUCCESS) {
            server.robot.setPosition(new Position(0, 0));
            positionSet = true;
        }

        if(!positionSet) {
            for (int i = 0; i < 1000; i++) {
                int x = 0;
                int y = 0;
                int upperX = world.BOTTOM_RIGHT.getX() + 1;
                int lowerX = world.TOP_LEFT.getX();
                int upperY = world.TOP_LEFT.getY() + 1;
                int lowerY = world.BOTTOM_RIGHT.getY();
                if(world.BOTTOM_RIGHT.getX() != 0 || world.TOP_LEFT.getX() != 0) {
                    x = random.nextInt(upperX - lowerX) + lowerX;
                    y = random.nextInt(upperY - lowerY) + lowerY;
                }
                
                if (world.maze.blocksPosition(world.getRobots(), new Position(x, y), server.robotName) == UpdateResponse.SUCCESS){
                    server.robot.setPosition(new Position(x, y));
                    positionSet = true;
                    break;
                }
            }
        }
        
        if (!positionSet) {
            server.robot = null;
            server.robotName = null;
            data.put("message", "No more space in this world");
            server.response.addData(data);
            server.response.add("result", "ERROR");
            return;
        }

        data.put("position", server.robot.getPosition().getAsList());
        data.put("visibility", config.getVisibility());
        data.put("reload", config.getReloadTime());
        data.put("repair", config.getShieldRechargeTime());
        data.put("mine", config.getMineSetTime());
        data.put("shields", server.robot.getShields());

        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        JSONObject data = new JSONObject();
            // Create
//        Robot robot = new Robot("Hal");
        ResponseBuilder response = new ResponseBuilder();

        if (!doesRobotExist(world, robot)) {
            data.put("message", "Too many of you in this world");
            response.addData(data);
            response.add("result", "ERROR");
            return response;
        } else {
            // Create
            world.addRobot(robot);
            int maxShield = Math.min(Integer.parseInt(args.get(1).toString()), world.MAX_SHIELDS);
            int maxShot = Math.min(Integer.parseInt(args.get(2).toString()), world.MAX_SHOTS);
            robot.setMaxes(maxShield, maxShot);
        }

        Random random = new Random();

        boolean positionSet = false;

        if(world.maze.blocksPosition(world.getRobots(), new Position(0, 0), robot.getName()) == UpdateResponse.SUCCESS) {
            robot.setPosition(new Position(0, 0));
            positionSet = true;
        }

        if(!positionSet) {
            for (int i = 0; i < 1000; i++) {
                int x = 0;
                int y = 0;
                int upperX = world.BOTTOM_RIGHT.getX() + 1;
                int lowerX = world.TOP_LEFT.getX();
                int upperY = world.TOP_LEFT.getY() + 1;
                int lowerY = world.BOTTOM_RIGHT.getY();
                if(world.BOTTOM_RIGHT.getX() != 0 || world.TOP_LEFT.getX() != 0) {
                    x = random.nextInt(upperX - lowerX) + lowerX;
                    y = random.nextInt(upperY - lowerY) + lowerY;
                }
                
                if (world.maze.blocksPosition(world.getRobots(), new Position(x, y), robot.getName()) == UpdateResponse.SUCCESS){
                    robot.setPosition(new Position(x, y));
                    positionSet = true;
                    break;
                }
            }
        }

        if (!positionSet) {
            data.put("message", "No more space in this world");
            response.addData(data);
            response.add("result", "ERROR");
            world.removeRobot(robot.getName());
            return response;
        }
        data.put("position", robot.getPosition().getAsList());
        data.put("visibility", config.getVisibility());
        data.put("reload", config.getReloadTime());
        data.put("repair", config.getShieldRechargeTime());
        data.put("mine", config.getMineSetTime());
        data.put("shields", robot.getShields());
        response.addData(data);
        // robot.setRobotResponse(data);
        response.add("result", "OK");
        return response;
        }

    private boolean doesRobotExist(World world, Robot robot) {
        try {
            ConcurrentHashMap<String, Robot> robotDict = world.getRobots();
            Set<String> robots = robotDict.keySet();

            for (String key : robots) {
                Robot currentRobot = robotDict.get(key);
                if (currentRobot.getName().equals(robot.getName())) {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    private boolean doesRobotExist(World world, Server server) {
        try {
            ConcurrentHashMap<String, Robot> robotDict = world.getRobots();
            Set<String> robots = robotDict.keySet();

            for (String key : robots) {
                Robot currentRobot = robotDict.get(key);
                if (currentRobot.getName().equals(server.robotName)) {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }
}
