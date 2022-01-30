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
        handleTurn();
    }

    // Handles turn taking
    private void handleTurn() {
        userInput = new Scanner(inStream);

        while (!gameOver) {
            if (userToPlay == true) {
                userTakesTurn();
                userToPlay = false;
                gameBoard.print();

                if (gameBoard.findFours(Player.USER) > 0) {
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

    // Generates computer's move.
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

    // Handles end of game
    private void endGame(Player player) {
        gameOver = true;

        if (player != null) {
            printStream.print("Winner: "); 
            if (player == Player.USER) printStream.println("Player");
            else printStream.println("Computer");
        } else {
            printStream.print("It's a draw!");
        }
        
        printStream.println();
        printStream.println("Thanks for playing!");
    }
}

    