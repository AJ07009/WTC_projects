package za.co.wethinkcode.Map;

import za.co.wethinkcode.Robot.Position;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Robot.UpdateResponse;
import za.co.wethinkcode.Server.Server;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface to represent a maze. A World will be loaded with a Maze, and will delegate the work to check if a path is blocked by certain obstacles etc to this maze instance.
 */
public interface Maze {
    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    Vector<Obstacle> getObstacles();

    Vector<Obstacle> getPits();

    Vector<Obstacle> getMines();

    void hitMine(Position minePosition, Server server);

    void hitMine(Position minePosition, Robot robot);

    void createMine(Position position);

    void createObstacles(Position position);

    void removeObstacle(Obstacle obstacle);
    /**
     * Checks if this maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     * @param a first position
     * @param b second position
     * @return `true` if there is an obstacle is in the way
     */
    UpdateResponse blocksPath(Position a, Position b, ConcurrentHashMap<String, Robot> robots, String robotName);

    UpdateResponse blocksPosition(ConcurrentHashMap<String, Robot> robots, Position position, String robotName);

    void setObstacles(Vector<Obstacle> obstacleList);
    
}
