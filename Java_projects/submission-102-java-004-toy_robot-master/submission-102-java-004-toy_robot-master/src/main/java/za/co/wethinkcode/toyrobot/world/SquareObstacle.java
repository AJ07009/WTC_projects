package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle {

    private final int x;
    private final int y;


    public SquareObstacle(int x, int y){
        this.x= x;
        this.y = y;
    }

    @Override
    public int getBottomLeftX() {
        return this.x;
    }

    @Override
    public int getBottomLeftY() {
        return this.y;
    }

    @Override
    public int getSize() {
        return 5;
    }

    @Override
    public boolean blocksPosition(Position position) {
        boolean xOnObstacle = this.x <= position.getX() && (this.x + 4) >= position.getX();
        boolean yOnObstacle = this.y <= position.getY() && (this.y + 4) >= position.getY();
        return xOnObstacle && yOnObstacle;
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
}