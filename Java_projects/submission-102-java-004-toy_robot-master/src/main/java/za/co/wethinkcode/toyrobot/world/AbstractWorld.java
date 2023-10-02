package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

import java.util.List;

public abstract class AbstractWorld implements IWorld {

    private final Position TOP_LEFT = new Position(-100,200);
    private final Position BOTTOM_RIGHT = new Position(100,-200);
    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private IWorld.Direction currentDirection;
    private List<Obstacle> obstacles;

    public AbstractWorld(Maze maze){
        this.position = CENTRE;
        this.currentDirection = IWorld.Direction.UP;
        this.obstacles = maze.getObstacles();
    }

    public void setPosition(Position position){
        this.position = position;
    }

    @Override
    public String toString() {
        String list = "There are some obstacles:\n";
        Obstacle obstacle;
        for (int i = 0; i < this.obstacles.size(); i++) {
            obstacle = this.obstacles.get(i);
            int x = obstacle.getBottomLeftX();
            int y = obstacle.getBottomLeftY();
            list += String.format("- At position %d,%d (to %d,%d)", x, y, x+5, y+5);
            if (i != this.obstacles.size()-1) list += "\n";
        }
        return list;
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();
        boolean pathBlocked = false, positionBlocked = false;
        UpdateResponse response;

        switch (this.currentDirection) {
            case UP:
                newY += nrSteps;
                break;
            case RIGHT:
                newX += nrSteps;
                break;
            case DOWN:
                newY -= nrSteps;
                break;
            default:
                newX -= nrSteps;
                break;
        }

        Position newPosition = new Position(newX, newY);

        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){

            response = UpdateResponse.SUCCESS;

            for (Obstacle obstacle: this.obstacles) {
                pathBlocked = obstacle.blocksPosition(newPosition);
                positionBlocked = obstacle.blocksPath(this.position, newPosition);
                if(positionBlocked || pathBlocked){
                    response = UpdateResponse.FAILED_OBSTRUCTED;
                    break;
                }
            }
            if (response.equals(UpdateResponse.SUCCESS)) {
                this.position = newPosition;
            }

        } else {
            response = UpdateResponse.FAILED_OUTSIDE_WORLD;
        }

        return response;
    }

    @Override
    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            this.currentDirection = this.currentDirection.next();
        }else{
            this.currentDirection = this.currentDirection.prev();
        }
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return (position.isIn(TOP_LEFT, BOTTOM_RIGHT));
    }

    @Override
    public boolean isAtEdge() {

        boolean x = this.position.getX() == -100 || this.position.getX() == 100;
        boolean y = this.position.getY() == -200 || this.position.getY() == 200;

        return x || y;
    }

    @Override
    public void reset() {
        this.currentDirection = IWorld.Direction.UP;
        this.position = CENTRE;
        this.obstacles.clear();
        this.obstacles = null;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public void showObstacles() {}

}
