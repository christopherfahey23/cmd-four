public class CmdFour {
    private static Game game;
    public static void main(String[] args) throws Exception {
        game = new Game(System.out, System.in);
        game.start();
    }
}
