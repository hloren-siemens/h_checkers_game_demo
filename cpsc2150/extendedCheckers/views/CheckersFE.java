package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.CheckerBoardMem;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *  The driver for the checkers game.
 *
 * @initialization_ensures:
 *      self is initialized
 */

{

    /**
     * Simple upper-case conversion function for piece
     *
     * @param piece the char to be converted to upper-case
     *
     * @return piece in upper-case form
     *
     * @pre piece is of type char AND piece != NULL
     *
     * @post the piece variable with its value converted to its upper-case form
     */
    public static char toUpperCase(char piece){
        return Character.toUpperCase(piece);
    }

    /**
     * Simple lower-case conversion function for piece
     *
     * @param piece the char to be converted to lower-case
     *
     * @return piece in lower-case form
     *
     * @pre piece is of type char AND piece != NULL
     *
     * @post the piece variable with its value converted to its lower-case form
     */
    public static char toLowerCase(char piece){
        return Character.toLowerCase(piece);
    }

    public static void main(String[] args)
    {
        /*
        Main function. Remember, for project 1 we aren't implementing anything yet.

        You are welcome to add your own helper functions to this file. However, those functions must be given contracts.

        Also, the main typically doesn't need a contract. But you should still write the class contract.
         */

        //initialize checkerBoard and set up scanner to take in user inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Checkers!"); //Added Common Checkers Greeting

        // get player 1's piece
        System.out.println("Player 1, please enter your piece: ");
        String playerTwoneCheck = scanner.nextLine();
        while(playerTwoneCheck.length() > 1 || (!playerTwoneCheck.toLowerCase().equals(playerTwoneCheck))){
            System.out.println("Please enter only a single lowercase letter character: ");
            playerTwoneCheck = scanner.nextLine();
        }
        char aPlayerOne = playerTwoneCheck.charAt(0);


        // get player 2's piece
        System.out.println("Player 2, please enter your piece: ");
        String playerTwoCheck = scanner.nextLine();
        while(playerTwoCheck.length() > 1 || (!playerTwoCheck.toLowerCase().equals(playerTwoCheck))){
            System.out.println("Please enter only a single lowercase letter character: ");
            playerTwoCheck = scanner.nextLine();
        }
        char aPlayerTwo = playerTwoCheck.charAt(0);


        // player one starts the game
        char currentPlayer = aPlayerOne;
        char opponentPlayer = aPlayerTwo;

        System.out.println("Do you want a fast game (F/f) or a memory efficient game (M/m)?");
        char boardChoice = scanner.next().charAt(0);

        while (toLowerCase(boardChoice) != ('m') && toLowerCase(boardChoice) != ('f')){
            System.out.println("Invalid input. Please try again.");
            boardChoice = scanner.next().charAt(0);
        }


        //ask size of board, min 8x8, max 16 x 16, must be even
        System.out.println("How big should the board be? It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number: ");
        int aDimensions = scanner.nextInt();
        while ((aDimensions % 2 != 0) || (aDimensions < 8) || (aDimensions > 16)){
            System.out.println("Invalid input. Please try again.");
            System.out.println("Please enter an even number between 8 and 16 for the dimensions of the board you want to play");
            aDimensions = scanner.nextInt();

        }

        //initialize cb to either MemBoard or StandardBoard
        ICheckerBoard cb = null;

        if (boardChoice == 'm') {
            // use MemBoard
            cb = new CheckerBoardMem(aDimensions, aPlayerOne, aPlayerTwo);
        }
        if (boardChoice == 'f') {
            // use StandardBoard
            cb = new CheckerBoard(aDimensions, aPlayerOne, aPlayerTwo);
        }


        //sets the gameRunning flag
        boolean gameRunning = true;
        while (gameRunning) {

            //print current board
            System.out.print(cb);
            boolean validMoveFound = false;

            while(!validMoveFound) {

                //Prompt player to pick a piece to move
                System.out.print("Player " + currentPlayer + ", which piece do you wish to move? \n" +
                        "Enter the row followed by a space followed by the column.\n");

                //takes in user input for which piece to move
                int rowChoice = scanner.nextInt();
                int colChoice = scanner.nextInt();

                // out of range prevention
                while(rowChoice < 0 || rowChoice > aDimensions ||  colChoice < 0 || colChoice > aDimensions){
                    System.out.print("player " + currentPlayer + ", that isn't your piece. Pick one of your pieces.\n" +
                            "Player " + currentPlayer + ", which piece do you wish to move? \n" +
                            "Enter the row followed by a space followed by the column.\n");

                    rowChoice = scanner.nextInt();
                    colChoice = scanner.nextInt();
                }


                BoardPosition currentPos = new BoardPosition(rowChoice, colChoice);
                char piece = cb.whatsAtPos(currentPos);

                //flag for input validation
                boolean validPieceSelection = (toLowerCase(piece) == currentPlayer);

                while (!validPieceSelection) {
                    System.out.print("player " + currentPlayer + ", that isn't your piece. Pick one of your pieces.\n" +
                            "Player " + currentPlayer + ", which piece do you wish to move? \n" +
                            "Enter the row followed by a space followed by the column.\n");

                    rowChoice = scanner.nextInt();
                    colChoice = scanner.nextInt();

                    // restarts validPieceSelection loop if input is out of range
                    if(rowChoice < 0 || rowChoice > aDimensions ||  colChoice < 0 || colChoice > aDimensions){
                        continue;
                    }
                    
                    currentPos = new BoardPosition(rowChoice, colChoice);
                    piece = cb.whatsAtPos(currentPos);

                    if (toLowerCase(piece) == currentPlayer) {
                        validPieceSelection = true;
                    }
                }


                //manipulatable viableDirections for checking if the piece is crowned
                HashMap<Character, ArrayList<DirectionEnum>> viableDirections = cb.getViableDirections();

                //self-explanatory
                HashMap<DirectionEnum, Character> surroundingPosition = cb.scanSurroundingPositions(currentPos);

                //list of the viable moves
                ArrayList<DirectionEnum> validMoveList = new ArrayList<DirectionEnum>();

                //flags for if the direction listed is a valid jump target
                boolean jumpSW = false;
                boolean jumpSE = false;
                boolean jumpNW = false;
                boolean jumpNE = false;

                //validation for if the piece has any valid moves
                boolean hasNoValidDirections = true;


                //if the piece is crowned, add all viable directions to the code
                if (piece == toUpperCase(currentPlayer)) {
                    ArrayList<DirectionEnum> crownViableDirections = new ArrayList<DirectionEnum>();
                    crownViableDirections.add(DirectionEnum.SE);
                    crownViableDirections.add(DirectionEnum.SW);
                    crownViableDirections.add(DirectionEnum.NE);
                    crownViableDirections.add(DirectionEnum.NW);
                    viableDirections.put(currentPlayer, crownViableDirections);
                }
                else{
                    if(toLowerCase(currentPlayer) == aPlayerTwo){
                        ArrayList<DirectionEnum> playerTwoViableDirections = new ArrayList<>();

                        playerTwoViableDirections.add(DirectionEnum.NE);
                        playerTwoViableDirections.add(DirectionEnum.NW);
                        viableDirections.put(currentPlayer, playerTwoViableDirections);
                    }
                    else{
                        ArrayList<DirectionEnum> playerOneViableDirections = new ArrayList<>();

                        playerOneViableDirections.add(DirectionEnum.SE);
                        playerOneViableDirections.add(DirectionEnum.SW);
                        viableDirections.put(currentPlayer, playerOneViableDirections);
                    }
                }

                //checking if the SW direction is a valid move
                if (viableDirections.get(currentPlayer).contains(DirectionEnum.SW) && surroundingPosition.get(DirectionEnum.SW) != null) {
                    if (surroundingPosition.get(DirectionEnum.SW) == ' ') {
                        validMoveList.add(DirectionEnum.SW);
                    } else if (toLowerCase(surroundingPosition.get(DirectionEnum.SW)) == opponentPlayer) {
                        BoardPosition jumpSWPos = new BoardPosition(rowChoice + 1, colChoice - 1);
                        if (cb.scanSurroundingPositions(jumpSWPos).get(DirectionEnum.SW) == ' ') {
                            jumpSW = true;
                            validMoveList.add(DirectionEnum.SW);
                        }
                    }
                }

                //checking if the SE direction is a valid move
                if (viableDirections.get(currentPlayer).contains(DirectionEnum.SE) && surroundingPosition.get(DirectionEnum.SE) != null) {
                    if (surroundingPosition.get(DirectionEnum.SE) == ' ') {
                        validMoveList.add(DirectionEnum.SE);
                    } else if (toLowerCase(surroundingPosition.get(DirectionEnum.SE)) == opponentPlayer) {
                        BoardPosition jumpSEPos = new BoardPosition(rowChoice + 1, colChoice + 1);
                        if (cb.scanSurroundingPositions(jumpSEPos).get(DirectionEnum.SE) == ' ') {
                            jumpSE = true;
                            validMoveList.add(DirectionEnum.SE);
                        }
                    }
                }

                //checking if the NW direction is a valid move
                if (viableDirections.get(currentPlayer).contains(DirectionEnum.NW) && surroundingPosition.get(DirectionEnum.NW) != null) {
                    if (surroundingPosition.get(DirectionEnum.NW) == ' ') {
                        validMoveList.add(DirectionEnum.NW);
                    } else if (toLowerCase(surroundingPosition.get(DirectionEnum.NW)) == opponentPlayer) {
                        BoardPosition jumpSNPos = new BoardPosition(rowChoice - 1, colChoice - 1);
                        if (cb.scanSurroundingPositions(jumpSNPos).get(DirectionEnum.NW) == ' ') {
                            jumpNW = true;
                            validMoveList.add(DirectionEnum.NW);
                        }
                    }
                }

                //checking if the NE direction is a valid move
                if (viableDirections.get(currentPlayer).contains(DirectionEnum.NE) && surroundingPosition.get(DirectionEnum.NE) != null) {
                    if (surroundingPosition.get(DirectionEnum.NE) == ' ') {
                        validMoveList.add(DirectionEnum.NE);
                    } else if (toLowerCase(surroundingPosition.get(DirectionEnum.NE)) == opponentPlayer) {
                        BoardPosition jumpNEPos = new BoardPosition(rowChoice - 1, colChoice + 1);
                        if (cb.scanSurroundingPositions(jumpNEPos).get(DirectionEnum.NE) == ' ') {
                            jumpNE = true;
                            validMoveList.add(DirectionEnum.NE);
                        }
                    }
                }

                if (validMoveList.isEmpty()) {
                    System.out.print("You have selected a piece with no valid moves. \n" +
                            "Please select another piece. \n");
                    continue;

                }

                //move selection input validation
                boolean validMoveSelection = false;

                while (!validMoveSelection) {
                    //prompt the user to input which direction they wish to move/jump
                    System.out.print("In which direction do you wish to move the piece?\n " +
                            "Enter one of these options: \n");

                    //print out what moves are valid
                    if (validMoveList.contains(DirectionEnum.SW)) {

                        System.out.print("SW\n");
                    }
                    if (validMoveList.contains(DirectionEnum.SE)) {

                        System.out.print("SE\n");
                    }
                    if (validMoveList.contains(DirectionEnum.NW)) {

                        System.out.print("NW\n");
                    }
                    if (validMoveList.contains(DirectionEnum.NE)) {

                        System.out.print("NE\n");
                    }

                    //input for the move choice
                    String moveChoice = scanner.next();

                    BoardPosition newPos = null;

                    //moves the pieces or does input validation
                    if (moveChoice.equals("SW") || moveChoice.equals("sw") && validMoveList.contains(DirectionEnum.SW)) {
                        if (jumpSW) {
                            newPos = cb.jumpPiece(currentPos, DirectionEnum.SW);
                        } else {
                            newPos = cb.movePiece(currentPos, DirectionEnum.SW);
                        }
                        validMoveSelection = true;

                    } else if (moveChoice.equals("SE") || moveChoice.equals("se") && validMoveList.contains(DirectionEnum.SE)) {
                        if (jumpSE) {
                            newPos = cb.jumpPiece(currentPos, DirectionEnum.SE);
                        } else {
                            newPos = cb.movePiece(currentPos, DirectionEnum.SE);
                        }
                        validMoveSelection = true;
                    } else if (moveChoice.equals("NW") || moveChoice.equals("nw") && validMoveList.contains(DirectionEnum.NW)) {
                        if (jumpNW) {
                            newPos = cb.jumpPiece(currentPos, DirectionEnum.NW);
                        } else {
                            newPos = cb.movePiece(currentPos, DirectionEnum.NW);
                        }
                        validMoveSelection = true;
                    } else if (moveChoice.equals("NE") || moveChoice.equals("ne") && validMoveList.contains(DirectionEnum.NE)) {
                        if (jumpNE) {
                            newPos = cb.jumpPiece(currentPos, DirectionEnum.NE);
                        } else {
                            newPos = cb.movePiece(currentPos, DirectionEnum.NE);
                        }
                        validMoveSelection = true;
                    } else {
                        System.out.print("You have not selected a valid direction. Please input a valid direction.\n");
                    }

                    //crown piece
                    if (validMoveSelection && newPos != null) {
                        char newPiece = cb.whatsAtPos(newPos);
                        int row = newPos.getRow();

                        if ((newPiece == aPlayerOne && row == (aDimensions - 1)) || (newPiece == aPlayerTwo && row == 0)) {
                            cb.crownPiece(newPos);
                        }
                    }
                }
                validMoveFound = true;
            }
            //checks if currentPlayer has won, and prompts to reset if so
            if (cb.checkPlayerWin(currentPlayer)) {
                System.out.print("Player " + currentPlayer + " has won!\n");

                boolean validReset = true;
                while(validReset){
                    System.out.print("Would you like to play again? Enter 'Y' or 'N'\n");
                    String gameResetChoice = scanner.next();
                    if (gameResetChoice.equals("Y") || gameResetChoice.equals("y")) {
                        gameRunning = false;
                        validReset = false;
                    } else if (gameResetChoice.equals("N") || gameResetChoice.equals("n")){
                        System.exit(0);
                        validReset = false;
                    } else {
                        System.out.print("Not a valid character.\n");
                    }
                }
            }

            //swaps the current player to the other player
            if (currentPlayer == aPlayerOne) {
                currentPlayer = aPlayerTwo;
                opponentPlayer = aPlayerOne;
            }
            else {
                currentPlayer = aPlayerOne;
                opponentPlayer = aPlayerTwo;
            }
        }
        // restarts game
        main(args);
    }

}