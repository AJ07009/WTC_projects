package za.co.wethinkcode.Map;

import za.co.wethinkcode.Robot.Position;

import java.util.Random;

public class RandomMaze extends BaseMaze {
    private final Random rand = new Random();
    private final int numberOfObs;
    private final int numberOfPits;
    private final int upperX;
    private final int upperY;
    private final int lowerX;
    private final int lowerY;
    private final int maxX;
    private final int maxY;

    /**
     * Constructor for the random maze.
     * */
    public RandomMaze(Position TOP_LEFT, Position BOTTOM_RIGHT) {
        numberOfObs = ((TOP_LEFT.getY()*2)+(BOTTOM_RIGHT.getX()*2))/20;
        numberOfPits = ((TOP_LEFT.getY()*2)+(BOTTOM_RIGHT.getX()*2))/20;
        this.upperX = BOTTOM_RIGHT.getX()-4;
        this.upperY = TOP_LEFT.getY()-4;
        this.lowerX = TOP_LEFT.getX();
        this.lowerY = BOTTOM_RIGHT.getY();
        this.maxX = BOTTOM_RIGHT.getX();
        this.maxY = TOP_LEFT.getY();
        generateList();
    }

    /**
     * getter for numberOfObs
     * */
    public int getNumberOfObs() {
        return this.numberOfObs;
    }

    /**
     * Checks if it overlaps the start (from -5 to 0) as we start at 0,0 and the square is 5 long.
     * @param bottomLeftX: the bottomleft X co-ordinate.
     * @param bottomLeftY: the bottomleft Y co-ordinate.
     * @return: true if overlaps start.
     * */
    public boolean overLappingStart(int bottomLeftX, int bottomLeftY) {
        return (bottomLeftX < -5 || bottomLeftX >= 0) || (bottomLeftY < -5 || bottomLeftY >= 0);
    }

    /**
     * Checks if it overlaps another obstacle in its positions. checking each of the position (so that nothing at all overlaps.
     * @param bottomLeftX: the bottomleft X co-ordinate.
     * @param bottomLeftY: the bottomleft Y co-ordinate.
     * @return: true if it overlaps an obstacle.
     * */
    public boolean overLappingObs(int bottomLeftX, int bottomLeftY) {
        for (Obstacle obstacle : this.getObstacles()) {
            if ((obstacle.blocksPosition(new Position(bottomLeftX, bottomLeftY))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX + 4, bottomLeftY))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX, bottomLeftY + 4))) ||
                    (obstacle.blocksPosition(new Position(bottomLeftX + 4, bottomLeftY + 4)))){
                return false;
            }
        }
        return true;
    }

    /**
     * Generate a random integer between bounds of the X or Y,
     * @param upper: highest possibly point of an obstacle;
     * @param lower: lowest possibly point of an obstacle;
     * @param max: highest possible point of the entire side;
     * @return: the integer of the next random int.
     * */
    public int generateRandomNumber(int upper,int lower,int max) {
        return rand.nextInt(upper-(lower)) - max;
    }

    /**
     * Generate a random number of obstacles up to 100,
     * then create a bottom left x and y co-ord for the obstacle,
     * check if that new obstacle is not overlapping the start nor overlapping another obstacle.
     * create the obstacle if it does not overlap
     * */
    public void generateList() {
        int counter = 0;
        while (counter != getNumberOfObs()) {
            int bottomLeftX = generateRandomNumber(upperX,lowerX,maxX);
            int bottomLeftY = generateRandomNumber(upperY,lowerY,maxY);
            if (overLappingStart(bottomLeftX, bottomLeftY) && overLappingObs(bottomLeftX, bottomLeftY)) {
                createObstacles(new Position(bottomLeftX, bottomLeftY));
                counter++;
            }
        }
        while (pitsList.size() != numberOfPits) {
            int bottomLeftX = generateRandomNumber(upperX,lowerX,maxX);
            int bottomLeftY = generateRandomNumber(upperY,lowerY,maxY);
            if (overLappingStart(bottomLeftX, bottomLeftY) && overLappingObs(bottomLeftX, bottomLeftY)) {
                createPit(new Position(bottomLeftX, bottomLeftY));
            }
        }
    }
}