package za.co.wethinkcode.Commands;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.Utility.Schedule;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.World;

import java.io.IOException;


@SuppressWarnings("unchecked")
public class RepairCommand extends Command{


    public RepairCommand() {
        super("repair");
    }

    /**
     * Starts the task of repairing the robots shield.
     * build the JsonObject to send to the client, stating that the repairing has started.
     * @param world;
     * @param server;
     */
    public void execute(World world, Server server) {
        try {
            server.robot.setStatus("REPAIR");
            new Schedule(server, world, "repair", world.REPAIR_TIME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject data = new JSONObject();
        data.put("message", "Repair");
        server.response.addData(data);
        server.response.add("result", "OK");
    }

    @Override
    public ResponseBuilder execute(World world, Robot robot) {
        ResponseBuilder response = new ResponseBuilder();
        JSONObject data = new JSONObject();

        try {
            robot.setStatus("REPAIR");
            Schedule sch = new Schedule(robot, world, "repair", world.getREPAIR_TIME());
            sch.startTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("message", "Repair");
        response.addData(data);
        response.add("result", "OK");
        return response;
    }
}
