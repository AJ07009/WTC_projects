package za.co.wethinkcode.Utility;

import org.json.simple.JSONObject;
import za.co.wethinkcode.Commands.Command;
import za.co.wethinkcode.Commands.ForwardCommand;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.World;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unchecked")
public class Schedule {
    private final ResponseBuilder response;
    private final Timer timer;
    private final Robot robot;
    private final String todo;
    private Server server;
    private final World world;
    private final int seconds;

    public Schedule(Server server, World world, String todo, int seconds) throws IOException {
        this.response = new ResponseBuilder();
        this.todo = todo;
        this.timer = new Timer();
        this.robot = server.robot;
        this.server = server;
        this.world = world;
        this.seconds = seconds;
    }

    public Schedule(Robot robot2, World world2, String todo2, int seconds) {
        this.response = new ResponseBuilder();
        this.todo = todo2;
        this.timer = new Timer();
        this.seconds = seconds;
        this.robot = robot2;
        this.world = world2;
    }

    /**
     * Starts the new task to be created.
     */
    public ResponseBuilder startTask() {
        timer.schedule(new Task(), seconds * 1000L);
        return this.response;
    }

    /**
     * Changes the status of the robot while a task is being created or completed.
     * @param status to change.
     */
    public void changeRobotState(String status) {
        robot.setStatus(status);
    }

    /**
     * Method will after a given seconds delay perform a task that repairs the robot.
     * Robots shields will set to the maximum that were set at launch and state will be set to NORMAL.
     *A JsonObject is constructed to let the user know that the task has completed and is sent to the user with new the new state.
     */
    private void repairRobot() {
        robot.setShields(robot.getMaxShields());
        changeRobotState("NORMAL");

        JSONObject data = new JSONObject();
        data.put("message", "Done");
        response.addData(data);
        response.add("result", "OK");
        response.add("state", robot.getState());
        server.out.println(response);
    }

    /**
     * Method will after a given seconds delay perform a task that repairs the robot.
     * Robot will move forward by one place and place a mine on the old location that he was on.
     * Status of robot is set to NORMAL and a JsonObject is built to send to the user with new state.
     * @param server to perform task on.
     * @param world to update mines list.
     */
    private ResponseBuilder layMine(Robot robot, World world) {
        try {Position oldPos = new Position(robot.getPosition().getX(),
                robot.getPosition().getY());

            if (robot.getShields() == 0){
                world.getMaze().createMine(oldPos);
            }
            robot.setShields(robot.getOldShield() - robot.getShields());

            Command forward1 = new ForwardCommand("1");
            forward1.execute(world, server);
            JSONObject data = new JSONObject();
            if (oldPos.equals(robot.getPosition())) {
                world.getMaze().hitMine(robot.getPosition(), server);
                data.put("message", "Mine");
                if (robot.isDead().equals("DEAD")) {
                    robot.kill(world, server, "Mine");
                }
            }
            else {
                data.put("message", "Done");
            }
            response.addData(data);

            robot.setStatus("NORMAL");

            response.add("result", "OK");
            response.add("state", robot.getState());
        } catch (Exception e) {
            System.out.println("Robot died before completion");
        }
        return response;
    }

    /**
     * Method will after a given seconds delay perform a task that reloads the robots ammo.
     * Robots shots will set to the maximum that were set at launch and state will be set to NORMAL.
     * A JsonObject is constructed to let the user know that the task has completed and is sent to the user with new the new state.
     * @param server to issue commands to.
     */
    private ResponseBuilder reload(Robot robot) {
        robot.setShots(robot.getMaxShots());
        changeRobotState("NORMAL");

        JSONObject data = new JSONObject();
        data.put("message", "Done");
        response.addData(data);
        response.add("result", "OK");
        response.add("state", robot.getState());
        return response;
    }

    /**
     * Method will choose which task needs to be scheduled to run.
     */
    class Task extends TimerTask {
        public void run() {
            switch (todo) {
                case "mine":
                    layMine(robot, world);
                    break;
                case "reload":
                    reload(robot);
                    break;
                case "repair":
                    repairRobot();
                    break;
            }
            timer.cancel(); //Terminate the timer thread
        }
    }
}
