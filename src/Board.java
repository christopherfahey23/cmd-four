import java.io.PrintStream;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];

    public Board() {
        
    }

    public void print(PrintStream s) {
        s.println("Hello");

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
                s.print("┃   ");

                
                // TODO: print pieces
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
