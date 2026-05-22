package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CheckerBoard is an extension for AbsCheckersBoard. CheckerBoards are objects
 * that have a square grid of BoardPositions, with the length being equal to BOARD_DIMENSIONS.
 *
 * @initialization_ensures:
 *      self is initialized
 *      board is initialized in either a 8x8, 10x10, 12x12, 14x14, or 16x16 grid
 *      pieces placed in starting position
 *      piece counts set correctly for each player
 *      directions are set for each player type
 *
 *
 * @defines:
 *      self: The CheckerBoard object
 *      pieceCount: the total number of pieces each player has
 *      ViableDirections: the directions that a player can move in
 *
 * @constraints
 *          self != null AND pieceCount > 0 AND ViableDirections = [the directions that a player's pieces can move in]
 */
public class CheckerBoard extends AbsCheckerBoard
{
    /**
     * A 2D array of characters used to represent our checkerboard.
     */
    private char[][] board;

    /**
     * A HashMap, with a Character key and an Integer value, that is used to map a player's char to the number of
     * tokens that player still has left on the board.
     */
    private HashMap<Character, Integer> pieceCount;

    /**
     * A HashMap, with a Character key and an ArrayList of DirectionEnums value, used to map a player (and its king
     * representation) to the directions that player can viably move in. A non-kinged (standard) piece can only move
     * in the diagonal directions away from its starting position. A kinged piece can move in the same directions the
     * standard piece can move in plus the opposite directions the standard piece can move in.
     */
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;
    //Moved EMPTY_POS -> ICheckerBoard
    //Moved BLACK_TILE -> ICheckerBoard

    private int ROW_NUM;
    private int COL_NUM;

    public char playerOne;
    public char playerTwo;

    /**
     * parameterized constructor for the CheckerBoard object
     *
     * @param aDimensions the size of the square board
     * @param aPlayerOne the character for player one
     * @param aPlayerTwo the character for player two
     *
     * @pre none
     *
     * @post board = new char[ROW_NUM][COL_NUM] AND pieceCount = new HashMap<Character, Integer>()
     * AND viableDirections = new HashMap<Character, ArrayList<DirectionEnum>() AND [viableDirections maps
     * the players to their respective starting directions] AND [pieceCount maps the starting count of tokens to each
     * player] AND [all indices within the checkerboard are initialized to either a player char, an asterisk, or a space]
     * AND
     */
    public CheckerBoard(int aDimensions, char aPlayerOne, char aPlayerTwo) {

        this.COL_NUM = aDimensions;
        this.ROW_NUM = aDimensions;
        this.playerOne = aPlayerOne;
        this.playerTwo = aPlayerTwo;

        // formula that can calculate the number of starting pieces on each side
        int STARTING_COUNT = (((ROW_NUM / 2) - 1) * COL_NUM) / 2;

        board = new char[ROW_NUM][COL_NUM];
        pieceCount = new HashMap<>();
        viableDirections = new HashMap<>();

        pieceCount.put(playerOne, STARTING_COUNT);
        pieceCount.put(playerTwo, STARTING_COUNT);

        ArrayList<DirectionEnum> playerOneDirections = new ArrayList<>();
        playerOneDirections.add(DirectionEnum.SE);
        playerOneDirections.add(DirectionEnum.SW);
        viableDirections.put(playerOne, playerOneDirections);

        ArrayList<DirectionEnum> playerTwoDirections = new ArrayList<>();
        playerTwoDirections.add(DirectionEnum.NE);
        playerTwoDirections.add(DirectionEnum.NW);
        viableDirections.put(playerTwo, playerTwoDirections);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                if ((r + c) % 2 == 0) {
                    if (r < (ROW_NUM / 2) - 1) {
                        board[r][c] = playerOne;
                    }
                    else if (r > (ROW_NUM / 2)) {
                        board[r][c] = playerTwo;
                    }
                    else {
                        board[r][c] = EMPTY_POS;
                    }
                }
                else {
                    board[r][c] = BLACK_TILE;
                }
            }
        }

    }

    /**
     * Standard getter for the dimensions of the CheckerBoard
     *
     * @return the dimensions of the CheckerBoard
     *
     * @pre none
     *
     * @post dimensions = dimensions AND dimensions = #dimensions
     */
    @Override
    public int getDimensions() {
        return ROW_NUM;
    }

    /**
     * Simple accessor for viableDirections HashMap
     *
     * @return the HashMap for viableDirections
     *
     * @pre none
     *
     * @post viableDirections = viableDirections AND viableDirections = #viableDirections
     * AND pieceCount = #pieceCount
     */
    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Simple accessor for getPieceCounts HashMap
     *
     * @return the HashMap for getPieceCounts
     *
     * @pre none
     *
     * @post getPieceCount = pieceCount AND pieceCount = #pieceCount AND viableDirections = #viableDirections
     */
    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    /**
     * places a piece at the position chosen on the board
     *
     * @param pos position to place the piece
     * @param player character representing players piece
     *
     * @pre pos != null AND 0 = pos.getRow() < ROW_NUM AND 0 <= pos.getCol() < COL_NUM
     *
     * @post  board[pos.getRow(), pos.getCol()] = player AND pieceCount = #pieceCount
     * AND viableDirections = #viableDirections
     */
    @Override
    public void placePiece(BoardPosition pos, char player) {
        board[pos.getRow()][pos.getColumn()] = player;
    }

    /**
     * Returns the character at the position
     *
     * @param pos position to check
     *
     * @return character at the position
     *
     * @pre pos != null AND 0 <= pos.getRow() < ROW_NUM AND 0 <= pos.getCol() < COL_NUM
     *
     * @post whatsAtPos = board[pos.getRow(), pos.getCol()] AND pieceCount = #pieceCount
     * AND viableDirections = #viableDirections
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }
}