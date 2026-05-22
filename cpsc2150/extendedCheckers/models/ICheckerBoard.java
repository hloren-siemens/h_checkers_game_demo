package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 *  This interface represents a checkerboard for a game of checkers.
 *  The board is a grid where players can move pieces diagonally.
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
 *      board: a representation of the game board
 *      pieceCount: a representation of the number of pieces each player has left on the board
 *      viableDirections: a representation of the directions each player can move in
 *      BOARD_DIMENSIONS: the dimensions of the board
 *
 * @constraints:
 *     board length is set equal to the value of BOARD_DIMENSIONS
 */
public interface ICheckerBoard {

   //moved static final chars to interface to create more consistency and less redundancy
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';
    /**
     * Standard getter for the dimensions of the CheckerBoard
     *
     * @return the dimensions of the CheckerBoard
     *
     * @pre none
     *
     * @post dimensions = dimensions AND dimensions = #dimensions
     */
    public int getDimensions();

    /**
     * Simple accessor for viableDirections HashMap
     *
     * @return the HashMap for viableDirections
     *
     * @pre none
     *
     * @post viableDirections = viableDirections AND viableDirections = #viableDirections AND pieceCount = #pieceCount
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();

    /**
     * Simple accessor for getPieceCounts HashMap
     *
     * @return the HashMap for getPieceCounts
     *
     * @pre none
     *
     * @post getPieceCount = pieceCount AND pieceCount = #pieceCount
     * AND viableDirections = #viableDirections
     */
    public HashMap<Character, Integer> getPieceCounts();

    /**
     * places a piece at the position chosen on the board
     *
     * @param pos position on the board to place piece.
     * @param player character representing players piece
     *
     * @pre pos != null AND 0 <= pos.getRow() < ROW_NUM AND 0 <= pos.getCol() < COL_NUM
     *
     * @post  player piece placed at the pos on the board AND pieceCount = #pieceCount
     * AND viableDirections = #viableDirections
     */
    public void placePiece(BoardPosition pos, char player);

    /**
     * Returns the character at the position
     *
     * @param pos position to check
     *
     * @return character at the position
     *
     * @pre pos != null AND 0 <= pos.getRow() < ROW_NUM AND 0 <= pos.getCol() < COL_NUM
     *
     * @post whatsAtPos = piece at pos AND pieceCount = #pieceCount AND viableDirections = #viableDirections
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Check if player has won
     *
     * @param player character that represents the player to check
     *
     * @return true if player won, false otherwise
     *
     * @pre player = PLAYER_ONE OR player = PLAYER_TWO
     *
     * @post checkPlayerWin = true IFF only the player's pieces remain on the board, OW false
     * AND pieceCount = #pieceCount And viableDirections = #viableDirections
     */
    default public boolean checkPlayerWin(Character player) {
        HashMap<Character, Integer> pieceCounts = getPieceCounts();
        Integer playerPieces = pieceCounts.get(player);


        char opponent = 'p';
        for (Character key : pieceCounts.keySet()) {
            if (!key.equals(player)) {
                opponent = key;
                break;
            }
        }
        Integer opponentPieces = pieceCounts.get(opponent);

        if (playerPieces == null) {
            playerPieces = 0;

        }
        if (opponentPieces == null) {
            opponentPieces = 0;
        }

        return playerPieces > 0 && opponentPieces == 0;
    }

    /**
     * converts a piece into a king piece (an uppercase version of the piece)
     *
     * @param posOfPlayer position of the piece to crown
     *
     * @pre posOfPlayer != null AND 0 <= posOfPlayer.getRow() < ROW_NUM AND 0 <= posOfPLayer.getCol() < COL_NUM
     *
     * @post posOfPlayer = crownedPiece AND pieceCount = #pieceCount AND viableDirections = #viableDirections
     */
    default public void crownPiece(BoardPosition posOfPlayer) {
        char currentPiece = whatsAtPos(posOfPlayer);

        // changed crownPiece to reduce redundant variable assignment, uppercase is passed directly to placePiece
        if (Character.isLowerCase(currentPiece) && currentPiece != ' ' && currentPiece != '*')
        {
            placePiece(posOfPlayer, Character.toUpperCase(currentPiece));
        }

    }

