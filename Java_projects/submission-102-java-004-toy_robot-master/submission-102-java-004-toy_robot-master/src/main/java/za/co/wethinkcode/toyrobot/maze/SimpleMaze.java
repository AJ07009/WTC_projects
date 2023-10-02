package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class SimpleMaze extends AbstractMaze{

    public SimpleMaze() {
        super();
        super.setName("Simple Maze");
        super.addObstacles(this.generateObstacles());
    }

    private List<Obstacle> generateObstacles() {
        SquareObstacle center = new SquareObstacle(1, 1);
        List<Obstacle> obs = new ArrayList<>();
        obs.add(center);
        return obs;
    }
}
