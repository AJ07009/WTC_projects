package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;

import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

@SuppressWarnings("unchecked")
public class StateCommand extends Command{

    public StateCommand() {
        super("state");
    }

    /**
     * Builds the JsonObject for the state of the robots to send back to client.
     * @param world;
     * @param server;
     */
    @Override
    public void execute(World world, Server server) {
        JSONObject data = new JSONObject();
        data.put("message", "State");
        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        JSONObject data = new JSONObject();
        ResponseBuilder response = new ResponseBuilder();
        data.put("message", "State");
        response.addData(data);
        response.add("result", "OK");
        return response;
    }
}