    /**
     * moves a piece on the board in the direction designated by the DirectionEnum dir. The position that the piece moved from
     * will now be empty
     *
     * @param startingPos current position of piece
     * @param dir direction of movement
     *
     * @return new position of the piece
     *
     * @pre startingPos != null AND dir != null AND 0 <= startingPos.getRow() < Row_NUM AND startingPos.getCol() < COL_NUM
     * AND board[startingPos.getRow(), startingPos.getCol()] is a player piece and move is agreeable to rules
     *
     * @post piece is moved to new position AND startingPOS = EMPTY_POS AND pieceCount = #pieceCount
     * AND viableDirections = #viableDIrections
     */
    default public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir) {
        int startRow = startingPos.getRow();
        int startCol = startingPos.getColumn();
        int BOARD_DIMENSIONS = getDimensions();

        //initialize the offset for movement
        int rowOffset = 0;
        int colOffset = 0;

        if (Objects.requireNonNull(dir) == DirectionEnum.NE) {
            rowOffset = -1; //move up
            colOffset = 1; //move right
        }
        else if (dir == DirectionEnum.NW) {
            rowOffset = -1; //move up
            colOffset = -1; //move left
        }
        else if (dir == DirectionEnum.SE) {
            rowOffset = 1; //move down
            colOffset = 1; //move right
        }
        else if (dir == DirectionEnum.SW) {
            rowOffset = 1; //move down
            colOffset = -1; //move left
        }

        int newRow = startRow + rowOffset;
        int newCol = startCol + colOffset;

        if (newRow >= 0 && newRow < BOARD_DIMENSIONS && newCol >= 0 && newCol < BOARD_DIMENSIONS) {
            char currentPiece = whatsAtPos(startingPos);

            if (currentPiece != ' ' && currentPiece != '*') {
                BoardPosition newPos = new BoardPosition(newRow, newCol);
                placePiece(newPos, currentPiece);
                placePiece(startingPos, ' ');
                return newPos;
            }
        }

        return null;
    }

    /**
     * Modified movePiece that both moves the player two positions, and removes the piece that was jumped
     *
     * @param startingPos of the piece that is jumping
     * @param dir direction that the piece is moving
     *
     * @return an updated boardPosition with the original piece moved, and the "jumped" piece removed
     *
     * @pre startingPos is a valid piece position AND dir is a valid direction for movement
     *
     * @post piece is moved to new location AND pieceCount = pieceCount - 1 AND "jumped" piece is removed
     */
    default public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir) {
        int startRow = startingPos.getRow();
        int startCol = startingPos.getColumn();

        int numRows = getDimensions();
        int numCols = getDimensions();

        //initialize the offsets for checking and jumping
        int rowOffset = 0;
        int colOffset = 0;
        int rowJumpOffset = 0;
        int colJumpOffset = 0;

        if (Objects.requireNonNull(dir) == DirectionEnum.NE) {
            rowOffset = -2; //move up
            colOffset = 2; //move right
            rowJumpOffset = -1; //check up
            colJumpOffset = 1; //check right

        }
        else if (Objects.requireNonNull(dir) == DirectionEnum.NW) {
            rowOffset = -2; //move up
            colOffset = -2; //move left
            rowJumpOffset = -1; //check up
            colJumpOffset = -1; //check left

        }
        else if (Objects.requireNonNull(dir) == DirectionEnum.SE) {
            rowOffset = 2; //move down
            colOffset = 2; //move right
            rowJumpOffset = 1; //check down
            colJumpOffset = 1; //check right

        }
        else if (Objects.requireNonNull(dir) == DirectionEnum.SW) {
            rowOffset = 2; //move down
            colOffset = -2; //move left
            rowJumpOffset = 1; //check down
            colJumpOffset = -1; //check left
        }

        int newRow = startRow + rowOffset;
        int newCol = startCol + colOffset;

        int jumpedRow = startRow + rowJumpOffset;
        int jumpedCol = startCol + colJumpOffset;


        if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols){
            char currentPiece = whatsAtPos(startingPos);
            BoardPosition jumpedPos = new BoardPosition(jumpedRow, jumpedCol);
            char jumpedPiece = whatsAtPos(jumpedPos);

            if (currentPiece != ' ' && currentPiece != '*' && jumpedPiece != ' ' && jumpedPiece != '*' && jumpedPiece != currentPiece) {
                BoardPosition newPos = new BoardPosition(newRow, newCol);
                placePiece(newPos, currentPiece);
                placePiece(startingPos, ' ');
                placePiece(jumpedPos, ' ');

                HashMap<Character, Integer> pieceCounts = getPieceCounts();

                if(jumpedPiece == Character.toUpperCase(jumpedPiece)){
                    jumpedPiece = Character.toLowerCase(jumpedPiece);
                }
                pieceCounts.put(jumpedPiece, pieceCounts.get(jumpedPiece) - 1);

                return newPos;
            }
        }

        return null;
    }

    /**
     * Checks the spaces around a position for any empty spaces or players
     *
     * @param startingPos position to check for any empty spaces or players
     *
     * @return a HashMap mapping each DirectionEnum to either ' ' (if the space is empty)
     * or a character representing the player occupying that space
     *
     * @pre startingPos != null AND 0 <= startingPos.getRow() < Row_NUM AND startingPos.getCol() < COL_NUM
     *
     * @post the Hashmap with each surrounding position contains all valid DirectionEnum keys mapping to either ' ' (empty space)
     * or the character representing the player at that position. If a direction is out of bounds, it will be omitted or mapped to a default value.
     */
    default public HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos) {
        HashMap<DirectionEnum, Character> result = new HashMap<>();

        int BOARD_DIMENSIONS = getDimensions();

        int row = startingPos.getRow();
        int col = startingPos.getColumn();

        //NE
        if (row - 1 >= 0 && col + 1 < BOARD_DIMENSIONS) {
            BoardPosition pos = new BoardPosition(row - 1, col + 1);
            result.put(DirectionEnum.NE, whatsAtPos(pos));
        } else {
            result.put(DirectionEnum.NE, '*');
        }
        //NW
        if (row - 1 >= 0 && col - 1 >= 0) {
            BoardPosition pos = new BoardPosition(row - 1, col - 1);
            result.put(DirectionEnum.NW, whatsAtPos(pos));
        } else {
            result.put(DirectionEnum.NW, '*');
        }
        //SE
        if (row + 1 < BOARD_DIMENSIONS && col + 1 < BOARD_DIMENSIONS) {
            BoardPosition pos = new BoardPosition(row + 1, col + 1);
            result.put(DirectionEnum.SE, whatsAtPos(pos));
        } else {
            result.put(DirectionEnum.SE, '*');
        }
        //SW
        if (row + 1 < BOARD_DIMENSIONS && col - 1 >= 0) {
            BoardPosition pos = new BoardPosition(row + 1, col - 1);
            result.put(DirectionEnum.SW, whatsAtPos(pos));
        } else {
            result.put(DirectionEnum.SW, '*');
        }

        return result;
    }

    /**
     *Updates the board with the direction the piece moved in
     *
     * @param dir for the piece to move in
     *
     * @return a BoardPosition that has been updated with the movement of the piece
     *
     * @pre dir is a valid direction for movement
     */
    public static BoardPosition getDirection(DirectionEnum dir) {
        if (dir == DirectionEnum.NE) {
            //move up and right
            return new BoardPosition(-1,1);

        }
        else if (dir == DirectionEnum.NW) {
            //move up and left
            return new BoardPosition(-1,-1);
        }
        else if (dir == DirectionEnum.SE) {
            //move down and right
            return new BoardPosition(1, 1);
        }
        else if (dir == DirectionEnum.SW) {
            //move down and left
            return new BoardPosition(1, -1);
        }
        else {
            return null;
        }
    }
}
