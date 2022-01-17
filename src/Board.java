import java.io.PrintStream;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];

    public Board() {

    }

    public void print(PrintStream s) {
        s.println("Hello");

        for (int r = 0; r < ROWS; r++) {
            // top of box
            for (int c = 0; c < COLS; c++) {
                // top row
                if (r == 0) {
                    // upper left vs. center
                    if (c == 0) s.print("┌");
                    else s.print("┬");

                    s.print("───");
                    
                    // upper right
                    if (c == COLS - 1) s.print("┐");
                }
                else {
                    // left edge
                    if (c == 0) s.print("├");
                    else s.print("┼");

                    s.print("───");

                    // right edge
                    if (c == COLS - 1) s.print("┤");
                }
            }
            s.println();

            // middle of box
            for (int c = 0; c < COLS; c++) {
                s.print("│   ");
            }
            s.println("│");
        }
        // bottom of board
        for (int c = 0; c < COLS; c++) {
            if (c == 0) s.print("└");
            else s.print("┴");

            s.print("───");
        }
        s.println("┘");

        for (int c = 0; c < COLS; c++) {
            s.print("  " + Integer.toString(c + 1) + " ");
        }
        
    }
}
