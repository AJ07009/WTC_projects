package za.co.wethinkcode.Server;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.World;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManagement implements Runnable {
    //Ansi escape codes to be used for pretty printing.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32;1m";
    public static final String ANSI_PURPLE = "\u001B[35;1m";
    public static final String ANSI_CYAN = "\u001B[36m";
    //Display to be drawn on for the dump command.
    private final Scanner sc;
    private final World world;
    boolean running;

    public ServerManagement(World world) {
        this.sc = new Scanner(System.in);
        this.world = world;
        running = true;
    }

    public void run() {
        //Sleep for 3 seconds due to threads printing at the same time when run on fast CPU's.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Pretty print the instructions for using the server to the server admin.
        System.out.println("Server is running and live!\n" +
                ANSI_PURPLE + "Server can issue commands using the command name.\n" +
                ANSI_GREEN +"       eg. <command> <tag>\n" + ANSI_RESET +
                ANSI_GREEN+"       <purge> <client-name>"+ANSI_RESET+" - Purges the selected user from the server.\n" +
                ANSI_GREEN+"       <clients> <>         "+ANSI_RESET+" - Lists all the currently connected users and their username.\n" +
                ANSI_GREEN+"       <robots> <>          "+ANSI_RESET+" - Lists the robots currently on the map and their states.\n" +
                ANSI_GREEN+"       <quit> <>            "+ANSI_RESET+" - Closes all currently connected clients and threads. Quits program.\n" +
                ANSI_GREEN+"       <display> <>         "+ANSI_RESET+" - Displays all saved worlds in database.\n" +
                ANSI_GREEN+"       <save> <>            "+ANSI_RESET+" - Saves the world under World01 ID.\n" +
                ANSI_GREEN+"       <save> <worldID>     "+ANSI_RESET+" - Saves the world under the given ID.\n" + 
                ANSI_GREEN+"       <restore> <>         "+ANSI_RESET+" - Restores a default world.\n" +
                ANSI_GREEN+"       <restore> <worldID>  "+ANSI_RESET+" - Restores a world with a specific WorldID.");

        while (running) {
            try {
                String serverMessage = sc.nextLine();
                //Split the console input into a string.
                List<String> inputString = Arrays.asList(serverMessage.split(" "));
                List<String> arguments = inputString.subList(1, inputString.size());

                //execute server commands that will alter the world.
                switch (inputString.get(0)) {
                    case "quit":
                        quitServer();
                        System.out.println("Quiting the server!");
                        break;
                    case "robots":
                        listRobots();
                        System.out.println("Listed robots!");
                        break;
                    case "purge":
                        if (inputString.size() > 1) {
                            purgeUser(arguments.get(0));
                            System.out.println("Purged user!");
                        }
                        break;
                    case "clients":
                        showUsers();
                        System.out.println("Showed users!");
                        break;
                    case "dump":
                        break;
//                    case "display":
//                        listWorld();
//                        break;
                    case "save":
                        if (inputString.size() > 1) {
                            saveWorld(arguments.get(0));
                        }
                        else {
                            saveWorld();
                            System.out.println("World has been saved to ROBOT Worlds DB as World01");
                        }
                        break;
                    case "restore":
                        closeConnection();
                        if (inputString.size() > 1) {
                            SQLiteManagement.restoreData(world,arguments.get(0));
                        } else {
                            SQLiteManagement.restoreData(world);
                        }
                        break;

                }
            } catch (Exception ignored) {}
        }
    }

    /**
     * Method will close all the threads currently running on the server,
     * then it will close the server completely.
     */
    private void quitServer() {
        //Loops through the client list and closes the.
        closeConnection();
        this.running = false;
        System.exit(69);
    }

    private void closeConnection() {
        try {
            for (Server client : MultiServer.clients) {
                client.closeThread();
            }
        } catch (Exception ignored) {}
    }

    /**
     * Method will display all the robots currently in play on the field,
     * as well as their current states.
     */
    private void listRobots() {
        //Creates a copy of the robots HashMap
        ConcurrentHashMap<String, Robot> robotDict = world.getRobots();
        //Create a set with the keys from the HashMap
        Set<String> robots = robotDict.keySet();

        //Loops through the list of robots and grabs the values.
        for (String key : robots) {
            Robot currentRobot = robotDict.get(key);
            JSONObject robot = currentRobot.getState();
            //Pretty prints the state of the current robot in the loop.
            System.out.println(ANSI_GREEN + "\t\t\t\tName\t\t:\t" + ANSI_CYAN + currentRobot.getName() + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\tPosition\t:\t" + ANSI_CYAN + robot.get("position") + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\tDirection\t:\t" + ANSI_CYAN + robot.get("direction") + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\tShield\t\t:\t" + ANSI_CYAN + robot.get("shields") + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\tShots\t\t:\t" + ANSI_CYAN + robot.get("shots") + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\tStatus\t\t:\t" + ANSI_CYAN + robot.get("status") + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\t\t\t\t_____\t\t:\t" + ANSI_CYAN + "_____" + ANSI_RESET);
        }
    }

    /**
     * Removes the user with the given name from the robots list as well as client list.
     * @param username that will be purged.
     */
    private void purgeUser(String username) {
        for (Server client:MultiServer.clients) {
            if (client.robotName.equalsIgnoreCase(username)) {
                client.robot.kill(world, client, "Bonk");
                break;
            }
        }
        try {
            world.removeRobot(username);
        } catch (NullPointerException ignored) {}
    }

    /**
     * Prints the currently connected clients to the console.
     */
    private void showUsers() {
        for (Server client : MultiServer.clients) {
            System.out.println(client.robotName + ": " + client);
        }
    }

    private void saveWorld(){
        Gson gson = new Gson();
        String worldID = "World01";
        String bottomRight = String.valueOf(world.BOTTOM_RIGHT);
        String topLeft = String.valueOf(world.TOP_LEFT);
        String mazeString = String.valueOf(gson.toJson(world.maze));
        Integer maxShots = world.MAX_SHOTS;
        Integer maxShields = world.MAX_SHIELDS;
        Integer reloadTime = world.RELOAD_TIME;
        Integer repairTime = world.REPAIR_TIME;
        Integer visibility = world.VISIBILITY;
        Integer mineSetTime = world.MINE_SET_TIME;
        SQLiteManagement.insert(worldID, bottomRight,topLeft,mazeString,maxShots,maxShields,reloadTime,repairTime,visibility,mineSetTime);
    }

    private void saveWorld(String worldID){
        Gson gson = new Gson();
        String bottomRight = String.valueOf(world.BOTTOM_RIGHT);
        String topLeft = String.valueOf(world.TOP_LEFT);
        String mazeString = String.valueOf(gson.toJson(world.maze));
        Integer maxShots = world.MAX_SHOTS;
        Integer maxShields = world.MAX_SHIELDS;
        Integer reloadTime = world.RELOAD_TIME;
        Integer repairTime = world.REPAIR_TIME;
        Integer visibility = world.VISIBILITY;
        Integer mineSetTime = world.MINE_SET_TIME;

        if (SQLiteManagement.checkWorldID(worldID)){
            System.out.println("Would you like to override the world saved?");
            String sqlCheck = sc.nextLine();
            if (sqlCheck.equalsIgnoreCase("Yes")){
                SQLiteManagement.insert(worldID, bottomRight,topLeft,mazeString,maxShots,maxShields,reloadTime,repairTime,visibility,mineSetTime);
                System.out.println("World has been saved to ROBOT Worlds DB as " + worldID);
            }
            else {
                System.out.println("World has not been saved, please re-run command.");
            }
        }
        else {
            SQLiteManagement.insert(worldID, bottomRight,topLeft,mazeString,maxShots,maxShields,reloadTime,repairTime,visibility,mineSetTime);
            System.out.println("World has been saved to ROBOT Worlds DB as " + worldID);
        }
    }

//    private void listWorld(){
//        List<Map<String, String>> worlds = SQLiteManagement.listWorlds();
//        for (String s : worlds.) {
//            System.out.println(s);
//        }
//    }
}
