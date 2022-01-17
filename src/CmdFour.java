import java.util.Scanner;

public class CmdFour {
    private static Board board;

    private static boolean quit; 
    public static void main(String[] args) throws Exception {
        board = new Board(System.out);
        quit = false;
        readInput();
    }

    private static void readInput() {
        Scanner input = new Scanner(System.in);

        while (!quit) {
            String cmd = input.nextLine();
            
            if (cmd.equals("quit")) {
                quit = true;
            } else {
                try {
                    int move = Integer.parseInt(cmd);
                    if (move >= 1 && move <= 7) {
                        board.takeTurn(move);
                    } else {
                        throw new RuntimeException();
                    }
                } catch(Exception e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
}
