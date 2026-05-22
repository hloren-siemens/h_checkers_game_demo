package cpsc2150.extendedCheckers.tests;
import static org.junit.Assert.*;

import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCheckerBoard {
    private String arrtoString(char [][] board) {
        StringBuilder s = new StringBuilder("|  |");
        int numRows = board.length;
        int numCols = board[0].length;

        //create column headers
        for (int i = 0; i < numCols; i++) {
            if (i < 10) {
                s.append(" ").append(i).append("|");
            }
            else {
                s.append(i).append("|");
            }
        }
        s.append("\n");

        for (int i = 0; i < numRows; i++) {
            if (i < 10) {
                s.append("|").append(i).append(" ");
            }
            else {
                s.append("|").append(i);
            }

            for (int j = 0; j < numCols; j++) {
                s.append("|");
                char pos = board[i][j];
                if (pos != '*') {
                    s.append(pos).append(" ");
                }
                else {
                    s.append("* ");
                }
            }
            s.append("|\n");
        }
        return s.toString();
    }

    private ICheckerBoard makeBoard() {
        int aDimensions = 8;
        char aPlayerOne = 'x';
        char aPlayerTwo = 'o';
        return new CheckerBoard(aDimensions, aPlayerOne, aPlayerTwo);
    }

    //constructor, toString
    @Test
    public void testConstructor() {
        ICheckerBoard cb = makeBoard();
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    //constructor, whatsAtPos
    @Test
    public void testWhatsAtPos_MinRow_MinCol_xPiece() {
        ICheckerBoard cb = makeBoard();
        char obs = cb.whatsAtPos(new BoardPosition(0, 0));

        char exp = 'x';

        assertEquals(exp, obs);
    }

    //constructor, whatsAtPos
    @Test
    public void testWhatsAtPos_MinRow_MaxCol_blackTile() {
        ICheckerBoard cb = makeBoard();
        char obs = cb.whatsAtPos(new BoardPosition(0, 7));

        char exp = '*';

        assertEquals(exp, obs);
    }

    //constructor, whatsAtPos
    @Test
    public void testWhatsAtPos_MaxRow_MinCol_blackTile() {
        ICheckerBoard cb = makeBoard();
        char obs = cb.whatsAtPos(new BoardPosition(7, 0));

        char exp = '*';

        assertEquals(exp, obs);
    }

    //constructor, whatsAtPos
    @Test
    public void testWhatsAtPos_MaxRow_MaxCol_oPiece() {
        ICheckerBoard cb = makeBoard();
        char obs = cb.whatsAtPos(new BoardPosition(7, 7));

        char exp = 'o';

        assertEquals(exp, obs);
    }

    //constructor, whatsAtPos
    @Test
    public void testWhatsAtPos_Row5_Col4_blackTile() {
        ICheckerBoard cb = makeBoard();
        char obs = cb.whatsAtPos(new BoardPosition(5, 4));

        char exp = '*';

        assertEquals(exp, obs);
    }

    //constructor, getPieceCounts
    @Test
    public void testGetPieceCounts_startingBoard() {
        ICheckerBoard cb = makeBoard();
        HashMap<Character, Integer> obs = cb.getPieceCounts();

        HashMap<Character, Integer> exp = new HashMap<>();
        exp.put('x', 12);
        exp.put('o', 12);

        assertEquals(exp, obs);
    }

    //constructor, getViableDirections
    @Test
    public void testGetViableDirections_startingBoard() {
        ICheckerBoard cb = makeBoard();
        HashMap<Character, ArrayList<DirectionEnum>> obs = cb.getViableDirections();

        HashMap<Character, ArrayList<DirectionEnum>> exp = new HashMap<>();

        ArrayList<DirectionEnum> xExpDir = new ArrayList<>();
        xExpDir.add(DirectionEnum.SE);
        xExpDir.add(DirectionEnum.SW);

        ArrayList<DirectionEnum> oExpDir = new ArrayList<>();
        oExpDir.add(DirectionEnum.NE);
        oExpDir.add(DirectionEnum.NW);

        exp.put('x', xExpDir);
        exp.put('o', oExpDir);

        assertEquals(exp, obs);
    }

    //constructor, toString, placePiece
    @Test
    public void testPlacePiece_RoutinePlace_Player_o_Row4_Col0(){

        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(4,0), 'o');
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {'o', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }

    @Test
    public void testPlacePiece_RoutinePlace_Player_x_Row3_Col3(){

        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(3,3), 'x');
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', 'x', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }

    @Test
    public void testPlacePiece_EdgeCase_Player_x_Row0_Col0(){

        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(0,0), 'x');
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }



    @Test
    public void testPlacePiece_EdgeCase_Player_o_Row7_Col7(){

        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(7,7), 'o');
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }
    @Test
    public void testPlacePiece_Place_x_On_o_Row6_Col0(){

        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(6,0), 'x');
        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'x', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }

    @Test
    public void testGetDirection_SW(){

        BoardPosition obs = ICheckerBoard.getDirection(DirectionEnum.SW);
        BoardPosition exp = new BoardPosition(1,-1);

        assertEquals(exp, obs);


    }

    @Test
    public void testCheckPlayerWin_Player_o_One_x_One_o() {
        ICheckerBoard cb = makeBoard();

        cb.getPieceCounts().put('x', 1);
        cb.getPieceCounts().put('o', 1);


        assertFalse(cb.checkPlayerWin('o'));
    }

    @Test
    public void testCheckPlayerWin_Player_x_One_x_Zero_o() {
        ICheckerBoard cb = makeBoard();

        cb.getPieceCounts().put('x', 1);
        cb.getPieceCounts().put('o', 0);

        assertTrue(cb.checkPlayerWin('x'));
    }

    @Test
    public void testCrownPiece_Row7_Column1(){
        ICheckerBoard cb = makeBoard();
        cb.crownPiece(new BoardPosition(7, 1));

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'O', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testCrownPiece_Row0_Column2(){
        ICheckerBoard cb = makeBoard();
        cb.crownPiece(new BoardPosition(0, 2));

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'X', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testCrownPiece_Row5_Column3(){
        ICheckerBoard cb = makeBoard();
        cb.crownPiece(new BoardPosition(5, 3));

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'O', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testMovePiece_SEMove_x_Row2_Col0(){
        ICheckerBoard cb = makeBoard();
        BoardPosition startingPos = new BoardPosition(2,0);
        cb.movePiece(startingPos, DirectionEnum.SE);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);

    }

    @Test
    public void testMovePiece_NEMove_o_Row5_Column1(){
        ICheckerBoard cb = makeBoard();
        BoardPosition startingPos = new BoardPosition(5, 1);
        cb.movePiece(startingPos, DirectionEnum.NE);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', 'o', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testMovePiece_SWMove_x_Row2_Column4(){
        ICheckerBoard cb = makeBoard();
        BoardPosition startingPos = new BoardPosition(2, 4);
        cb.movePiece(startingPos, DirectionEnum.SW);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', ' ', '*', 'x', '*'},
                {'*', ' ', '*', 'x', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testJumpPiece_NEMove_o_Row4_Column2(){
        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(4,2), 'o');
        cb.placePiece(new BoardPosition(5,1), ' ');
        cb.placePiece(new BoardPosition(3,3), 'x');
        cb.placePiece(new BoardPosition(2,4), ' ');
        BoardPosition startingPos = new BoardPosition(4, 2);
        cb.jumpPiece(startingPos, DirectionEnum.NE);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', 'o', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', ' ', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testJumpPiece_SWMove_x_Row3_Column3(){
        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(4,2), 'o');
        cb.placePiece(new BoardPosition(5,1), ' ');
        cb.placePiece(new BoardPosition(3,3), 'x');
        cb.placePiece(new BoardPosition(2,4), ' ');
        BoardPosition startingPos = new BoardPosition(3, 3);
        cb.jumpPiece(startingPos, DirectionEnum.SW);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {'x', '*', 'x', '*', ' ', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', ' ', '*', ' ', '*', ' ', '*'},
                {'*', 'x', '*', 'o', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testJumpPiece_SEMove_x_Row2_Column0(){
        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(3,1), 'o');
        cb.placePiece(new BoardPosition(5,3), ' ');
        BoardPosition startingPos = new BoardPosition(2, 0);
        cb.jumpPiece(startingPos, DirectionEnum.SE);

        String obs = cb.toString();

        char[][] expBoard = {
                {'x', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', 'x', '*', 'x', '*', 'x', '*', 'x'},
                {' ', '*', 'x', '*', 'x', '*', 'x', '*'},
                {'*', ' ', '*', ' ', '*', ' ', '*', ' '},
                {' ', '*', 'x', '*', ' ', '*', ' ', '*'},
                {'*', 'o', '*', ' ', '*', 'o', '*', 'o'},
                {'o', '*', 'o', '*', 'o', '*', 'o', '*'},
                {'*', 'o', '*', 'o', '*', 'o', '*', 'o'},
        };

        String exp = arrtoString(expBoard);

        assertEquals(exp, obs);
    }

    @Test
    public void testScanSurroundingPositions_oSWSE_blankNWNE(){
        ICheckerBoard cb = makeBoard();
        BoardPosition startingPos = new BoardPosition(5, 1);
        HashMap<DirectionEnum, Character> obs = cb.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> exp = new HashMap<>();

        exp.put(DirectionEnum.NE, ' ');
        exp.put(DirectionEnum.NW, ' ');
        exp.put(DirectionEnum.SE, 'o');
        exp.put(DirectionEnum.SW, 'o');

        assertEquals(exp, obs);
    }

    @Test
    public void testScanSurroundingPositions_oSE_xNE(){
        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(3,1), 'o');
        cb.placePiece(new BoardPosition(5,3), ' ');
        BoardPosition startingPos = new BoardPosition(2, 0);
        HashMap<DirectionEnum, Character> obs = cb.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> exp = new HashMap<>();

        exp.put(DirectionEnum.NE, 'x');
        exp.put(DirectionEnum.SE, 'o');
        exp.put(DirectionEnum.SW, '*');
        exp.put(DirectionEnum.NW, '*');

        assertEquals(exp, obs);
    }

    @Test
    public void testScanSurroundingPositions_oSW_xNWNE_blankSE(){
        ICheckerBoard cb = makeBoard();
        cb.placePiece(new BoardPosition(3,1), 'o');
        cb.placePiece(new BoardPosition(5,3), ' ');
        cb.placePiece(new BoardPosition(4,0), 'o');
        cb.placePiece(new BoardPosition(5,1), ' ');
        BoardPosition startingPos = new BoardPosition(3, 1);
        HashMap<DirectionEnum, Character> obs = cb.scanSurroundingPositions(startingPos);

        HashMap<DirectionEnum, Character> exp = new HashMap<>();

        exp.put(DirectionEnum.NE, 'x');
        exp.put(DirectionEnum.NW, 'x');
        exp.put(DirectionEnum.SE, ' ');
        exp.put(DirectionEnum.SW, 'o');

        assertEquals(exp, obs);
    }

}
