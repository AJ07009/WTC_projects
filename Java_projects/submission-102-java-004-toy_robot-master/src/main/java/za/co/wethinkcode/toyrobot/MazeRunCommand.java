package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.SimpleMazeRunner;
import za.co.wethinkcode.toyrobot.world.IWorld;

public class MazeRunCommand  extends Command {
    SimpleMazeRunner mazeRun;
    IWorld.Direction edgeDirection;

    @Override
    public boolean execute(Robot target) {
        target.setStatus("Starting maze run..");
        return mazeRun.mazeRun(target, edgeDirection);
    }

    public MazeRunCommand(String argument) {
        super("mazerun", argument);
        this.mazeRun = new SimpleMazeRunner();

        switch (this.getArgument().toLowerCase()) {
            case "top":
                this.edgeDirection = IWorld.Direction.UP;
                break;
            case "right":
                this.edgeDirection = IWorld.Direction.RIGHT;
                break;
            case "bottom":
                this.edgeDirection = IWorld.Direction.DOWN;
                break;
            case "left":
                this.edgeDirection = IWorld.Direction.LEFT;
                break;
        }
    }
}
