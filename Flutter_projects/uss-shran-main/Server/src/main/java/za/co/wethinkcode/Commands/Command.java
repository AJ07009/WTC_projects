package za.co.wethinkcode.Commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Direction;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

@SuppressWarnings("unused")
public abstract class Command {
    private final String name;
    private String argument;

    public abstract void execute(World world, Server server);

    /**
     * constructor for command with no arguments
     * */
    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * constructor for command with arguments
     * */
    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * getter for the argument
     * */
    public String getArgument() {
        return this.argument;
    }

    public String getName(){
        return this.name;
    }

    /**
     * create command that does:
     * creates args, based on the arguments passed through.
     * checks if the args is just 1 (so like 'help' instead of 'forward 1'), and runs those commands.
     * else if does the commands and passes through their arguments.
     * if the argument is illegal or unsupported then it will throw back an exception
     *
     * @param instruction;
     * */
    public static Command create(JSONObject instruction, Boolean robotExists) {
        String command = instruction.get("command").toString();
        JSONArray args = (JSONArray) instruction.get("arguments");

        if (!robotExists) {
            return noRobot(command,args, instruction.get("robot").toString());
        }

        if ( args != null && args.size() > 0) {
            return argCommands(command, args);
        }

        switch (command) {
            case "state":
                return new StateCommand();
            case "look":
                return new LookCommand();
            case "mine":
                return new LayMineCommand();
            case "repair":
                return new RepairCommand();
            case "reload":
                return new ReloadCommand();
            case "fire":
                return new FireCommand();
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction.get("command"));
        }
    }

    private static Command argCommands(String command, JSONArray args) {
        switch (command) {
            case "forward":
                return new ForwardCommand(args.get(0).toString());
            case "back":
                return new ForwardCommand("-" + args.get(0).toString());
            case "turn":
                switch(args.get(0).toString()) {
                    case "left":
                        return new LeftCommand();
                    case "right":
                        return new RightCommand();
                    default:
                        throw new IllegalArgumentException("Unsupported argument: " + args.get(0).toString());
                }
            case "launch":
                return new LaunchCommand(args);
            default:
                throw new IllegalArgumentException("Unsupported command: " + command);
        }
    }

    private static Command noRobot(String command, JSONArray args, String name) {
        switch (command) {
            case "launch":
                return new LaunchCommand(args);
            default:
                throw new IllegalArgumentException("Robot: " + name + " does not exist.");
        }
    }

    public UpdateResponse updatePosition(int nrSteps, Robot robot, World world) {
        Position currentPosition = robot.getPosition();
        Direction currentDirection = robot.getCurrentDirection();

        int oldX = currentPosition.getX();
        int oldY = currentPosition.getY();

        Position newPos = getNewPosition(oldX,oldY,currentDirection, nrSteps);

        Position oldPosition = new Position(oldX, oldY);

        UpdateResponse response;
        if (Math.abs(nrSteps) == 1) {
            response = world.maze.blocksPosition(world.getRobots(), newPos, robot.getName());
        }
        else {
            response = world.maze.blocksPath(oldPosition, newPos, world.getRobots(), robot.getName());
            //this broke, don't touch
        }

        if (response == UpdateResponse.FAILED_HIT_MINE) {
            robot.setPosition(newPos);
            return response;
        }
        if (response != UpdateResponse.SUCCESS) return response;

        response = world.isInWorld(oldPosition, newPos);
        if (response != UpdateResponse.SUCCESS) return response;

        robot.setPosition(newPos);
        return UpdateResponse.SUCCESS;
    }

    private Position getNewPosition(int oldX, int oldY, Direction curDirection, int nrSteps) {
        int newX = oldX;
        int newY = oldY;

        switch (curDirection) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                break;
            case WEST:
                newX = newX - nrSteps;
                break;
            case EAST:
                newX = newX + nrSteps;
                break;
        }
        return new Position(newX,newY);
    }

    public abstract ResponseBuilder execute(World world, Robot robot);
}

