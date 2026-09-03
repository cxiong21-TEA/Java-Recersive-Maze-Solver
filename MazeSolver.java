/**
 * MazeSolver solver code, based on code by Lewis and Chase.
 * Uses recursion to solve the maze and collect gold coins.
 */
public class MazeSolver extends Maze {
    private CELL[][] grid;
    private Position startPosition;
    private Position targetPosition;

    /**
     * Constructs a MazeSolver from the given input file.
     *
     * @param inputFile the maze file to read
     */
    public MazeSolver(String inputFile) {
        try {
            readFile(inputFile);
        } catch (Exception e) {
            throw new RuntimeException("Could not read maze file: " + inputFile, e);
        }
    }

    /**
     * Returns the internal maze grid.
     *
     * @return the grid
     */
    @Override
    public CELL[][] getGrid() {
        return grid;
    }

    /**
     * Sets the internal maze grid.
     *
     * @param g the new grid
     */
    @Override
    public void setGrid(CELL[][] g) {
        grid = g;
    }

    /**
     * Returns the size of the maze.
     *
     * @return the size of the square maze
     */
    @Override
    public int getSize() {
        return grid.length;
    }

    /**
     * Sets the start position.
     *
     * @param st the start position
     */
    @Override
    public void setStartPosition(Position st) {
        startPosition = st;
    }

    /**
     * Returns the start position.
     *
     * @return the start position
     */
    @Override
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the target position.
     *
     * @param ed the target position
     */
    @Override
    public void setTargetPosition(Position ed) {
        targetPosition = ed;
    }

    /**
     * Returns the target position.
     *
     * @return the target position
     */
    @Override
    public Position getTargetPosition() {
        return targetPosition;
    }

    /**
     * Returns true if the given position is the target.
     *
     * @param pos the position to check
     * @return true if the position is the target
     */
    @Override
    public boolean positionIsTarget(Position pos) {
        return pos != null && pos.equal(targetPosition);
    }

    /**
     * Returns true if the position is valid in the maze.
     *
     * @param pos the position to check
     * @return true if the position is inside the grid
     */
    @Override
    public boolean positionIsValid(Position pos) {
        if (pos == null) {
            return false;
        }

        int row = pos.getRow();
        int col = pos.getCol();

        return row >= 0 && row < getSize()
                && col >= 0 && col < getSize();
    }

    /**
     * Returns true if the position has a gold coin.
     *
     * @param pos the position to check
     * @return true if the position contains a gold coin
     */
    @Override
    public boolean positionHasGold(Position pos) {
        if (!positionIsValid(pos)) {
            return false;
        }

        return grid[pos.getRow()][pos.getCol()] == CELL.GOLDCOIN;
    }

    /**
     * Returns true if the position is available.
     * A position is available if it is OPEN or GOLDCOIN.
     *
     * @param pos the position to check
     * @return true if the position is available
     */
    @Override
    public boolean positionIsAvailable(Position pos) {
        if (!positionIsValid(pos)) {
            return false;
        }

        CELL value = grid[pos.getRow()][pos.getCol()];
        return value == CELL.OPEN || value == CELL.GOLDCOIN;
    }

    /**
     * Marks a position as visited.
     *
     * @param pos the position to mark
     * @throws IllegalAccessException if the position is invalid
     */
    @Override
    public void markAsVisited(Position pos) throws IllegalAccessException {
        if (!positionIsValid(pos)) {
            throw new IllegalAccessException("Invalid position");
        }

        grid[pos.getRow()][pos.getCol()] = CELL.VISITED;
    }

    /**
     * Marks a position as part of the path.
     *
     * @param pos the position to mark
     * @throws IllegalAccessException if the position is invalid
     */
    @Override
    public void markAsPath(Position pos) throws IllegalAccessException {
        if (!positionIsValid(pos)) {
            throw new IllegalAccessException("Invalid position");
        }

        grid[pos.getRow()][pos.getCol()] = CELL.PATH;
    }

    /**
     * Recursively traverses the maze to find the target.
     *
     * @param p the current position
     * @return true if a path to the target is found
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Override
    public boolean traverse(final Position p) throws IllegalAccessException {
        if (!positionIsValid(p)) {
            return false;
        }

        CELL current = grid[p.getRow()][p.getCol()];

        if (current == CELL.WALL || current == CELL.VISITED || current == CELL.PATH) {
            return false;
        }

        markAsVisited(p);

        if (positionIsTarget(p)) {
            markAsPath(p);
            return true;
        }

        Position down = new Position(p.getRow() + 1, p.getCol());
        Position right = new Position(p.getRow(), p.getCol() + 1);
        Position up = new Position(p.getRow() - 1, p.getCol());
        Position left = new Position(p.getRow(), p.getCol() - 1);

        if (traverse(down) || traverse(right) || traverse(up) || traverse(left)) {
            markAsPath(p);
            return true;
        }

        return false;
    }

    /**
     * Recursively traverses the maze and counts all reachable gold coins.
     *
     * @param p the current position
     * @return the number of reachable gold coins
     * @throws IllegalAccessException if an invalid position is marked
     */
    @Override
    public int pickupGoldCoins(final Position p) throws IllegalAccessException {
        if (!positionIsValid(p)) {
            return 0;
        }

        CELL current = grid[p.getRow()][p.getCol()];

        if (current == CELL.WALL || current == CELL.VISITED || current == CELL.PATH) {
            return 0;
        }

        int coins = 0;
        if (current == CELL.GOLDCOIN) {
            coins = 1;
        }

        markAsVisited(p);

        Position down = new Position(p.getRow() + 1, p.getCol());
        Position right = new Position(p.getRow(), p.getCol() + 1);
        Position up = new Position(p.getRow() - 1, p.getCol());
        Position left = new Position(p.getRow(), p.getCol() - 1);

        return coins
                + pickupGoldCoins(down)
                + pickupGoldCoins(right)
                + pickupGoldCoins(up)
                + pickupGoldCoins(left);
    }
}