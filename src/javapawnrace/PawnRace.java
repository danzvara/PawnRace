package javapawnrace;

public class PawnRace {
    private static Color currenPlayer;
    private static char whiteGap;
    private static char blackGap;
    private static boolean player1Computer;
    private static boolean player2Computer;
    private static Board board;
    private static Game game;
    private static Player player1;
    private static Player player2;

    public static void main(String[] args) {
        if (args[0].equals("P")) {
            player1Computer = false;
        } else if (args[0].equals("C")) {
            player1Computer = true;
        }

        if (args[1].equals("P")) {
            player2Computer = false;
        } else if (args[1].equals("C")) {
            player2Computer = true;
        }

        whiteGap = args[2].charAt(0);
        blackGap = args[3].charAt(0);
        board = new Board(whiteGap, blackGap);
        game = new Game(board);
        player1 = new Player(game, board, Color.WHITE, player1Computer);
        player2 = new Player(game, board, Color.BLACK, player2Computer);

        board.display();
    }
}