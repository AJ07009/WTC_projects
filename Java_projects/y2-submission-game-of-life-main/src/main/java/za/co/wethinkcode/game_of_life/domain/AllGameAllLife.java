package za.co.wethinkcode.game_of_life.domain;

import za.co.wethinkcode.game_of_life.domain.World;

import java.util.Arrays;

public class AllGameAllLife {

    public int[][] nextGeneration(int grid[][], int m,int n)
    {
        int[][] future = new int[m][n];

        for (int length = 1; length < m - 1; length++)
        {
            for (int breadth = 1; breadth < n - 1; n++)
            {
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        aliveNeighbours += grid[length + i][breadth + j];

                aliveNeighbours -= grid[length][breadth];


                if ((grid[length][breadth] == 1) && (aliveNeighbours < 2))
                    future[length][breadth] = 0;

                else if ((grid[length][breadth] == 1) && (aliveNeighbours > 3))
                    future[length][breadth] = 0;

                else if ((grid[length][breadth] == 0) && (aliveNeighbours == 3))
                    future[length][breadth] = 1;

                    // Remains the same
                else
                    future[length][breadth] = grid[length][breadth];
            }
        }

        System.out.println("Next Generation");
        System.out.println(Arrays.deepToString(future));
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (future[i][j] == 0)
                    System.out.print("[0]");
                else
                    System.out.print("[1]");
            }
            System.out.println();
        }
        return future;
    }
}
