 package za.co.wethinkcode.toyrobot;

public enum Direction 
{
    UP,
    RIGHT,
    DOWN,
    LEFT;

    static public final Direction[] values = values();

    public Direction prev() {
        return values[(ordinal() - 1  + values.length) % values.length];
    }

    public Direction next() {
        return values[(ordinal() + 1) % values.length];
    }
}
