import java.io.PrintStream;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];
    private PrintStream s;

    public Board(PrintStream s) {
        this.s = s;
    }

    public void takeTurn(int move) {
        boolean pieceFound = false;
        int i = 0;

        while (!pieceFound && i < 6) {
            if (board[i][move] != null) pieceFound = true;
            else i++;
        }

        if (i == 0) s.println("Invalid move (column occupied). Please try again.");
        else {
            s.println("User places piece in column " + (move + 1) + ".");
            board[i - 1][move] = new Piece(Player.USER);
            
            // TODO: computer response
            // Check if there's a win - if so, send info back to CmdFour
            
            print();
        }
    }

    public void print() {
        // top of board
        for (int c = 0; c < COLS; c++) {
            if (c == 0) s.print("┏");
            else s.print("┳");

            s.print("───");
        }
        s.println("┓"); 

        // boxes
        for (int r = 0; r < ROWS; r++) {
            // middle of boxes
            for (int c = 0; c < COLS; c++) {
                String box = board[r][c] == null ? " " : board[r][c].print(); 
                
                s.print("┃ ");
                s.print(box);
                s.print(" ");
            }
            s.println("┃");
            
            // bottom of boxes/board
            for (int c = 0; c < COLS; c++) {
                if (c == 0) {
                    if (r == ROWS - 1) s.print("┗");
                    else s.print("┠");
                } else {
                    if (r == ROWS - 1) s.print("┻");
                    else s.print("╂");
                }
                if (r == ROWS - 1) s.print("━━━");
                else s.print("───");
                
                if (c == COLS - 1) {
                    if (r == ROWS - 1) s.print("┛");
                    else s.print("┨");
                }
            }
            s.println();
        }
        
        // column labels
        for (int c = 0; c < COLS; c++) {
            s.print("  " + Integer.toString(c + 1) + " ");
        }
        s.println();
    }
}
