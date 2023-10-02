package za.co.wethinkcode.game_of_life;

import io.javalin.Javalin;

public class GameServer {
    private final Javalin server;
    private final int DEFAULT_PORT = 8080;

    public GameServer() {
        server = Javalin.create();
        server.get("/_ping", context -> context.result("pong"));
        // <SOLUTION>
        WorldApiHandler worldApiHandler = new WorldApiHandler();
        server.post("/world", context -> worldApiHandler.createNew(context));
        server.post("/world/{id}/next", context -> worldApiHandler.equation(context));
        // </SOLUTION>
    }

    public void start(int port) {
        int listen_port = port > 0 ? port : DEFAULT_PORT; // use port if > 0 otherwise use DEFAULT_PORT value
        this.server.start(listen_port);
    }

    public void stop() {
        this.server.stop();
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start(gameServer.DEFAULT_PORT);
    }
}

