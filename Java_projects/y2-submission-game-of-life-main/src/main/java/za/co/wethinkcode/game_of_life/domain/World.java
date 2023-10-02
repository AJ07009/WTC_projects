package za.co.wethinkcode.game_of_life.domain;

public class World {
    private int[][] cells;
    private String worldName;
    private Integer worldSize;
    private Integer worldID;

    public World() {
    }

    public Integer getWorldID() {
        return worldID;
    }

    public void setWorldID(Integer worldID) {
        this.worldID = worldID;
    }

    private World(String worldName, Integer worldSize) {
        this.worldName = worldName;
        this.worldSize = worldSize;
    }

    public static World define(String worldName, Integer worldSize, int[][] worldInitialState) {
        World world = new World(worldName, worldSize);
        world.setCells(worldInitialState);
        return world;
    }

    private void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getState() {
        return this.cells;
    }

    public String getWorldName() {
        return worldName;
    }
    
    public void setWorldName(String worldName){
        this.worldName = worldName;
    }

    public Integer getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(Integer worldSize) {
        this.worldSize = worldSize;
    }

    public void setArray(int state) {
    }
}
