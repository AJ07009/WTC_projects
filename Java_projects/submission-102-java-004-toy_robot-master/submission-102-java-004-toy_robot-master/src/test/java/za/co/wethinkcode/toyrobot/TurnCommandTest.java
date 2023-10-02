package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.world.IWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TurnCommandTest {
    @Test
    void executeLeft() {
        Robot robot = new Robot("CrashTestDummy");
        Command left = Command.create("left");
        assertTrue(left.execute(robot));
        IWorld.Direction direction = robot.getCurrentDirection();
        assertEquals(direction, IWorld.Direction.LEFT);
        assertEquals("Turned left.", robot.getStatus());
    }

    @Test
    void executeRight() {
        Robot robot = new Robot("CrashTestDummy");
        Command right = Command.create("right");
        assertTrue(right.execute(robot));
        IWorld.Direction direction = robot.getCurrentDirection();
        assertEquals(direction, IWorld.Direction.RIGHT);
        assertEquals("Turned right.", robot.getStatus());
    }
}
