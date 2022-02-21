import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
    
    private PrintStream printStream;
    private InputStream inStream;
    private Scanner userInput;

    private boolean userToPlay;
    private boolean gameOver;

    private Board gameBoard;

    public Game(PrintStream printStream, InputStream inStream) {
        this.printStream = printStream;
        this.inStream = inStream;

        gameBoard = new Board(printStream);

        gameOver = false;
        userToPlay = true;

        printStream.println();
        printStream.println("Welcome to Four in a Row!");
        gameBoard.print();
        handleTurn();
    }

    // Handles turn taking
    private void handleTurn() {
        userInput = new Scanner(inStream);

        while (!gameOver) {
            if (userToPlay == true) {
                userTakesTurn();
                userToPlay = false;

                if (gameBoard.findFours(Player.USER) > 0) {
                    gameBoard.print();
                    endGame(Player.USER);
                }
            } else {
                computerTakesTurn();
                userToPlay = true;
                gameBoard.print();

                if (gameBoard.findFours(Player.COMPUTER) > 0) {
                    endGame(Player.COMPUTER);
                }
            }
            
            if (!gameOver && gameBoard.isFull()) endGame(null);
        }
        
        userInput.close();
    }

    // Waits for a valid move from user input (an integer from 1 to 7, inclusive). 
    // Places piece into row specified by user. 
    private void userTakesTurn() {    
        boolean validInput = false;

        while (!validInput) {
            printStream.println();
            printStream.print("Select a column: ");
            String cmd = userInput.nextLine();

            try {
                int col = Integer.parseInt(cmd) - 1;
                if (col >= 0 && col <= 6) {
                    int row = gameBoard.dropPiece(col, Player.USER);

                    if (row == -1) {
                        printStream.println("Invalid move. Please try again.");
                    } else {
                        validInput = true;
                        printStream.println();
                        printStream.println("User places a piece in column " 
                            + (col + 1) + ".");
                    }
                } else {
                    throw new RuntimeException();
                }
            } catch(Exception e) {
                printStream.println("Invalid input. Please try again.");
            }
        }
    }

    // Generates computer's move.
    private void computerTakesTurn() {
        int maxScore = Integer.MIN_VALUE;
        int maxMove = 0;

        for (int c = 0; c < Board.COLS; c++) {
            Board testBoard = new Board(gameBoard);
            int moveScore = 0;

            if (testBoard.dropPiece(c, Player.COMPUTER) == -1) {
                moveScore = Integer.MIN_VALUE;
            } else {
                int twosScore = testBoard.findTwos(Player.COMPUTER) 
                    - testBoard.findTwos(Player.USER);
                int compThreesScore = testBoard.findThrees(Player.COMPUTER);
                int userThreesScore = testBoard.findThrees(Player.USER);
                int foursScore = testBoard.findFours(Player.COMPUTER);

                moveScore = 1 * twosScore + 50 * compThreesScore 
                    - 100000 * userThreesScore + 100000000 * foursScore;
            }

            if (moveScore > maxScore) {
                maxScore = moveScore;
                maxMove = c;
            }
        }

        gameBoard.dropPiece(maxMove, Player.COMPUTER);

        printStream.println("Computer places a piece in column " + (maxMove + 1) + ".");
    }

    // Handles end of game.
    private void endGame(Player player) {
        gameOver = true;

        printStream.println();
        if (player != null) {
            printStream.print("WINNER: "); 
            if (player == Player.USER) printStream.println("Player");
            else printStream.println("Computer");
        } else {
            printStream.print("It's a draw!");
        }
        
        printStream.println();
        printStream.println("Thanks for playing!");
    }
}

    