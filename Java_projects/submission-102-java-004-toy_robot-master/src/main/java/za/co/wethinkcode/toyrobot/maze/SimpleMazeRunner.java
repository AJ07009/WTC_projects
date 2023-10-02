package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.*;
import za.co.wethinkcode.toyrobot.world.IWorld;


import java.util.ArrayList;
import java.util.List;


public class SimpleMazeRunner implements MazeRunner {
    private int cost;

    @Override
    public boolean mazeRun(Robot target, IWorld.Direction edgeDirection) {
        
        this.cost = 0;
        boolean run = true;
        List<Position> visited = new ArrayList<>();
        
        turnToDir(target, edgeDirection);
        while (run) {
            Command step = new ForwardCommand("1");
            target.handleCommand(step);
            this.cost++;
            switch (edgeDirection) {
                case UP:
                    if (target.getPosition().getY() == 200) run = false;
                    break;
                case RIGHT:
                    if (target.getPosition().getX() == 100) run = false;
                    break;
                case DOWN:
                    if (target.getPosition().getY() == -200) run = false;
                    break;
                case LEFT:
                    if (target.getPosition().getX() == -100) run = false;
                    break;
            }
            if (target.getObstructed()) {
                this.turn(target);
                this.cost++;
            }
            for (Position pos : visited) {
                if (target.getPosition().equals(pos)) {
                    this.turn(target);
                    this.cost++;
                    break;
                }
            }
            visited.add(target.getPosition());
            Play.printStatus(target.toString());
        }
        String edge = (edgeDirection.equals(IWorld.Direction.UP))?"top":
                (edgeDirection.equals(IWorld.Direction.DOWN))?"bottom":
                        (edgeDirection.equals(IWorld.Direction.LEFT))?"left":"right";

        target.setStatus("I am at the "+edge+" edge. (Cost: "+visited.size()+" steps)");
        return true;
    }

    @Override
    public int getMazeRunCost() {
        return this.cost;
    }

    private void turn(Robot target) {
        Position newPosLeft = new Position(target.getPosition().getX() - 5, target.getPosition().getY());
        Position current = target.getPosition();
        Command turn = new RightCommand();
        if (target.getMaze().blocksPath(current, newPosLeft)) {
            turn = new RightCommand();
        }
        target.handleCommand(turn);
    }

    private void turnToDir(Robot target, IWorld.Direction edgeDirection) {
        while (!target.getDirection().equals(edgeDirection)) {
            target.handleCommand(new RightCommand());
        }
    }
}
