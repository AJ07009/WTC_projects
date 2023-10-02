package za.co.wethinkcode.toyrobot.world;

import org.turtle.*;
import za.co.wethinkcode.toyrobot.maze.Maze;

import java.awt.*;

public class TurtleWorld extends AbstractWorld 
{
    private Turtle turtle;

    public TurtleWorld(Maze maze)
    {
        super(maze);
        turtle = new Turtle(0.0, 0.0, 0);
        turtle.setColor(Color.green);
        StdDraw.setXscale(-105, 105);
        StdDraw.setYscale(-205, 205);

        this.showObstacles();

        turtle.setColor(Color.white);
        StdDraw.point(0,0);
        StdDraw.show();
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) 
    {
        StdDraw.enableDoubleBuffering();
        UpdateResponse response = super.updatePosition(nrSteps);
        if (response.equals(UpdateResponse.SUCCESS))
        {
            //Update turtle
            StdDraw.setPenRadius(0.003);
            StdDraw.clear();
            showObstacles();
            StdDraw.point(getPosition().getX(), getPosition().getY());

            turtle.show();

            StdDraw.show();
            StdDraw.pause(150); //OPTIONAL
        }
        return response;
    }

    @Override
    public void updateDirection(boolean turnRight) 
    {
        super.updateDirection(turnRight);
        if(turnRight) turtle.right(90);
        else turtle.left(90);
    }

    @Override
    public void showObstacles() {
        StdDraw.enableDoubleBuffering();
        turtle.setSize(0.01);
        turtle.setColor(Color.blue);
        for (Obstacle obstacle: getObstacles()) 
        {
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                 {
                    turtle.forward(0.00);
                    turtle.setPosition(obstacle.getBottomLeftX() + i, obstacle.getBottomLeftY() + j);
                    turtle.left(90);
                }
            }
        }
        turtle.setColor(Color.black);
        StdDraw.disableDoubleBuffering();
    }

}
