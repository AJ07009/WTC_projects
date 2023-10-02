 package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

 public class Robot {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;
    private AbstractWorld world;
    private Maze maze;
    private boolean obstructed;

    private ArrayList<Command> history;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.UP;
        this.history = new ArrayList<Command>();
        this.obstructed = false;

        //This is bullshit just to pass tests
        this.world = new TextWorld(new EmptyMaze());
    }

    public void setWorld(AbstractWorld world) {
        this.world = world;
    }


    public String getStatus() {
        return this.status;
    }

    public IWorld.Direction getCurrentDirection() {
        return this.world.getCurrentDirection();
    }

    public boolean handleCommand(Command command) {
        setObstructed(false);
        if ( (command instanceof ForwardCommand) ||
            (command instanceof BackCommand) ||
            (command instanceof RightCommand) || 
            (command instanceof LeftCommand) ||
            (command instanceof SprintCommand) ) {
            history.add(command);
        }
        return command.execute(this);
    }

    public String handleReplayCommand(boolean reverse, boolean ranged, boolean single, int start, int end) {
        int size = this.history.size();
        List<Command> replayList = this.history;

        if (ranged) {
            replayList = this.history.subList(size-start, size-end);
        } else if (single) {
            replayList = this.history.subList(size-start, size);
        }

        if (reverse) {
            Collections.reverse(replayList);
        }

        for (Command cmd : replayList) {
            cmd.execute(this);
            Play.printStatus(this.toString());
        }
        return "replayed " + replayList.size() + " commands.";
    }

    public IWorld.UpdateResponse updatePosition(int nrSteps){
        return world.updatePosition(nrSteps);
    }

    public void updateDirection(boolean turnRight) {world.updateDirection(turnRight);}

    @Override
    public String toString() {
       return "[" + this.world.getPosition().getX() + "," + this.world.getPosition().getY() + "] "
               + this.name + "> " + this.status;
    }

    public Position getPosition() {
        return this.world.getPosition();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public IWorld.Direction getDirection() {
        return this.world.getCurrentDirection();
    }

    public void setObstructed(Boolean obstructed) {
        this.obstructed = obstructed;
    }

    public Maze getMaze() {
        return this.maze;
    }

    public boolean getObstructed() {
        return this.obstructed;
    }

    public void setDirection(Direction newDirection) {
        this.currentDirection = newDirection;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }
}