package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class DesignedMaze extends AbstractMaze{

    public DesignedMaze() {
        super();
        super.setName("Designed Maze");
        super.addObstacles(this.generateObstacles());
    }

    public List<Obstacle> generateObstacles(){
        int size, x, y;
        List<Obstacle> obstacles = new ArrayList<>();
        size = 7;

        for(int i = 0; i < size+21; i++){
            x = 60;
            y = 120;

            obstacles.add(new SquareObstacle(x, y-5*i));
        }
        for(int i = 0; i < size+17; i++){
            x = -40;
            y = -120;

            obstacles.add(new SquareObstacle(x+5*i, y));
        }
        for(int i = 0; i < size+19; i++){
            x = -60;
            y = -120;

            obstacles.add(new SquareObstacle(x, y+5*i));
        }
        for(int i = 0; i < size+15; i++){
            x = -30;
            y = 180;

            obstacles.add(new SquareObstacle(x+5*i, y));
        }


        return obstacles;
    }
}
