package cpsc2150.extendedCheckers.models;

public class BoardPosition {
    /**
     * Row component of the BoardPosition
     */
    private int row;

    /**
     * Column component of the BoardPosition
     */
    private int column;

    /**
     * A standard parameterized constructor for board position
     *
     * @param aRow the value to be set for the row of the board position
     * @param aCol the value to be set for the column of the board position
     * @pre 0 <= aRow <= 7 AND <= aCol <= 7
     * @post row = aRow AND col = aCol
     */
    public BoardPosition(int aRow, int aCol) {
        this.row = aRow;
        this.column = aCol;
    }
      //modified some contracts to be a little more explicit
    /**
     * Standard getter for row
     *
     * @return the row of the board position
     * @pre none
     * @post getRow = row AND row = #row AND col = #col
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Standard getter for column
     *
     * @return the column of the board position
     * @pre none
     * @post getCol = col AND col = #col AND row = #row
     */
    public int getColumn() {
        return this.column;
    }


    /**
     * an Override to the Object's equals method. Checks to see if an object is equal to this board position
     *
     * @param obj the object to check for equivalency
     * @return true or false depending on if obj and this BoardPosition are equal
     * @pre obj != null AND obj is an instance of BoardPosition AND this != obj
     * @post equals = true IFF obj and this have the same variable value OW false AND row = #row AND col = #col
     */
    public boolean equals(Object obj) {
        //while this is technically not necessary, it is a good practice to check before typecasting due to inheritance and how that works in Java
        if (obj instanceof BoardPosition) {
            BoardPosition bp = (BoardPosition) obj;
            return (this.row == bp.getRow() && this.column == bp.getColumn());
        }
        return false;
    }

    /**
     * an Override to the Object's toString method. Returns a string representation of BoardPosition. Does NOT print!
     *
     * @return a string representation of the BoardPosition
     * @pre none
     * @post toString = a string representation of the BoardPosition formatted:
     * "row,column"
     * AND row = #row AND col = #col
     */
    public String toString() {
        return this.row + "," + this.column;
    }
}