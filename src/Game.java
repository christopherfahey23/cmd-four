import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];
    
    private PrintStream outStream;
    private InputStream inStream;

    private boolean gameOver;
    private boolean userToPlay;

    public Game(PrintStream outStream, InputStream inStream) {
        this.outStream = outStream;
        this.inStream = inStream;
    }

    public void start() {
        gameOver = false;
        userToPlay = true;

        handleTurn();
    }

    private void handleTurn() {
        Scanner userInput = new Scanner(inStream);

        while (!gameOver) {
            if (userToPlay == true) {
                userTakesTurn(userInput);
                userToPlay = false;
                // here for now
                if (!gameOver) print();
            } else {
                computerTakesTurn();
                userToPlay = true;

                if (!gameOver) print();
            }
            // check for win
            // if (!gameOver)
        }
        // TODO: if gameOver, print gameover screen with final board 
        if (gameOver) outStream.println("Thanks for playing!");

        userInput.close();
    }

    private void userTakesTurn(Scanner userInput) {    
        boolean validInput = false;

        while (!validInput) {
            String cmd = userInput.nextLine();

            if (cmd.equals("quit")) {
                validInput = true; 
                gameOver = true;
            } else {
                try {
                    int col = Integer.parseInt(cmd);
                    if (col >= 1 && col <= 7) {
                        if (dropPiece(col - 1, Player.USER)) {
                            validInput = true;
                            outStream.println(
                                "User places piece in column " 
                                + col + ".");
                        } else {
                            outStream.println(
                                "Invalid move. Please try again.");
                        }
                    } else {
                        throw new RuntimeException();
                    }
                } catch(Exception e) {
                    outStream.println("Invalid input. Please try again.");
                }
            }
        }
    }


    private void computerTakesTurn() {
        //temporary random move generator
        int randCol = (int) ((Math.random() * 6) + 1);
        System.out.println("Computer dropping piece in col " + randCol);
        if(!dropPiece(randCol - 1, Player.COMPUTER)) computerTakesTurn();
    }

    private boolean dropPiece(int col, Player player) {
        boolean pieceFound = false;
        int i = 0;

        while (!pieceFound && i < 6) {
            if (board[i][col] != null) pieceFound = true;
            else i++;
        }

        if (i == 0) return false;
        else {
            board[i - 1][col] = new Piece(player);
            return true;
        }
    }

    public void print() {
        // top of board
        for (int c = 0; c < COLS; c++) {
            if (c == 0) outStream.print("╔");
            else outStream.print("╦");

            outStream.print("───");
        }
        outStream.println("╗"); 

        // boxes
        for (int r = 0; r < ROWS; r++) {
            // middle of boxes
            for (int c = 0; c < COLS; c++) {
                String box = board[r][c] == null ? " " : board[r][c].print(); 
                
                outStream.print("║ ");
                outStream.print(box);
                outStream.print(" ");
            }
            outStream.println("║");
            
            // bottom of boxes/board
            for (int c = 0; c < COLS; c++) {
                if (c == 0) {
                    if (r == ROWS - 1) outStream.print("╚");
                    else outStream.print("╟");
                } else {
                    if (r == ROWS - 1) outStream.print("╩");
                    else outStream.print("╫");
                }
                if (r == ROWS - 1) outStream.print("═══");
                else outStream.print("───");
                
                if (c == COLS - 1) {
                    if (r == ROWS - 1) outStream.print("╝");
                    else outStream.print("╢");
                }
            }
            outStream.println();
        }
        
        // column labels
        for (int c = 0; c < COLS; c++) {
            outStream.print("  " + Integer.toString(c + 1) + " ");
        }
        outStream.println();
    }
}
