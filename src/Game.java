import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
    
    private PrintStream printStream;
    private InputStream inStream;
    private Scanner userInput;

    private boolean gameOver;
    private boolean userToPlay;

    private Board gameBoard;

    public Game(PrintStream printStream, InputStream inStream) {
        this.printStream = printStream;
        this.inStream = inStream;

        gameBoard = new Board(printStream);

        gameOver = false;
        userToPlay = true;
        handleTurn();
    }

    private void handleTurn() {
        userInput = new Scanner(inStream);

        while (!gameOver) {
            if (userToPlay == true) {
                userTakesTurn();
                userToPlay = false;

                gameOver = gameBoard.findFours(Player.USER) > 0;
            } else {
                computerTakesTurn();
                userToPlay = true;

                gameOver = gameBoard.findFours(Player.COMPUTER) > 0;
            }
            gameBoard.print();
            // TODO: check for draw. Ensure no turn is taken when board is full.
        }
        // TODO: if gameOver, print gameover screen with final board 
        if (gameOver) printStream.println("Thanks for playing!");

        userInput.close();
    }

    // Waits for a valid move from user input (an integer from 1 to 7, inclusive). 
    // Places piece into row specified by user. 
    private void userTakesTurn() {    
        boolean validInput = false;

        while (!validInput) {
            String cmd = userInput.nextLine();

            try {
                int col = Integer.parseInt(cmd) - 1;
                if (col >= 0 && col <= 6) {
                    int row = gameBoard.dropPiece(col, Player.USER);

                    if (row == -1) {
                        printStream.println("Invalid move. Please try again.");
                    } else {
                        validInput = true;
                        printStream.println("User places piece in column " 
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

    // Generates computer's move and returns as Position.
    private void computerTakesTurn() {
        //temporary random move generator
        int randCol = (int) (Math.random() * 6);

        int row = gameBoard.dropPiece(randCol, Player.COMPUTER);

        if(row == -1) {
            computerTakesTurn();
        } else {
            printStream.println("Computer places piece in column " + (randCol + 1) + ".");
        }
    }
}

    