package javapawnrace;

public class PawnRace {
    private static char whiteGap;
    private static char blackGap;
    private static boolean player1Computer;
    private static boolean player2Computer;
    private static Board board;
    private static Game game;
    private static Player player1;
    private static Player player2;
    private static String command;
    private static Ai ai;

    public static void main(String[] args) {
        Move nextMove;

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
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        ai = new Ai(board, game, player1, player2);
        board.display();

        while(!game.isFinished()) {
            if (game.getPlayerColor() == player1.getColor() && player1Computer) {
                aiMove(player1.getColor(), player1.getAllValidMoves());
            } else if (game.getPlayerColor() == player2.getColor() && player2Computer) {
                aiMove(player2.getColor(), player2.getAllValidMoves());
            } else {
                /*for (int i = 0; i < game.move_index; i++) {
                    System.out.print((game.moves[i]).getSAN() + " | ");
                }*/
                System.out.println("");

                System.out.print("(" + game.getPlayerColor() + "): Input move in algebraic notation: ");
                command = IOUtil.readString();
                nextMove = game.parseMove(command);
                if (nextMove == null) {
                    continue;
                }

                if (!command.equals("back")) {
                    game.applyMove(nextMove);
                }
            }

            board.display();
        }

        System.out.print("The " + game.getGameResult() + " won!");
    }

    private static void aiMove(Color c, Move[] possibleMoves) {
        game.applyMove(ai.getNextMove(possibleMoves, c));
    }
}