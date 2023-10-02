package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.Utility.Schedule;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.World;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class ReloadCommand extends Command{

    public ReloadCommand() {
        super("reload");
    }

    /**
     * Starts the task scheduler for laying a mine on the field.
     * Build the JsonObject to send to the client stating that the reloading has begun.
     * @param world;
     * @param server;
     */
    @Override
    public void execute(World world, Server server) {
        try {
            server.robot.setStatus("RELOAD");
            new Schedule(server, world, "reload", world.RELOAD_TIME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject data = new JSONObject();
        data.put("message", "Reload");
        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        ResponseBuilder response = new ResponseBuilder();
        robot.setShots(robot.getMaxShots());
        JSONObject data = new JSONObject();
        data.put("message", "Done, Reloaded");
        response.addData(data);
        response.add("result", "OK");
        // robot.setRobotResponse(data);
        return response;
    }
}
