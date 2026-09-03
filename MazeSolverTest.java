import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Tests for MazeSolver.
 *
 * These tests create temporary maze files and verify the required helper
 * methods and recursive maze operations.
 *
 * @author Chai Xiong
 * @version Apr 11, 2026
 */
public class MazeSolverTest {

    /**
     * Creates a temporary maze file and returns its absolute path.
     *
     * @param contents the contents to write into the maze file
     * @return the absolute path of the temporary maze file
     * @throws IOException if the file cannot be created
     */
    private String makeMazeFile(String contents) throws IOException {
        File temp = File.createTempFile("maze", ".txt");
        temp.deleteOnExit();

        FileWriter writer = new FileWriter(temp);
        writer.write(contents);
        writer.close();

        return temp.getAbsolutePath();
    }

    /**
     * Tests that the constructor loads the maze and getSize returns the maze
     * size.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testConstructorAndGetSize() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);

        assertNotNull(maze.getGrid());
        assertEquals(2, maze.getSize());
    }

    /**
     * Tests the default start and target positions when S and T are not given.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testDefaultStartAndTarget() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);

        assertEquals(0, maze.getStartPosition().getRow());
        assertEquals(0, maze.getStartPosition().getCol());
        assertEquals(1, maze.getTargetPosition().getRow());
        assertEquals(1, maze.getTargetPosition().getCol());
    }

    /**
     * Tests the setter and getter methods for the start and target positions.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testSetStartAndTargetPosition() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);
        Position start = new Position(0, 1);
        Position target = new Position(1, 0);

        maze.setStartPosition(start);
        maze.setTargetPosition(target);

        assertEquals(start, maze.getStartPosition());
        assertEquals(target, maze.getTargetPosition());
    }

    /**
     * Tests that setGrid replaces the internal maze grid.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testSetGrid() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);

        Maze.CELL[][] g = {
                { Maze.CELL.OPEN, Maze.CELL.WALL },
                { Maze.CELL.GOLDCOIN, Maze.CELL.OPEN }
        };

        maze.setGrid(g);

        assertEquals(Maze.CELL.WALL, maze.getGrid()[0][1]);
        assertEquals(Maze.CELL.GOLDCOIN, maze.getGrid()[1][0]);
    }

    /**
     * Tests several helper methods related to position validity, target
     * detection, gold detection, and availability.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionChecks() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S G\n" +
                        "1 T\n");

        MazeSolver maze = new MazeSolver(file);

        assertTrue(maze.positionIsValid(new Position(0, 0)));
        assertFalse(maze.positionIsValid(null));
        assertFalse(maze.positionIsValid(new Position(-1, 0)));
        assertFalse(maze.positionIsValid(new Position(2, 0)));

        assertTrue(maze.positionHasGold(new Position(0, 1)));
        assertFalse(maze.positionHasGold(new Position(1, 1)));

        assertTrue(maze.positionIsAvailable(new Position(0, 1)));
        assertTrue(maze.positionIsAvailable(new Position(1, 0)));
        assertFalse(maze.positionIsAvailable(new Position(5, 5)));

        assertTrue(maze.positionIsTarget(new Position(1, 1)));
        assertFalse(maze.positionIsTarget(new Position(0, 0)));
    }

    /**
     * Tests that positionIsTarget returns false for a null position.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsTargetNull() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        assertFalse(maze.positionIsTarget(null));
    }

    /**
     * Tests that positionIsTarget returns false for a non-target position.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsTargetFalse() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        assertFalse(maze.positionIsTarget(new Position(0, 1)));
    }

    /**
     * Tests that positionIsTarget returns true for the target position.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsTargetTrue() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        assertTrue(maze.positionIsTarget(new Position(1, 1)));
    }

    /**
     * Tests that positionHasGold returns false for an invalid position.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionHasGoldInvalid() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "1 1\n"));

        assertFalse(maze.positionHasGold(new Position(5, 5)));
    }

    /**
     * Tests that positionHasGold returns false when the cell has no gold.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionHasGoldFalse() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "1 1\n"));

        assertFalse(maze.positionHasGold(new Position(0, 0)));
    }

    /**
     * Tests that positionHasGold returns true for a gold coin cell.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionHasGoldTrue() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "1 1\n"));

        assertTrue(maze.positionHasGold(new Position(0, 1)));
    }

    /**
     * Tests that an invalid position is not considered available.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsAvailableInvalid() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "0 1\n"));

        assertFalse(maze.positionIsAvailable(new Position(-1, 0)));
    }

    /**
     * Tests that a wall cell is not considered available.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsAvailableWall() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "0 1\n"));

        assertFalse(maze.positionIsAvailable(new Position(1, 0)));
    }

    /**
     * Tests that an open cell is considered available.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsAvailableOpen() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "0 1\n"));

        assertTrue(maze.positionIsAvailable(new Position(0, 0)));
    }

    /**
     * Tests that a gold coin cell is considered available.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testPositionIsAvailableGold() throws IOException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 G\n" +
                        "0 1\n"));

        assertTrue(maze.positionIsAvailable(new Position(0, 1)));
    }

    /**
     * Tests marking cells as visited and path.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testMarkMethods() throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);

        maze.markAsVisited(new Position(0, 0));
        assertEquals(Maze.CELL.VISITED, maze.getGrid()[0][0]);

        maze.markAsPath(new Position(1, 1));
        assertEquals(Maze.CELL.PATH, maze.getGrid()[1][1]);
    }

    /**
     * Tests that markAsVisited throws an exception for an invalid position.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if the invalid call is made
     */
    @Test(expected = IllegalAccessException.class)
    public void testMarkAsVisitedInvalid()
            throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);
        maze.markAsVisited(new Position(5, 5));
    }

    /**
     * Tests that markAsPath throws an exception for an invalid position.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if the invalid call is made
     */
    @Test(expected = IllegalAccessException.class)
    public void testMarkAsPathInvalid()
            throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);
        maze.markAsPath(new Position(-1, 0));
    }

    /**
     * Tests that traverse succeeds on a simple solvable maze.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseSuccess() throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S 1\n" +
                        "1 T\n");

        MazeSolver maze = new MazeSolver(file);

        assertTrue(maze.traverse());
        assertEquals(Maze.CELL.PATH, maze.getGrid()[1][1]);
    }

    /**
     * Tests that traverse fails on a blocked maze.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseFailure() throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S 0\n" +
                        "0 T\n");

        MazeSolver maze = new MazeSolver(file);

        assertFalse(maze.traverse());
    }

    /**
     * Tests traverse starting from a specific position.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseFromPosition()
            throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "3\n" +
                        "grid\n" +
                        "S 1 0\n" +
                        "1 1 1\n" +
                        "0 1 T\n");

        MazeSolver maze = new MazeSolver(file);

        assertTrue(maze.traverse(new Position(0, 0)));
    }

    /**
     * Tests that traverse returns false when started on a wall.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseOnWallReturnsFalse()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "0 1\n" +
                        "1 1\n"));

        assertFalse(maze.traverse(new Position(0, 0)));
    }

    /**
     * Tests that traverse returns false when started on a visited cell.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseOnVisitedReturnsFalse()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        maze.markAsVisited(new Position(0, 0));
        assertFalse(maze.traverse(new Position(0, 0)));
    }

    /**
     * Tests that traverse returns false when started on a path cell.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseOnPathReturnsFalse()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        maze.markAsPath(new Position(0, 0));
        assertFalse(maze.traverse(new Position(0, 0)));
    }

    /**
     * Tests traversal when the first successful recursive move is to the right.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseSucceedsGoingRight()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S T\n" +
                        "0 0\n"));

        assertTrue(maze.traverse());
    }

    /**
     * Tests traversal when the first successful recursive move is downward.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testTraverseSucceedsGoingDown()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S 0\n" +
                        "T 0\n"));

        assertTrue(maze.traverse());
    }

    /**
     * Tests that pickupGoldCoins collects all reachable gold in a small maze.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testPickupGoldCoins() throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S G\n" +
                        "G G\n");

        MazeSolver maze = new MazeSolver(file);

        assertEquals(3, maze.pickupGoldCoins());
        assertEquals(Maze.CELL.VISITED, maze.getGrid()[0][0]);
    }

    /**
     * Tests pickupGoldCoins starting from a specific position.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testPickupGoldCoinsFromPosition()
            throws IOException, IllegalAccessException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "S G\n" +
                        "G G\n");

        MazeSolver maze = new MazeSolver(file);

        assertEquals(3, maze.pickupGoldCoins(new Position(0, 0)));
    }

    /**
     * Tests that pickupGoldCoins returns zero when started on a wall.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testPickupGoldCoinsOnWallReturnsZero()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "0 1\n" +
                        "1 1\n"));

        assertEquals(0, maze.pickupGoldCoins(new Position(0, 0)));
    }

    /**
     * Tests that pickupGoldCoins returns zero when started on a visited cell.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testPickupGoldCoinsOnVisitedReturnsZero()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        maze.markAsVisited(new Position(0, 0));
        assertEquals(0, maze.pickupGoldCoins(new Position(0, 0)));
    }

    /**
     * Tests that pickupGoldCoins returns zero when started on a path cell.
     *
     * @throws IOException            if the temporary file cannot be created
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Test
    public void testPickupGoldCoinsOnPathReturnsZero()
            throws IOException, IllegalAccessException {
        MazeSolver maze = new MazeSolver(makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n"));

        maze.markAsPath(new Position(0, 0));
        assertEquals(0, maze.pickupGoldCoins(new Position(0, 0)));
    }

    /**
     * Tests that toString returns a non-null string.
     *
     * @throws IOException if the temporary file cannot be created
     */
    @Test
    public void testToString() throws IOException {
        String file = makeMazeFile(
                "size\n" +
                        "2\n" +
                        "grid\n" +
                        "1 1\n" +
                        "1 1\n");

        MazeSolver maze = new MazeSolver(file);

        assertNotNull(maze.toString());
    }
}