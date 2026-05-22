package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CheckerBoardMem extends AbsCheckerBoard
{
    /**
     * @invariant (8 <= ROW_NUM <= 16) AND (8 <= COL_NUM <= 16) AND (playerOne != playerTwo != (' ' OR '*' ))
     */


    /**
     * A map with a Character key and an ArrayList containing BoardPosition, that is used to track players
     * pieces on the board
     */
    private Map<Character, ArrayList<BoardPosition>> memBoard;

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
    public static final char EMPTY_POS = ' ';
    public static final char BLACK_TILE = '*';

    private int ROW_NUM;
    private int COL_NUM;

    public char playerOne;
    public char playerTwo;


    /**
     * @param aDimensions the size of the square board
     * @param aPlayerOne the character of player one
     * @param aPlayerTwo the character of player two
     *
     * @pre (8 <= ROW_NUM <= 16) AND (8 <= COL_NUM <= 16) AND (playerOne != playerTwo != (' ' OR '*' ))
     *
     * @post this.COL_NUM = aDimensions AND this.ROW_NUM = aDimensions AND this.PlayerOne = aPlayerOne AND this.playerTwo = aPlayerTwo
     * AND memBoard contains initial positions for both players for each piece AND
     * pieceCount contains their starting piece count AND viableDirections map players to their appropriate
     * moveable directions AND all board pos are properly initialized
     */
    public CheckerBoardMem(int aDimensions, char aPlayerOne, char aPlayerTwo) {

        this.COL_NUM = aDimensions;
        this.ROW_NUM = aDimensions;
        this.playerOne = aPlayerOne;
        this.playerTwo = aPlayerTwo;

        // formula that can calculate the number of starting pieces on each side
        int STARTING_COUNT = (((ROW_NUM / 2) - 1) * COL_NUM) / 2;

        memBoard = new HashMap<>();
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

        memBoard.put(playerOne, new ArrayList<>());
        memBoard.put(playerTwo, new ArrayList<>());

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                if ((r + c) % 2 == 0) {
                    BoardPosition pos = new BoardPosition(r, c);

                    if (r < (ROW_NUM / 2) - 1) {
                        memBoard.get(playerOne).add(pos);
                    } else if (r > ROW_NUM / 2) {
                        memBoard.get(playerTwo).add(pos);
                    }
                }
            }
        }
    }

    /**
     * Standard getter for the dimensions of the CheckerBoardMEM
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
     * @post viableDirections = viableDirections AND viableDirections = #viableDirections AND pieceCount = #pieceCount
     */
    @Override
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }

    /**
     * Simple accessor for pieceCount HashMap
     *
     * @return the HashMap for pieceCount
     *
     * @pre none
     *
     * @post pieceCount = #pieceCount
     */
    @Override
    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    /**
     * function that places a piece if it is not in the map, and removes it if EMPTY_POS is passed in
     *
     * @param pos board position you want to place/remove at
     * @param player the character you want to add/remove
     *
     * @pre pos is a valid position on the board
     *
     * @post memBoard.get(player) either adds or removes pos
     */
    @Override
    public void placePiece(BoardPosition pos, char player) {
        //removes from board if a space is passed on
        if (player == EMPTY_POS) {
            memBoard.get(playerOne).remove(pos);
            memBoard.get(playerTwo).remove(pos);
        }
        //effective extension for crowning a piece
        else if (player == Character.toUpperCase(playerOne)) {
            memBoard.get(playerOne).remove(pos);
            if (!memBoard.containsKey(player)) {
                memBoard.put(player, new ArrayList<>());
            }
            memBoard.get(player).add(pos);
        }
        else if (player == Character.toUpperCase(playerTwo)) {
            memBoard.get(playerTwo).remove(pos);
            if (!memBoard.containsKey(player)) {
                memBoard.put(player, new ArrayList<>());
            }
            memBoard.get(player).add(pos);
        }
        //routine adding of a piece
        else {
            if (!memBoard.containsKey(player)) {
                memBoard.put(player, new ArrayList<>());
            }

            if (!memBoard.get(player).contains(pos)) {
                memBoard.get(player).add(pos);
            }
        }
    }

    /**
     * returns what is at that position on the board
     *
     * @pre pos is a valid position on the board
     *
     * @param pos the position on the board you want to check
     *
     * @return either one of the two players, a space, or an asterisk
     *
     * @post memBoard = #memBoard
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {
        for (Map.Entry<Character, ArrayList<BoardPosition>> entry : memBoard.entrySet()) {
            if (entry.getValue().contains(pos)) {
                return entry.getKey();
            }
        }

        if ((pos.getColumn() % 2 == 0 && pos.getRow() % 2 == 1) || (pos.getColumn() % 2 == 1 && pos.getRow() % 2 == 0)) {
            return BLACK_TILE;
        }

        return EMPTY_POS;
    }
}