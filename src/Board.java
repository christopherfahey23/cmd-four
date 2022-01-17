import java.io.PrintStream;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private Piece[][] board = new Piece[ROWS][COLS];

    public Board() {

    }

    public void print(PrintStream s) {
        s.println("Hello");
    }
}
