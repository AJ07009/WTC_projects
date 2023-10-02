package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackCommandTest {
    @Test
    void getBackName() {
        BackCommand test = new BackCommand("100");
        assertEquals("back", test.getName());
        assertEquals("100", test.getArgument());
    }

    @Test
    void executeBack() {
        Robot robot = new Robot("Test");
        Command back10 = Command.create("back 10");
        assertTrue(back10.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() - 10);
        assertEquals(expectedPosition, robot.getPosition());
        assertEquals("Moved back by 10 steps.", robot.getStatus());
    }@Test
    void getForwardName() {
        ForwardCommand test = new ForwardCommand("100");
        assertEquals("forward", test.getName());
        assertEquals("100", test.getArgument());
    }
}
