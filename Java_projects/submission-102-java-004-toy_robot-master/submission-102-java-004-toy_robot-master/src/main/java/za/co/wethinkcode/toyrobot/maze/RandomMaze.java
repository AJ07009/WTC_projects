package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaze extends AbstractMaze{

    public RandomMaze() {
        super();
        super.setName("Random Maze");
        super.addObstacles(this.generateObstacles());
    }

    public List<Obstacle> generateObstacles(){
        int size, x, y;
        Random random = new Random();
        List<Obstacle> obstacles = new ArrayList<>();
        size = random.nextInt(11);

        for(int i = 0; i < size; i++){
            x = random.nextInt(201) - 100;
            y = random.nextInt(401) - 200;
            obstacles.add(new SquareObstacle(x, y));
        }

        return obstacles;
    }
}
