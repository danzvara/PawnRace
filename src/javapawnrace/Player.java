package javapawnrace;

import java.util.Random;

public class Player {
    private Game game;
    private Board board;
    private Color color;
    private Color opponentColor;
    private boolean isComputerPlayer;

    public Player(Game game, Board board, Color color, boolean isComputerPlayer) {
        this.game = game;
        this.board = board;
        this.color = color;
        this.isComputerPlayer = isComputerPlayer;
    }

    public void setOpponent(Player opponent) {
        //TODO: add opponent data
        this.opponentColor = opponent.getColor();
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isComputerPlayer() {
        return this.isComputerPlayer;
    }

    public Square[] getAllPawns() {
        Square[] playerPawns = new Square[8];
        int numOfPawns = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquare(i, j).occupiedBy() == this.getColor()) {
                    playerPawns[numOfPawns] = this.board.getSquare(i, j);
                    numOfPawns++;
                }
            }
        }

        Square[] livingPlayerPawns = new Square[numOfPawns];
        for (int i = 0; i < 8; i++) {
            if (playerPawns[i] != null) {
                livingPlayerPawns[numOfPawns - 1] = playerPawns[i];
                numOfPawns--;
            }
        }

        return livingPlayerPawns;
    }

    public Move[] getAllValidMoves() {
        Square[] pawns = this.getAllPawns();
        Move[] valid_moves = new Move[32];
        Square pawn;
        int noMoves = 0;
        int step_forward = -1;
        int baseY = 6;
        if (getColor() == Color.WHITE) {
            step_forward = 1;
            baseY = 1;
        }

        for (int i = 0; i < pawns.length; i++) {
            pawn = pawns[i];
            if (pawn.getY() == baseY && board.getSquare(pawn.getX(), baseY + step_forward).occupiedBy() == Color.NONE) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + step_forward), false);
                valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), baseY).occupiedBy());
                noMoves++;
                if (board.getSquare(pawn.getX(), baseY + 2 * step_forward).occupiedBy() == Color.NONE) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + 2 * step_forward), false);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), baseY).occupiedBy());
                    noMoves++;
                }
            } else if (board.getSquare(pawn.getX(), pawn.getY() + step_forward).occupiedBy() == Color.NONE) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX(), pawn.getY() + step_forward), false);
                valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                noMoves++;
            } else if (board.getSquare(pawn.getX() + 1, pawn.getY() + step_forward).occupiedBy() == this.opponentColor) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() + 1, pawn.getY() + step_forward), true);
                valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                noMoves++;
            } else if (board.getSquare(pawn.getX() - 1, pawn.getY() + step_forward).occupiedBy() == this.opponentColor) {
                valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() - 1, pawn.getY() + step_forward), true);
                valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                noMoves++;
            }
            //TODO: en-passant
        }

        Move[] returnMoves = new Move[noMoves];
        for (int i = 0; i < 32; i++) {
            if (valid_moves[i] != null) {
                returnMoves[noMoves - 1] = valid_moves[i];
                noMoves--;
            }
        }

        return returnMoves;
    }

    public boolean isPassedPawn(Square square) {
        //TODO: implement is passed pawn
        return false;
    }

    public void makeMove() {
        Random rg = new Random();
        Move[] moves = getAllValidMoves();
        game.applyMove(moves[rg.nextInt(moves.length)]);
    }
}
