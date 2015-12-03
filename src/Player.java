/**
 * Created by daniel on 01/12/15.
 */
import java.util.Random;

public class Player {
    private Game game;
    private Board board;
    private COLOR color;
    private COLOR opponentColor;
    private boolean isComputerPlayer;

    public Player(Game g, Board b, Color c, boolean isPCplayer) {
        this.game = g;
        this.board = b;
        this.color = c;
        this.isComputerPlayer = isPCplayer;
    }

    public void setOpponent(Player opponent) {
        //TODO: add opponent data
        this.opponentColor = opponent.getColor();
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
                if (board.getSquare(i, j).occupiedBy() == this.getColor()) {
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
        Square[] pawns = this.getAllPawns();
        Move[] valid_moves = new Move[32];
        Square pawn;
        int noMoves;
        COLOR oppositte
        int step_forward = -1;
        int baseY = 6;
        if (getColor() == WHITE) {
            step_forward = 1;
            baseY = 1;
        }

        for (int i = 0; i < pawns.length; i++) {
            pawn = pawns[i];
            if (pawn.getY() == baseY && board.getSquare(pawn.getX(), baseY + step_forward).occupiedBy() == NONE) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + step_forward), false);
                noMoves++;
                if(board.getSquare(pawn.getX(), baseY + 2 * step_forward).occupiedBy() == NONE) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + 2 * step_forward), false);
                    noMoves++;
                }
            } else if (board.getSquare(pawn.getX(), pawn.getY() + step_forward).occupiedBy() == NONE) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX(), pawn.getY() + step_forward), false);
                noMoves++;
            } else if (board.getSquare(pawn.getX() + 1, pawn.getY() + step_forward).occupiedBy() == this.opponentColor) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() + 1, pawn.getY() + step_forward), true);
                noMoves++;
            } else if (board.getSquare(pawn.getX() - 1, pawn.getY() + step_forward).occupiedBy() == this.opponentColor) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() - 1, pawn.getY() + step_forward), true);
                noMoves++;
            }
            //TODO: en-passant
        }

        Move[] returnMoves = new Move[noMoves];
        for (int i = 0; i < 32; i++) {
            if ( valid_moves[i] != 0) {
                returnMoves[noMoves-1] = valid_moves[i];
                noMoves--;
            }
        }

        return returnMoves;
    }

    public boolean isPassedPawn(Square square) {
        //TODO: implement is passed pawn
    }

    public void makeMove() {
        Random rg = new Random();
        Moves[] moves = getAllValidMoves();
        game.applyMove(moves[rg.nextInt(moves.length));
    }
}
