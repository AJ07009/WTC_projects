package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.Utility.Schedule;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.World;
import za.co.wethinkcode.Robot.Position;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class LayMineCommand extends Command{


    public LayMineCommand() {
        super("mine");
    }

    /**
     * Checks if the robot can move forward by 1 step, if the robot is obstructed
     * then the mine placed will detonate on the robot that is laying it.
     * Starts the task scheduler for laying the mine on the field.
     * Build the JsonObject to send to the client stating that the task has started.
     * @param world;
     * @param server;
     */
    @Override
    public void execute(World world, Server server) {
        // Checks if the robot is allowed to lay mines.
        if (canLay(server)) {
            // Create a forward command to move the robot 1 step ahead after laying mine.
            server.robot.setStatus("SETMINE");
            server.robot.setOldShield(server.robot.getShields());
            server.robot.setShields(0);
            try {
                new Schedule(server, world, "mine", world.MINE_SET_TIME);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject data = new JSONObject();
            data.put("message", "Done");
            server.response.addData(data);
            server.response.add("result", "OK");
        } else {
            JSONObject data = new JSONObject();
            data.put("message", "No mines while using a gun.");
            server.response.addData(data);
            server.response.add("result", "ERROR");
        }
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        // Create a forward command to move the robot 1 step ahead after laying mine.
//        robot.setStatus("SETMINE");
//        robot.setOldShield(robot.getShields());
//        robot.setShields(0);
//        try {
//            new Schedule(robot, world, "mine", world.MINE_SET_TIME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ResponseBuilder response = new ResponseBuilder();
        JSONObject data = new JSONObject();

        Position minePos = new Position(robot.getPosition().getX(),robot.getPosition().getY());
        world.getMaze().createMine(minePos);

        ForwardCommand fwd = new ForwardCommand("1");
        fwd.execute(world, robot);
        data.put("message", "Done, Mine Placed");
        // data.put("result", "OK");
        response.add("result", "OK");
        // robot.setRobotResponse(data);
        // response
        response.addData(data);
        return response;

    }

    private boolean canLay(Server server) {
        return (server.robot.getMaxShots() == 0);
    }
}
