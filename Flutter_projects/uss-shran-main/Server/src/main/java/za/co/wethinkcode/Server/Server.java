package za.co.wethinkcode.Server;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import za.co.wethinkcode.Commands.Command;
import za.co.wethinkcode.Robot.Robot;
import za.co.wethinkcode.Utility.ResponseBuilder;
import za.co.wethinkcode.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings({"unchecked", "RedundantCollectionOperation"})
public class Server implements Runnable {

    private boolean running;
    public final Socket socket;
    public final BufferedReader in;
    public final PrintStream out;
    public World world;
    public Robot robot;
    public String robotName;
    public ResponseBuilder response;

    public Server(Socket socket, World world) throws IOException {
        // Constructor for the class will create the in and out streams.
        this.socket = socket;
        String clientMachine = socket.getInetAddress().getHostName();
        System.out.println(ServerManagement.ANSI_GREEN+"Connection from " + ServerManagement.ANSI_RESET + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");
        running = true;
        this.world = world;
    }

    public void run() {
        try {
            String messageFromClient;
            while(robot == null) {
                messageFromClient = in.readLine();
                handleMessageBeforeLaunch(messageFromClient);
            }

            MultiServer.clients.add(this);
            while(running) {
                messageFromClient = in.readLine();
                handleClientMessage(messageFromClient);
            }
        } catch(IOException | NullPointerException  ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    /**
     * Method will handle the input from the connected client before any robot has been launched.
     * No fields will be set or allow any commands to be issued until launch is used.
     * @param messageFromClient JsonString from client.
     */
    private void handleMessageBeforeLaunch(String messageFromClient) {
        JSONObject jsonMessage = (JSONObject)JSONValue.parse(messageFromClient);
        printClientMessage(jsonMessage);

        this.robotName = (String)jsonMessage.get("robot");

        ResponseBuilder response = new ResponseBuilder();
        Robot robot = new Robot(this.robotName);

        try {
            Command command = Command.create(jsonMessage,false);
            response = world.handleCommand(command, robot);

            if (world.getRobot(this.robotName) != null) response.add("state", robot.getState());

        }catch (IllegalArgumentException e) {
            response.add("result","ERROR");
            JSONObject data = new JSONObject();
            data.put("message","Unsupported command");
            response.addData(data);
        }
        if(world.getRobot(this.robotName) != null) this.robot = world.getRobot(this.robotName);
        out.println(response.toString());
    }

    /**
     * Method will handle the input from the clients once a robot is instantiated.
     * This will handle calling all the commands the client sends through as well as error handling.
     * @param messageFromClient JsonString from client.
     */
    private void handleClientMessage(String messageFromClient) {
        JSONObject jsonMessage = (JSONObject)JSONValue.parse(messageFromClient);
        this.robotName = (String)jsonMessage.get("robot");
        ResponseBuilder response = new ResponseBuilder();
        try {
            if(robot.getStatus().equals("NORMAL")) {
                Command command = Command.create(jsonMessage,true);
                response = world.handleCommand(command, this.robot);
            } else {
                jsonMessage.put("command", "state");
                Command command = Command.create(jsonMessage,true);
                response = world.handleCommand(command, this.robot);
            }
            response.add("state", this.robot.getState());
        } catch (IllegalArgumentException e) {
            response.add("result","ERROR");
            JSONObject data = new JSONObject();
            data.put("message","Unsupported command");
            response.addData(data);
        }
        System.out.println(response.toString());
        out.println(response.toString());
    }

    /**
     * Sets running to false to end the Run method.
     * Calls the close Quietly command to end the threads process.
     */
    public void closeThread() {
        this.running = false;
        closeQuietly();
    }

    /**
     * Closes all the streams for the current thread.
     *
     */
    @SuppressWarnings("CatchMayIgnoreException")
    private void closeQuietly() {
        try {
            socket.close();
            in.close(); out.close();

            MultiServer.clients.remove(MultiServer.clients.indexOf(this));
            System.out.println(this.robotName + ": has left the server");

            if (this.robot != null) {
                world.removeRobot(this.robotName);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch (IndexOutOfBoundsException ex) {}
    }

    /**
     * Prints the currently received request from the client.
     * @param message from client on specific socket.
     */
    private void printClientMessage(JSONObject message) {
        System.out.println("\u001b[33;1m"+"Message from  : "+"\u001B[0m"+ this.robotName+"\n"+
                ServerManagement.ANSI_PURPLE + "\t\t\tName\t\t:\t" +ServerManagement.ANSI_CYAN + message.get("robot") + ServerManagement.ANSI_RESET +"\n"+
                ServerManagement.ANSI_PURPLE + "\t\t\tArguments\t:\t" +ServerManagement.ANSI_CYAN + message.get("arguments") + ServerManagement.ANSI_RESET +"\n"+
                ServerManagement.ANSI_PURPLE + "\t\t\tCommand\t:\t" +ServerManagement.ANSI_CYAN + message.get("command") + ServerManagement.ANSI_RESET);
    }
}
