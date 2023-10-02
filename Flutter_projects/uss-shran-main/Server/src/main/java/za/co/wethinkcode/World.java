package za.co.wethinkcode;

import za.co.wethinkcode.Commands.Command;
import za.co.wethinkcode.Map.*;
import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;
import za.co.wethinkcode.Utility.ConfigReader;
import za.co.wethinkcode.Utility.ResponseBuilder;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class World{
    public static ConfigReader config = new ConfigReader();

    //Hashmap of robots currently in play.
    private final ConcurrentHashMap<String, Robot> robots = new ConcurrentHashMap<>();
    //Sets the maps Bottom-right and top-left positions set in the config file.
    public Position BOTTOM_RIGHT;
    public Position TOP_LEFT;
    // The map that the world will be using.
    public Maze maze;
    //Values that were received from the config file.
    public int MAX_SHOTS = config.getMaxShots();
    public int MAX_SHIELDS = config.getMaxShieldStrength();
    public int RELOAD_TIME = config.getReloadTime();
    public int REPAIR_TIME = config.getReloadTime();
    public int VISIBILITY = config.getVisibility();
    public int MINE_SET_TIME = config.getMineSetTime();
    
    public Position getBOTTOM_RIGHT() {
        return BOTTOM_RIGHT;
    }

    public void setBOTTOM_RIGHT(Position bOTTOM_RIGHT) {
        BOTTOM_RIGHT = bOTTOM_RIGHT;
    }

    public Position getTOP_LEFT() {
        return TOP_LEFT;
    }

    public void setTOP_LEFT(Position tOP_LEFT) {
        TOP_LEFT = tOP_LEFT;
    }

    public int getMAX_SHOTS() {
        return MAX_SHOTS;
    }

    public void setMAX_SHOTS(int mAX_SHOTS) {
        MAX_SHOTS = mAX_SHOTS;
    }

    public int getMAX_SHIELDS() {
        return MAX_SHIELDS;
    }

    public void setMAX_SHIELDS(int mAX_SHIELDS) {
        MAX_SHIELDS = mAX_SHIELDS;
    }

    public int getRELOAD_TIME() {
        return RELOAD_TIME;
    }

    public void setRELOAD_TIME(int rELOAD_TIME) {
        RELOAD_TIME = rELOAD_TIME;
    }

    public int getREPAIR_TIME() {
        return REPAIR_TIME;
    }

    public void setREPAIR_TIME(int rEPAIR_TIME) {
        REPAIR_TIME = rEPAIR_TIME;
    }

    public int getVISIBILITY() {
        return VISIBILITY;
    }

    public void setVISIBILITY(int vISIBILITY) {
        VISIBILITY = vISIBILITY;
    }

    public int getMINE_SET_TIME() {
        return MINE_SET_TIME;
    }

    public void setMINE_SET_TIME(int mINE_SET_TIME) {
        MINE_SET_TIME = mINE_SET_TIME;
    }

    public World(int size) {
        setWorldSize(size);
        maze = new EmptyMaze(TOP_LEFT,BOTTOM_RIGHT);
    }

    public World(int size, List<int[]> obstacles) {
        setWorldSize(size);
        maze = new EmptyMaze(TOP_LEFT,BOTTOM_RIGHT, obstacles);
    }

    private void setWorldSize(int size) {
        TOP_LEFT = new Position(-size/2, size/2);
        BOTTOM_RIGHT = new Position(size/2, -size/2);
    }

    /**
     * getter to get the maze.
     * */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Return the list of SquareObstacles inside the map being used currently.
     * @return Vector list of obstacle objects.
     */
    public Vector<Obstacle> getObstacles() {
        return this.maze.getObstacles();
    }

    /**
     * Remove the robot at the given key.
     * If a robot is dead or a client is disconnected this method will need to called to remove them from play.
     * @param name of the robot to be removed
     */
    public void removeRobot(String name) {
        this.robots.remove(name);
    }

    /**
     * Method will call the execute method on the given command.
     * @param command to be executed.
     * @param server that the command will issue data.
     */
    public void handleCommand(Command command, Server server) {
        command.execute(this, server);
    }


    public ResponseBuilder handleCommand(Command command, Robot robot){
        return command.execute(this, robot);
    }
    /**
     * Adds the newly created robot to the hashmap of robots on play.
     * The Robot objects name will be used as the key.
     * @param target to be added to list.
     */
    public void addRobot(Robot target) {
        this.robots.put(target.getName(), target);
    }

    /**
     * Returns the robot from the hashmap with the given String as the key.
     * @param name of robot
     * @return Robot object.
     */
    public Robot getRobot(String name){
        return this.robots.get(name);
    }

    /**
     * Returns the hashmap containing all the robots currently in play.
     * Key: robot name, Value: Robot object
     * @return hashmap of robots
     */
    public ConcurrentHashMap<String, Robot> getRobots() {
        return robots;
    }

    /**
     * Shows the obstacles that are inside of the obstacle list.
     * printing them in the terminal at the positions of the obstacles.
     * */
    public void showObstacles() {
        maze.getObstacles();
        for (Obstacle maze : maze.getObstacles()) {
            System.out.println("Wall- At position "+ maze.getBottomLeftX()+ "," +maze.getBottomLeftY()+ " (to "+
                    (maze.getBottomLeftX() + 4) + "," + (maze.getBottomLeftY() + 4) + ")");
        }

        for (Obstacle pit : maze.getPits()) {
            System.out.println("Pit- At position "+ pit.getBottomLeftX()+ "," +pit.getBottomLeftY()+ " (to "+
                    (pit.getBottomLeftX() + 4) + "," + (pit.getBottomLeftY() + 4) + ")");
        }

        for (Obstacle mine : maze.getMines()) {
            System.out.println("Mine- At position "+ mine.getBottomLeftX()+ "," +mine.getBottomLeftY()+ " (to "+
                    (mine.getBottomLeftX()) + "," + (mine.getBottomLeftY()) + ")");
        }
    }

    /**
     * This function checks if the position is allowed.
     * @return: boolean true if allowed, false if not allowed.
     * */
    public UpdateResponse isInWorld(Position oldPosition, Position newPosition) {
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) return UpdateResponse.SUCCESS;
        else return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    public void changeWorldConfig(Position bottomRight, Position topLeft, int maxShields, int maxShots, int reloadTime,
            int repairTime, int visibility2, int mineSetTime, Vector<Obstacle> obstacleList) {
                setBOTTOM_RIGHT(bottomRight);
                setTOP_LEFT(topLeft);
                setMAX_SHIELDS(maxShields);
                setMAX_SHOTS(maxShots);
                setMINE_SET_TIME(mineSetTime);
                setRELOAD_TIME(reloadTime);
                setREPAIR_TIME(repairTime);
                setVISIBILITY(visibility2);
                maze.setObstacles(obstacleList);
    }
}
