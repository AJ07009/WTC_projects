/***************************************************************************************
 * Contributors                                                                        *
 *                                                                                     *
 * jtrichardt, tpema, agioio, zsaloogee, ipretori, mrawoot, nkhan, kdennis, ajosias    *
 *                                                                                     *
 ***************************************************************************************/

 package za.co.wethinkcode.toyrobot;

import org.turtle.Turtle;
import za.co.wethinkcode.toyrobot.maze.*;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;

import java.util.Scanner;

public class Play {
    static Scanner scanner;
    static Turtle turtle;
    static Maze maze;
    static boolean tur = false;
    static AbstractWorld world;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Robot robot;
        Maze maze = null;
        int index = 0;


        //check if > 0
        //check ig first turtle or text.
        //use index var to check for maze
        if (args.length > 0) {
            if (args[0].toLowerCase().equals("turtle")) {
                index = 1;
                tur = true;
                //set robot world turtle
            } else if (args[0].toLowerCase().equals("text")) {
                index = 1;
//                set robot world text
            }
            //look for maze
            try {
                if (args[index].toLowerCase().equals("turtle")) {
                    tur = true;
                }
                switch (args[1].toLowerCase()) {
                    case "emptymaze":
                        maze = new EmptyMaze();
                        break;
                    case "simplemaze":
                        maze = new SimpleMaze();
                        break;
                    case "designedmaze":
                        maze = new DesignedMaze();
                        break;
                    default:
                        //nomal
                        maze = new RandomMaze();
                }

            } catch (Exception e) {
                maze = new RandomMaze();
            }
        } else {
            //randommaze
            maze = new RandomMaze();
        }
        if (tur) {
            world = new TurtleWorld(maze);
        } else {
            world = new TextWorld(maze);
        }


        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);
        robot.setWorld(world);
        System.out.println("Hello Kiddo!");
        System.out.printf("Loaded %s%n", maze.getName());
        world.showObstacles();
        System.out.println(robot.toString());
        robot.setMaze(maze);

        Command command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }
            System.out.println(robot);
        } while (shouldContinue);

    }

    public static void printStatus(String status) {
        System.out.println(status);
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}
