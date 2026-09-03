import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the Position class.
 *
 * @author Chai Xiong
 * @version Apr 11, 2026
 */
public class PositionTest {

    /**
     * Tests that the constructor stores the row and column correctly.
     */
    @Test
    public void testConstructorAndGetters() {
        Position p = new Position(3, 4);
        assertEquals(3, p.getRow());
        assertEquals(4, p.getCol());
    }

    /**
     * Tests that equal returns true for matching positions.
     */
    @Test
    public void testEqualTrue() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertTrue(p1.equal(p2));
    }

    /**
     * Tests that equal returns false for different positions.
     */
    @Test
    public void testEqualFalse() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 1);
        assertFalse(p1.equal(p2));
    }

    /**
     * Tests that equal returns false for a non-Position object.
     */
    @Test
    public void testEqualWithNonPositionObject() {
        Position p = new Position(1, 2);
        assertFalse(p.equal("hello"));
    }

    /**
     * Tests that equals delegates to value equality.
     */
    @Test
    public void testEqualsOverride() {
        Position p1 = new Position(5, 6);
        Position p2 = new Position(5, 6);
        assertTrue(p1.equals(p2));
    }

    /**
     * Tests the string form of a position.
     */
    @Test
    public void testToString() {
        Position p = new Position(10, 20);
        assertEquals("(10, 20)", p.toString());
    }
}