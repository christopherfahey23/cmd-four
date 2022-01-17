import java.util.Scanner;

public class CmdFour {
    private static Board board;

    private static boolean quit; 
    public static void main(String[] args) throws Exception {
        board = new Board();
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
                        // TODO: call method in Board
                        // board.takeTurn(move);
                        
                        System.out.println("Row " + move);
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
