package za.co.wethinkcode.Commands;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Map.Obstacle;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class LookCommand extends Command{
    JSONArray array;

    public LookCommand() {
        super("look");
        array = new JSONArray();
    }

    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();
        server.response.add("result", "OK");

        checkObstacles(server, world.getMaze().getObstacles(), world.VISIBILITY);
        checkObstacles(server, world.getMaze().getPits(), world.VISIBILITY);
        checkObstacles(server, world.getMaze().getMines(), (int) Math.floor(world.VISIBILITY/2.0));
        checkRobots(server, world.getRobots(), world.VISIBILITY);
        checkForEdge(server, world, world.VISIBILITY);

        data.put("objects", array);
        server.response.addData(data);
        server.response.add("state", server.robot.getState());
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        JSONObject data = new JSONObject();
        ResponseBuilder response = new ResponseBuilder();
        response.add("result", "OK");
        // robot.setRobotResponse(data);

        checkObstaclesApi(robot, world.getMaze().getObstacles(), world.VISIBILITY);
        checkObstaclesApi(robot, world.getMaze().getPits(), world.VISIBILITY);
        checkObstaclesApi(robot, world.getMaze().getMines(), (int) Math.floor(world.VISIBILITY/2.0));
        checkRobotsApi(robot, world.getRobots(), world.VISIBILITY);
        checkForEdgeApi(robot, world, world.VISIBILITY);

        data.put("objects", array);
        response.addData(data);
        // robot.setRobotResponse(data);
        return response;
    }

    private void checkObstacles(Server server, Vector<Obstacle> obsList, int visionRange) {
        Robot robot = server.robot;

        for (Obstacle obstacle: obsList) {

            if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX(), robot.getPosition().getY() + visionRange))) {
                this.array.add(makeJsonObject(obstacle, (obstacle.getBottomLeftY() - robot.getPosition().getY()), "NORTH"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX() + visionRange, robot.getPosition().getY()))) {
                this.array.add(makeJsonObject(obstacle, (obstacle.getBottomLeftX() - robot.getPosition().getX()), "EAST"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX(), robot.getPosition().getY() - visionRange))) {
                this.array.add(makeJsonObject(obstacle, (robot.getPosition().getY() - obstacle.getBottomLeftY())-(obstacle.getSize()), "SOUTH"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX() - visionRange, robot.getPosition().getY()))) {
                this.array.add(makeJsonObject(obstacle, (robot.getPosition().getX() - obstacle.getBottomLeftX())-(obstacle.getSize()), "WEST"));
            }
        }
    }

    private void checkRobotsApi(Robot robot, ConcurrentHashMap<String, Robot> robots, int visionRange) {
        Set<String> keys = robots.keySet();

        for (String key : keys) {
            Robot mapRobot = robots.get(key);
            if (key.equals(robot.getName())) {
                continue;
            }
            if (mapRobot.getPosition().getY() > robot.getPosition().getY() &&
                    mapRobot.getPosition().getY() <robot.getPosition().getY()+visionRange &&
                    mapRobot.getPosition().getX() == robot.getPosition().getX()) {
                this.array.add(makeJsonObject(mapRobot, (mapRobot.getPosition().getY() - robot.getPosition().getY()), "NORTH"));
            } if (mapRobot.getPosition().getX() > robot.getPosition().getX() &&
                    mapRobot.getPosition().getX() < robot.getPosition().getX()+visionRange &&
                    mapRobot.getPosition().getY() == robot.getPosition().getY()) {
                this.array.add(makeJsonObject(mapRobot, (mapRobot.getPosition().getX() - robot.getPosition().getX()), "EAST"));
            } if (mapRobot.getPosition().getY() < robot.getPosition().getY() &&
                    mapRobot.getPosition().getY() > robot.getPosition().getY()-visionRange &&
                    mapRobot.getPosition().getX() == robot.getPosition().getX()) {
                this.array.add(makeJsonObject(mapRobot, (robot.getPosition().getY() - mapRobot.getPosition().getY()), "SOUTH"));
            } if (mapRobot.getPosition().getX() < robot.getPosition().getX() &&
                    mapRobot.getPosition().getX() > robot.getPosition().getX()-visionRange &&
                    mapRobot.getPosition().getY() == robot.getPosition().getY()) {
                this.array.add(makeJsonObject(mapRobot, (robot.getPosition().getX() - mapRobot.getPosition().getX()), "WEST"));
            }
        }
    }

    private void checkForEdge(Server server, World world, int visionRange) {


        if (server.robot.getPosition().getY() + (visionRange-1) >= world.TOP_LEFT.getY() &&
                server.robot.getPosition().getY() <= world.TOP_LEFT.getY()) {
            this.array.add(makeEdgeJson("NORTH", (world.TOP_LEFT.getY() - server.robot.getPosition().getY())));
        } if (server.robot.getPosition().getX() + (visionRange-1) >= world.BOTTOM_RIGHT.getX() &&
                server.robot.getPosition().getX() <= world.BOTTOM_RIGHT.getX()) {
            this.array.add(makeEdgeJson("EAST", (world.BOTTOM_RIGHT.getX() - server.robot.getPosition().getX())));
        } if (server.robot.getPosition().getY() - (visionRange-1) <= world.BOTTOM_RIGHT.getY() &&
                server.robot.getPosition().getY() >= world.BOTTOM_RIGHT.getY()) {
            this.array.add(makeEdgeJson("SOUTH", (server.robot.getPosition().getY() - world.BOTTOM_RIGHT.getY())));
        } if (server.robot.getPosition().getX() - (visionRange-1) <= world.TOP_LEFT.getX() &&
                server.robot.getPosition().getX() >= world.TOP_LEFT.getX()) {
            this.array.add(makeEdgeJson("WEST", (server.robot.getPosition().getX() - world.TOP_LEFT.getX())));
        }
    }

    private JSONObject makeEdgeJson(String direction, int distance) {
        JSONObject json = new JSONObject();

        json.put("direction", direction);
        json.put("type", "EDGE");
        json.put("distance", String.valueOf(distance+1));

        return json;
    }

    private JSONObject makeJsonObject(Object obstacle, int distance, String direction) {
        JSONObject json = new JSONObject();
        String obstacleType = obstacle.getClass().getSimpleName();

        if (obstacleType.equals("SquareObstacle")) obstacleType = "OBSTACLE";

        json.put("direction", direction);
        json.put("type", obstacleType.toUpperCase());
        json.put("distance", String.valueOf(distance));

        return json;
    }

    private void checkObstaclesApi(Robot robot, Vector<Obstacle> obsList, int visionRange) {
//        System.out.println(robot.getPosition());

        for (Obstacle obstacle: obsList) {

            if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX(), robot.getPosition().getY() + visionRange))) {
                this.array.add(makeJsonObject(obstacle, (obstacle.getBottomLeftY() - robot.getPosition().getY()), "NORTH"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX() + visionRange, robot.getPosition().getY()))) {
                this.array.add(makeJsonObject(obstacle, (obstacle.getBottomLeftX() - robot.getPosition().getX()), "EAST"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX(), robot.getPosition().getY() - visionRange))) {
                this.array.add(makeJsonObject(obstacle, (robot.getPosition().getY() - obstacle.getBottomLeftY())-(obstacle.getSize()), "SOUTH"));
            } if (obstacle.blocksPath(robot.getPosition(), new Position(robot.getPosition().getX() - visionRange, robot.getPosition().getY()))) {
                this.array.add(makeJsonObject(obstacle, (robot.getPosition().getX() - obstacle.getBottomLeftX())-(obstacle.getSize()), "WEST"));
            }
        }
    }

    private void checkRobots(Server server, ConcurrentHashMap<String, Robot> robots, int visionRange) {
        Set<String> keys = robots.keySet();

        for (String key : keys) {
            Robot robot = robots.get(key);
            if (key.equals(server.robotName)) {
                continue;
            }
            if (robot.getPosition().getY() > server.robot.getPosition().getY() &&
                    robot.getPosition().getY() < server.robot.getPosition().getY()+visionRange &&
                    robot.getPosition().getX() == server.robot.getPosition().getX()) {
                this.array.add(makeJsonObject(robot, (robot.getPosition().getY() - server.robot.getPosition().getY()), "NORTH"));
            } if (robot.getPosition().getX() > server.robot.getPosition().getX() &&
                    robot.getPosition().getX() < server.robot.getPosition().getX()+visionRange &&
                    robot.getPosition().getY() == server.robot.getPosition().getY()) {
                this.array.add(makeJsonObject(robot, (robot.getPosition().getX() - server.robot.getPosition().getX()), "EAST"));
            } if (robot.getPosition().getY() < server.robot.getPosition().getY() &&
                    robot.getPosition().getY() > server.robot.getPosition().getY()-visionRange &&
                    robot.getPosition().getX() == server.robot.getPosition().getX()) {
                this.array.add(makeJsonObject(robot, (server.robot.getPosition().getY() - robot.getPosition().getY()), "SOUTH"));
            } if (robot.getPosition().getX() < server.robot.getPosition().getX() &&
                    robot.getPosition().getX() > server.robot.getPosition().getX()-visionRange &&
                    robot.getPosition().getY() == server.robot.getPosition().getY()) {
                this.array.add(makeJsonObject(robot, (server.robot.getPosition().getX() - robot.getPosition().getX()), "WEST"));
            }
        }
    }

    private void checkForEdgeApi(Robot robot, World world, int visionRange) {


        if (robot.getPosition().getY() + (visionRange-1) >= world.TOP_LEFT.getY() &&
                robot.getPosition().getY() <= world.TOP_LEFT.getY()) {
            this.array.add(makeEdgeJson("NORTH", (world.TOP_LEFT.getY() - robot.getPosition().getY())));
        } if (robot.getPosition().getX() + (visionRange-1) >= world.BOTTOM_RIGHT.getX() &&
                robot.getPosition().getX() <= world.BOTTOM_RIGHT.getX()) {
            this.array.add(makeEdgeJson("EAST", (world.BOTTOM_RIGHT.getX() - robot.getPosition().getX())));
        } if (robot.getPosition().getY() - (visionRange-1) <= world.BOTTOM_RIGHT.getY() &&
                robot.getPosition().getY() >= world.BOTTOM_RIGHT.getY()) {
            this.array.add(makeEdgeJson("SOUTH", (robot.getPosition().getY() - world.BOTTOM_RIGHT.getY())));
        } if (robot.getPosition().getX() - (visionRange-1) <= world.TOP_LEFT.getX() &&
                robot.getPosition().getX() >= world.TOP_LEFT.getX()) {
            this.array.add(makeEdgeJson("WEST", (robot.getPosition().getX() - world.TOP_LEFT.getX())));
        }
    }
}
