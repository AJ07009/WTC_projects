package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void shouldKnowXandY() {
        Position p = new Position(10, 20);
        assertEquals(10, p.getX());
        assertEquals(20, p.getY());

    }
    @Test
    public void equality() {
        assertEquals(new Position(-44, 63), new Position(-44, 63));
        assertNotEquals(new Position(-44, 63), new Position(0, 63));
        assertNotEquals(new Position(-44, 63), new Position(-44, 0));
        assertNotEquals(new Position(-44, 63), new Position(0, 0));
    }
    @Test
        public void insideRectangularRegion() {
        
    }
}
