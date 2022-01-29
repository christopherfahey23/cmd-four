import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];
    
    private PrintStream outStream;
    private InputStream inStream;
    private Scanner userInput;

    private boolean gameOver;
    private boolean userToPlay;

    public Game(PrintStream outStream, InputStream inStream) {
        this.outStream = outStream;
        this.inStream = inStream;
    }

    // useless?
    public void start() {
        gameOver = false;
        userToPlay = true;

        handleTurn();
    }

    private void handleTurn() {
        userInput = new Scanner(inStream);

        while (!gameOver) {
            if (userToPlay == true) {
                Position pos = userTakesTurn();
                userToPlay = false;

                gameOver = checkWin(Player.USER, pos.getRow(), pos.getCol());
            } else {
                Position pos = computerTakesTurn();
                userToPlay = true;

                gameOver = checkWin(Player.COMPUTER, pos.getRow(), pos.getCol());
            }
            print();
            // TODO: check for draw. Ensure no turn is taken when board is full.
        }
        // TODO: if gameOver, print gameover screen with final board 
        if (gameOver) outStream.println("Thanks for playing!");

        userInput.close();
    }

    // Waits for a valid move from user input (an integer from 1 to 7, inclusive). 
    // Returns row piece is placed into and (-1, -1) otherwise.
    private Position userTakesTurn() {    
        boolean validInput = false;
        int row = -1;
        int col = -1;

        while (!validInput) {
            String cmd = userInput.nextLine();

            try {
                col = Integer.parseInt(cmd) - 1;
                if (col >= 0 && col <= 6) {
                    row = dropPiece(col, Player.USER);

                    if (row == -1) {
                        outStream.println("Invalid move. Please try again.");
                    } else {
                        validInput = true;
                        outStream.println("User places piece in column " 
                            + (col + 1) + ".");
                    }
                } else {
                    throw new RuntimeException();
                }
            } catch(Exception e) {
                outStream.println("Invalid input. Please try again.");
            }
        }

        return new Position(row, col);
    }

    // Generates computer's move and returns as Position.
    private Position computerTakesTurn() {
        //temporary random move generator
        int randCol = (int) (Math.random() * 6);

        int row = dropPiece(randCol, Player.COMPUTER);

        if(row == -1) return computerTakesTurn();
        else {
            outStream.println("Computer places piece in column " + (randCol + 1) + ".");
            return new Position(row, randCol);
        }
    }

    // TODO: create board class and move the following functions there. 
    // Use board objects to test different secnarios with AI.

    // Attempts to drop piece belonging to player in column col. 
    // Returns -1 if move is invalid and the row the piece lands in otherwise.
    private int dropPiece(int col, Player player) {
        boolean pieceFound = false;
        int i = 0;

        while (!pieceFound && i < 6) {
            if (board[i][col] != null) pieceFound = true;
            else i++;
        }

        if (i == 0) return -1;
        else {
            board[i - 1][col] = new Piece(player);
            return i - 1;
        }
    }

    // Checks if player has won after placing piece in row and col
    // TODO: pass board as parameter so that AI can check possible 
    // board states for win
    private boolean checkWin(Player player, int row, int col) {
        return (findRowGroups(player, 4) > 0 
            || findColGroups(player, 4) > 0
            || findDiagGroups(player, 4) > 0);
    }

    // Finds rows of 4 of player's tokens as well as groups of 
    // 1, 2, or 3 that can be connected into a row of 4. 
    private int findRowGroups(Player player, int size) {
        int groups = 0;
        
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 3; c++) {
                int playerCount = 0;
                int oppCount = 0;
                
                for (int i = 0; i < 4; i++) {
                    Piece curr = board[r][c + i];

                    if (curr != null) {
                        if (curr.getPlayer() == player) playerCount++;
                        else oppCount++;
                    }
                }

                if (playerCount == size && oppCount == 0) groups++; 
            }
        }

        return groups;
    }

    // Finds columns of 4 of player's tokens as well as groups of 
    // 1, 2, or 3 that can be connected into a column of 4. 
    private int findColGroups(Player player, int size) {
        int groups = 0;
        
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS - 3; r++) {
                int playerCount = 0;
                int oppCount = 0;
                
                for (int i = 0; i < 4; i++) {
                    Piece curr = board[r + i][c];

                    if (curr != null) {
                        if (curr.getPlayer() == player) playerCount++;
                        else oppCount++;
                    }
                }

                if (playerCount == size && oppCount == 0) groups++; 
            }
        }

        return groups;
    }

    private int findDiagGroups(Player player, int size) {
        int groups = 0;
        
        // upper left to lower right diagonal
        for (int r = 0; r < ROWS - 3; r++) {
            for (int c = 0; c < COLS - 3; c++) {
                int playerCount = 0;
                int oppCount = 0;

                for (int i = 0; i < 4; i++) {
                    Piece curr = board[r + i][c + i];

                    if (curr != null) {
                        if (curr.getPlayer() == player) playerCount++;
                        else oppCount++;
                    }
                }

                if (playerCount == size && oppCount == 0) groups++;
            }
        }
        
        // lower left to upper right diagonal
        for (int r = 0; r < ROWS - 3; r++) {
            for (int c = 3; c < COLS; c++) {
                int playerCount = 0;
                int oppCount = 0;

                for (int i = 0; i < 4; i++) {
                    Piece curr = board[r + i][c - i];
                    
                    if (curr != null) {
                        if (curr.getPlayer() == player) playerCount++;
                        else oppCount++;
                    }
                }

                if (playerCount == size && oppCount == 0) groups++;
            }
        }
        return groups;
    }

    // prints the board to outStream
    private void print() {
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
