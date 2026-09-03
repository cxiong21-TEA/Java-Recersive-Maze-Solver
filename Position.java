/**
 * Position represents a row and column location in a maze.
 * It is used to identify specific cells during maze traversal.
 *
 * @author Chai Xiong
 * @version Apr 11, 2026
 */
public class Position {
    private int row;
    private int col;

    /**
     * Constructs a Position with the given row and column.
     *
     * @param row the row value
     * @param col the column value
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of this position.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of this position.
     *
     * @return the column value
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns true if the given object is a Position with the same row and
     * column values as this object.
     *
     * @param arg the object to compare
     * @return true if the positions are equal in value
     */
    public boolean equal(Object arg) {
        if (!(arg instanceof Position)) {
            return false;
        }

        Position other = (Position) arg;
        return row == other.row && col == other.col;
    }

    /**
     * Compares this position with another object for equality.
     *
     * @param arg the object to compare
     * @return true if the object is an equal Position
     */
    @Override
    public boolean equals(Object arg) {
        return equal(arg);
    }

    /**
     * Returns a string representation of this position.
     *
     * @return the position in the form (row, col)
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}