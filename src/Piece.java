public class Piece {
    private Player player;

    public Piece (Player player) {
        this.player = player;
    }

    public Player getPlayer() { return this.player; }

    public String print() {
        if (player == Player.USER) return "●";
        else return "◯";
        
    }
}
