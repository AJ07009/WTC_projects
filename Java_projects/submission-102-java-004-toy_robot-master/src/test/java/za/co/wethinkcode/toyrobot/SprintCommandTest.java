package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SprintCommandTest {
    @Test
    void executeSprint() {
        Robot robot = new Robot("CrashTestDummy");
        Command sprint5 = Command.create("sprint 5");
        assertTrue(sprint5.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 15);
        assertEquals(expectedPosition, robot.getPosition());
    }

    @Test
    void getSprintName() {
        SprintCommand sprint = new SprintCommand("sprint 5");
        assertEquals("sprint",sprint.getName());
    }
}
