package cpsc2150.extendedCheckers.models;

abstract class AbsCheckerBoard implements ICheckerBoard {

    private static int VARIABLE_DIMENSIONS;
    /**
     * an Override to the Object's toString method. Returns a string representation of the checkerboard.
     * Does NOT print!
     *
     * @return a string representation of the checkerboard with a "header" line to display all the column numbers
     * and a "header column" to display all the row numbers
     *
     * @pre none
     *
     * @post toString = a string representation of the checkerboard formatted:
     * "|  | 0| 1| 2| 3| 4| 5| 6| 7|"
     * "|0 |x |* |x |* |x |* |x |* |"
     * "|1 |* |x |* |x |* |x |* |x |"
     * "|2 |x |* |x |* |x |* |x |* |"
     * "|3 |* |  |* |  |* |  |* |  |"
     * "|4 |  |* |  |* |  |* |  |* |"
     * "|5 |* |o |* |o |* |o |* |o |"
     * "|6 |o |* |o |* |o |* |o |* |"
     * "|7 |* |o |* |o |* |o |* |o |"
     * AND viableDirections = #viableDirection AND pieceCount = #pieceCount
     */
    @Override
    public String toString()
    {
        /*
        returns a String representation of the checkerboard with all the pieces on it and their current positions. there
        should be a "header" line to display all the column numbers and a "header column" that displays all the row
        numbers. In essence, it should look like this:

        |  | 0| 1| 2| 3| 4| 5| 6| 7|
        |0 |x |* |x |* |x |* |x |* |
        |1 |* |x |* |x |* |x |* |x |
        |2 |x |* |x |* |x |* |x |* |
        |3 |* |  |* |  |* |  |* |  |
        |4 |  |* |  |* |  |* |  |* |
        |5 |* |o |* |o |* |o |* |o |
        |6 |o |* |o |* |o |* |o |* |
        |7 |* |o |* |o |* |o |* |o |

        THIS FUNCTION DOES NOT PRINT TO THE CONSOLE OR MAKE ANY KIND OF SYSTEM.OUT.PRINTLN CALLS
         */
        StringBuilder cb = new StringBuilder();

        cb.append("|").append("  |");
        for (int c = 0; c < getDimensions(); c++) {

            if(c >= 10) {
                cb.append(c).append("|");
            }
            else {
                cb.append(" ").append(c).append("|");
            }
        }
        cb.append("\n");

        for (int r = 0; r < getDimensions(); r++) {
            if(r >= 10) {
                cb.append("|").append(r).append("|");
            }
            else{
                cb.append("|").append(r).append(" |");
            }

            for (int c = 0; c < getDimensions(); c++) {
                char piece = whatsAtPos(new BoardPosition(r, c));
                cb.append(piece).append(" |");
            }
            cb.append("\n");
        }
        return cb.toString();
    }


}
