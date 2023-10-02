package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

@SuppressWarnings("unchecked")
public class RightCommand extends Command {
    /**
     * Constructor for the right command
     * */
    public RightCommand() {
        super("right");
    }

    /**
     * Overrides the execute command with:
     * setting the current direction, based upon the previous direction, and which it should be facing.
     * */
    @Override
    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();

        server.robot.updateDirection(true);

        data.put("message", "Done");
        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        ResponseBuilder response = new ResponseBuilder();
        JSONObject data = new JSONObject();

        robot.updateDirection(true);

        data.put("message", "Done, Turned Right");
        response.add("result", "OK");
        response.addData(data);
        // robot.setRobotResponse(data);
        return response;
    }
}

