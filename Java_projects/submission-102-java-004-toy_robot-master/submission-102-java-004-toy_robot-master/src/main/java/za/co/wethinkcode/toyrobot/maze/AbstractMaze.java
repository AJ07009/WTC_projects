package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;


import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaze implements Maze{
    private List<Obstacle> obstacles;
    private String name;

    public AbstractMaze() {
        this.obstacles = new ArrayList<Obstacle>();
        this.name = "Abstract Maze";
    }

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public void addObstacles(List<Obstacle> obs) {
        this.obstacles = obs;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        if (a.getY() == b.getY()) {
            if (a.getX() > b.getX()) {
                for (int i = b.getX(); i <= a.getX(); i++) {
                    if (blocksPosition(new Position(i, b.getY())))
                        return true;
                }
            }if (a.getX() < b.getX()) {
                for (int i = a.getX(); i <= b.getX(); i++) {
                    if (blocksPosition(new Position(i, b.getY())))
                        return true;
                }
            }
            return false;
        }else if (a.getX() == b.getX()) {
            if (a.getY() > b.getY()) {
                for (int i = b.getY(); i <= a.getY(); i++)
                    if (blocksPosition(new Position(a.getX(), i)))
                        return true;
            }
        }if (a.getY() < b.getY()) {
            for (int j = a.getY(); j <= (b.getY()); j++)
                if (blocksPosition(new Position(a.getX(), j)))
                    return true;
            return false;
        }
        return false;
    }

    public boolean blocksPosition(Position position) {
        int topLeftPos = position.getY()+5;
        int bottomRightPos = position.getX()+5;

        Position topLeft = new Position(position.getX(), topLeftPos);
        Position bottomRight = new Position(bottomRightPos, position.getY());

        return position.isIn(topLeft, bottomRight);
    }

    @Override
    public String getName() {
        return this.name;
    }


    public void setName(String name) { this.name = name;}
}
