import java.io.PrintStream;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];

    private PrintStream printStream;
    
    public Board(PrintStream printStream) {
        this.printStream = printStream;
    }

    // Returns true if the board is full and false otherwise.
    public boolean isFull() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == null) return false;
            }
        }

        return true;
    }

    // Attempts to drop piece belonging to player in column col. 
    // Returns -1 if move is invalid and the row the piece lands in otherwise.
    public int dropPiece(int col, Player player) {
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

    // Finds groups of two of player's tokens that can be connected into 4
    public int findTwos(Player player) {
        return findRowGroups(player, 2) + findColGroups(player, 2)
            + findDiagGroups(player, 2);
    }

    // Finds groups of three of player's tokens that can be connected
    public int findThrees(Player player) {
        return findRowGroups(player, 3) + findColGroups(player, 3)
            + findDiagGroups(player, 3);
    }

    // Finds groups of four of player's tokens 
    public int findFours(Player player) {
        return findRowGroups(player, 4) + findColGroups(player, 4)
            + findDiagGroups(player, 4);
    }

    // Finds groups of size (2-4) of player's tokens 
    // that can be connected into rows. 
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

    // Finds groups of size (2-4) of player's tokens 
    // that can be connected into columns.
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

    // Finds groups of size (2-4) of player's tokens 
    // that can be connected into diagonals.
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

    // prints the board to printStream specified in constructor
    public void print() {
        // top of board
        for (int c = 0; c < COLS; c++) {
            if (c == 0) printStream.print("╔");
            else printStream.print("╦");

            printStream.print("───");
        }
        printStream.println("╗"); 

        // boxes
        for (int r = 0; r < ROWS; r++) {
            // middle of boxes
            for (int c = 0; c < COLS; c++) {
                String box = board[r][c] == null ? " " : board[r][c].print(); 
                
                printStream.print("║ ");
                printStream.print(box);
                printStream.print(" ");
            }
            printStream.println("║");
            
            // bottom of boxes/board
            for (int c = 0; c < COLS; c++) {
                if (c == 0) {
                    if (r == ROWS - 1) printStream.print("╚");
                    else printStream.print("╟");
                } else {
                    if (r == ROWS - 1) printStream.print("╩");
                    else printStream.print("╫");
                }
                if (r == ROWS - 1) printStream.print("═══");
                else printStream.print("───");
                
                if (c == COLS - 1) {
                    if (r == ROWS - 1) printStream.print("╝");
                    else printStream.print("╢");
                }
            }
            printStream.println();
        }
        
        // column labels
        for (int c = 0; c < COLS; c++) {
            printStream.print("  " + Integer.toString(c + 1) + " ");
        }
        printStream.println();
    }
}
