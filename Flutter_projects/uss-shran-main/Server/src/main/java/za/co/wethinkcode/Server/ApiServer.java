package za.co.wethinkcode.Server;

import io.javalin.Javalin;
import za.co.wethinkcode.Utility.*;
import za.co.wethinkcode.World;

import java.util.List;

public class ApiServer {
    // The loaded world with the map inserted, this will hold all the robots and data.
    public static World world;
    private final Javalin server;

    public ApiServer(String[] args) {
        server = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            ArgReader arguments = new ArgReader(args);
            arguments.setConfigs();

            int worldSize = arguments.getWorldSize();
            List<int[]> obstacles = arguments.getObstacles();
            world = new World(worldSize,obstacles);
        });
        this.server.get("/world/{WorldID}", context -> ApiHandler.getOne(context, world));
        this.server.get("/world", context -> ApiHandler.getWorld(context, world));
        this.server.post("/admin/save", context -> ApiHandler.saveWorld(context,world));
        this.server.get("/admin/display", context -> ApiHandler.displayWorlds(context));
        this.server.get("/robot/{name}/get", context -> ApiHandler.getRobot(context,world));
        this.server.post("/robot/{name}",context -> ApiHandler.command(context, world));
        this.server.get("/robots", context -> ApiHandler.getRobots(context, world));
        this.server.post("/robots", context ->ApiHandler.purgeRobot(context, world));
        this.server.get("/admin/obstacles", context -> ApiHandler.getObstacles(context, world));
        this.server.post("/admin/obstacles/add", context -> ApiHandler.addObstacle(context, world));
        this.server.post("/admin/obstacles/remove", context -> ApiHandler.removingObstacle(context, world));
    }


    public static void main(String[] args){
        ApiServer api = new ApiServer(args);
        api.server.start(5000);
        System.out.println("Port that API is using: " + api.server.port());
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}
