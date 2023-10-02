package za.co.wethinkcode.Map;

import java.util.ArrayList;
import java.util.List;

import za.co.wethinkcode.Robot.Position;

@SuppressWarnings("unused")
public class EmptyMaze extends BaseMaze {
//    private int upperX = 0;
//    private int upperY = 0;
//    private int lowerX = 0;
//    private int lowerY = 0;

    /**
     * Constructor for the empty maze.
     * it has a string whatIDo which is an easter EGG!!! Please don't mark me down.
     * */
    public EmptyMaze() {
        
    }

    public EmptyMaze(Position tOP_LEFT, Position bOTTOM_RIGHT) {
//        this.upperX = bOTTOM_RIGHT.getX();
//        this.lowerX = tOP_LEFT.getX();
//        this.upperY = tOP_LEFT.getY();
//        this.lowerY = bOTTOM_RIGHT.getY();
    }

    public EmptyMaze(Position tOP_LEFT, Position bOTTOM_RIGHT, List<int[]> obstacles) {
//        this.upperX = bOTTOM_RIGHT.getX();
//        this.lowerX = tOP_LEFT.getX();
//        this.upperY = tOP_LEFT.getY();
//        this.lowerY = bOTTOM_RIGHT.getY();
        generateObstacles(obstacles);
    }

    private void generateObstacles(List<int[]> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            int[] currentObs = obstacles.get(i);
            int x = currentObs[0];
            int y = currentObs[1];
            createObstacles(new Position(x, y));
        }
    }
}