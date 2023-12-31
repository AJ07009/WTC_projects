package za.co.wethinkcode.Map;

import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class BaseMaze implements Maze {

    Vector<Obstacle> obstaclesList = new Vector<>();
    Vector<Obstacle> pitsList = new Vector<>();
    Vector<Obstacle> minesList = new Vector<>();


    /**
     * setter to set the obstacles.
     * @param obstacle: takes param of a list obstacle.
     * */
    public void setObstacles(Vector<Obstacle> obstacle) {
        this.obstaclesList = obstacle;
    }

    /**
     * getter to get the obstacles.
     * */
    public Vector<Obstacle> getObstacles() {
        return this.obstaclesList;
    }

    public Vector<Obstacle> getPits() {
        return pitsList;
    }

    public Vector<Obstacle> getMines() {
        return minesList;
    }

    /**
     * Create the obstacle and then add the new obstacle to the list of obstacles.
     * @param position: of the obstacle to be created
     * */
    public void createObstacles(Position position) {
        this.obstaclesList.add(new SquareObstacle(position.getX(), position.getY()));
    }

    public void removeObstacle(Obstacle obstacle) {
        obstaclesList.removeIf(ob -> ob.getBottomLeftX() == obstacle.getBottomLeftX() && ob.getBottomLeftY() == obstacle.getBottomLeftY());
    }

    /**
     * Creates a new bottomless pit on the map and adds this to the list of current pits.
     * @param position: of the pit to be created
     */
    public void createPit(Position position) {
        this.pitsList.add(new Pits(position.getX(), position.getY()));
    }

    /**
     * Creates a new mine with the position given and adds it to the list of current mines.
     * @param position: of the mine to be created
     */
    public void createMine(Position position) {
        this.minesList.add(new Mines(position.getX(), position.getY()));
    }

    /**
     * Takes in 3 parameters, old and new position and checks if the path is blocked per each obstacle in the
     * obstacle lists as well as the list of robots currently in play.
     * @param a : the old position;
     * @param b : the new position;
     * @return: returns UpdateResponse of the obstacle hit.
     * */
    public UpdateResponse blocksPath(Position a, Position b, ConcurrentHashMap<String, Robot> robots, String robotName) {

        int incX = 1;
        int incY = 1;
        if (a.getX() > b.getX()) incX = -1;
        if (a.getY() > b.getY()) incY = -1;

        int x = a.getX();
        do {
            int y = a.getY();
            do {
                for (Obstacle pit : this.pitsList) {
                    if (pit.blocksPosition(new Position(x, y)))
                        return UpdateResponse.FAILED_BOTTOMLESS_PIT;
                }

                for (Obstacle obst : this.obstaclesList) {
                    if (obst.blocksPosition(new Position(x, y))) {
                        return UpdateResponse.FAILED_OBSTRUCTED;
                    }
                }

                for (Obstacle mine : this.minesList) {
                    if (mine.blocksPosition(new Position(x, y))) {
                        return UpdateResponse.FAILED_HIT_MINE;
                    }
                }

                Set<String> keys = robots.keySet();
                for (String key : keys) {
                    if (key.equals(robotName)) {
                        continue;
                    }
                    if (robots.get(key).blocksPosition(new Position(x,y))) {
                        return UpdateResponse.FAILED_OBSTRUCTED;
                    }
                }
                if (y != b.getY()) {
                    y += incY;
                }
            } while (y != b.getY());
            if (x != b.getX()) {
                x += incX;
            }
        } while  (x != b.getX());
        return UpdateResponse.SUCCESS;
    }

    /**
     * Method will loop through the list of mines and check the position of the robot.
     * If the mine is found that the robot has triggered their health will be reduced and the mine will be removed
     * from the list of currently active mines on the field.
     * @param robotPosition position of robot on mine.
     * @param server of the robot that will be hit by the mine.
     */
    public void hitMine(Position robotPosition, Server server) {
        Iterator<Obstacle> i = this.minesList.iterator();

        while (i.hasNext()) {
            Obstacle mine = i.next();
            if (mine.blocksPosition(robotPosition)) {
                server.robot.takeDamage(3);
                i.remove();
            }
        }
    }

    @Override
    public void hitMine(Position minePosition, Robot robot) {
        Iterator<Obstacle> i = this.minesList.iterator();

        while (i.hasNext()) {
            Obstacle mine = i.next();
            if (mine.blocksPosition(minePosition)) {
                robot.takeDamage(3);
                i.remove();
            }
        }
    }

    /**
     * Method will look through all possible lists of Mines,Pits,Robots and obstacles.
     * If any of these obstacles are within this position given the method will return a relevant update response
     * for that obstacle, for example if a mine is at position given FAILED_HIT_MINE is returned.
     * @param robots map of all robots in play.
     * @param position to be checked.
     * @param robotName of robot currently checking for movement.
     * @return UpdateResponse.
     */
    public UpdateResponse blocksPosition(ConcurrentHashMap<String, Robot> robots, Position position, String robotName) {
        for (Obstacle pit : this.pitsList) {
            if (pit.blocksPosition(position))
                return UpdateResponse.FAILED_BOTTOMLESS_PIT;
        }

        for (Obstacle obst : this.obstaclesList) {
            if (obst.blocksPosition(position)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }

        for (Obstacle mine : this.minesList) {
            if (mine.blocksPosition(position)) {
                return UpdateResponse.FAILED_HIT_MINE;
            }
        }

        Set<String> keys = robots.keySet();
        for (String key : keys) {
            if (key.equals(robotName)) {
                continue;
            }
            if (robots.get(key).blocksPosition(position)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }
        }
        return UpdateResponse.SUCCESS;
    }

}