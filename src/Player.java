/**
 * Created by daniel on 01/12/15.
 */
public class Player {
    private Game game;
    private Board board;
    private Color color;
    private boolean isComputerPlayer;

    public Player(Game g, Board b, Color c, boolean isPCplayer) {
        this.game = g;
        this.board = b;
        this.color = c;
        this.isComputerPlayer = isPCplayer;
    }

    public void setOpponent(Player opponent) {
        //TODO: add opponent data
    }

    public void COLOR getColor() {
        return this.color;
    }

    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    public Square [] getAllPawns() {
        Square[] playerPawns = new Square[8];
        int numOfPawns = 0;
        for (int i = 0; i < 8; i++) {
            for (int j=0; j < 8; j++) {
                if (game.gameBoard.getSquare(i, j).occupiedBy() == this.getColor()) {
                    playerPawns[numOfPawns] = game.gameBoard.getSquare(i, j);
                    numOfPawns++;
                }
            }
        }

        Square[] livingPlayerPawns = new Square[numOfPawns];
        for (int i = 0; i < 8; i++) {
            if (playerPawns[i] != 0) {
                livingPlayerPawns[numOfPawns-1] = playerPawns[i];
                numOfPawns--;
            }
        }

        return livingPlayerPawns;
    }

    public Move [] getAllValidMoves() {
        //TODO: implement list of valid moves
    }

    public boolean isPassedPawn(Square square) {
        //TODO: implement is passed pawn
    }
}
