package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

@SuppressWarnings("unchecked")
public class LeftCommand extends Command {
    /**
     * Constructor for left command
     * */
    public LeftCommand() {
        super("left");
    }

    /**
     * Overrides the execute command with:
     * setting the current direction, based upon the previous direction, and which it should be facing.
     * */
    @Override
    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();

        server.robot.updateDirection(false);

        data.put("message", "Done");
        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {

        JSONObject data = new JSONObject();

        ResponseBuilder response = new ResponseBuilder();

        robot.updateDirection(false);

        data.put("message", "Done, Turned Left");
        data.put("result", "OK");
        response.add("result", "OK");
        // robot.setRobotResponse(data);
        response.addData(data);
        return response;
    }
}
