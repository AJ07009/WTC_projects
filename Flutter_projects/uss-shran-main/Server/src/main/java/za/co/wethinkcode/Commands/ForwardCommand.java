package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

@SuppressWarnings({"unchecked", "unused"})
public class ForwardCommand extends Command {
    private World currentWorld;

    /**
     * Constructor for forward command
     * */
    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        JSONObject data = new JSONObject();
        ResponseBuilder responseBuilder = new ResponseBuilder();
        int nrSteps;
        int totalSteps;
        try {
            String argument = getArgument();
            if (argument.contains("--")){
                argument = argument.substring(2, argument.length() - 1);
            }
            nrSteps = Integer.parseInt(argument);
            totalSteps = nrSteps;
        }catch (NumberFormatException e) {
            data.put("message", "Could not parse arguments");
            data.put("result", "ERROR");
            robot.setRobotResponse(data);
            responseBuilder.addData(data);
            return responseBuilder;
        }

        UpdateResponse response = UpdateResponse.SUCCESS;
        int step = 1;
        if (nrSteps < 0) step = -1;
        while (nrSteps != 0 && response == UpdateResponse.SUCCESS) {
            response =  updatePosition(step, robot, world);
            nrSteps -= step;
        }

        String message = "";
        if (response == UpdateResponse.SUCCESS){
            message = "Done, Moved by " + totalSteps + " Steps." ;
        } else if (response == UpdateResponse.FAILED_OBSTRUCTED){
            message = "Obstructed";
//        } else if (response == UpdateResponse.FAILED_BOTTOMLESS_PIT) {
//            message = "Fell";
//            robot.kill(world, server, "Fell");
        } else if (response == UpdateResponse.FAILED_HIT_MINE) {
            message = "Mine";
            world.maze.hitMine(robot.getPosition(), robot);
            if (robot.isDead().equals("DEAD")) {
                robot.kill(world,"Mine", robot);
            }
        } else if (response == UpdateResponse.FAILED_OUTSIDE_WORLD){
            message = "Obstructed";
        }
        data.put("message", message);
        responseBuilder.add("result", "OK");
        // robot.setRobotResponse(data);
        responseBuilder.addData(data);
        return responseBuilder;
    }

    /**
     * Overrides execute with:
     * number of steps and updates the steps on a positive amount so you move back the required
     * number of steps
     * */
    @Override
    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();
        int nrSteps;
        try {
            String argument = getArgument();
            if (argument.contains("--")){
                argument = argument.substring(2, argument.length() - 1);
            }
            nrSteps = Integer.parseInt(argument);
        }catch (NumberFormatException e) {
            data.put("message", "Could not parse arguments");
            server.response.addData(data);
            server.response.add("result", "ERROR");
            return;
        }

        UpdateResponse response = UpdateResponse.SUCCESS;
        int step = 1;
        if (nrSteps < 0) step = -1;
        while (nrSteps != 0 && response == UpdateResponse.SUCCESS) {
            response =  updatePosition(step, server.robot, world);
            nrSteps -= step;
        }

        String message = "";
        if (response == UpdateResponse.SUCCESS){
            message = "Done";
        } else if (response == UpdateResponse.FAILED_OBSTRUCTED){
            message = "Obstructed";
        } else if (response == UpdateResponse.FAILED_BOTTOMLESS_PIT) {
            message = "Fell";
            server.robot.kill(world, server, "Fell");
        } else if (response == UpdateResponse.FAILED_HIT_MINE) {
            message = "Mine";
            world.maze.hitMine(server.robot.getPosition(), server);
            if (server.robot.isDead().equals("DEAD")) {
                server.robot.kill(world, server, "Mine");
            }
        } else if (response == UpdateResponse.FAILED_OUTSIDE_WORLD){
            message = "Obstructed";
        }

        data.put("message", message);
        server.response.addData(data);
        server.response.add("result", "OK");
    }
}

