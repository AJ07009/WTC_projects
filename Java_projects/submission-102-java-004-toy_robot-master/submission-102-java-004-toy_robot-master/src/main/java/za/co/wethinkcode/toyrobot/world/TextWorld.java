package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.maze.Maze;



public class TextWorld extends AbstractWorld 
{

    public TextWorld(Maze maze)
    {
        super(maze);
    }

    @Override
    public void showObstacles() 
    {
        System.out.println(this.toString());
    }
}
