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

        if (color == Color.WHITE) {
            opponentColor = Color.BLACK;
        } else {
            opponentColor = Color.WHITE;
        }
    }

    public void setOpponent(Player opponent) {
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
            if (baseY + step_forward < 8 && pawn.getY() + step_forward < 8
                    && pawn.getY() + step_forward >= 0 && baseY + step_forward >=0) {
                if (pawn.getY() == baseY && board.getSquare(pawn.getX(), baseY + step_forward).occupiedBy() == Color.NONE) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + step_forward), false);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), baseY).occupiedBy());
                    noMoves++;

                    if (baseY + 2 * step_forward < 8 && baseY + 2 * step_forward >= 0) {
                        if (board.getSquare(pawn.getX(), baseY + 2 * step_forward).occupiedBy() == Color.NONE) {
                            valid_moves[noMoves] = new Move(new Square(pawn.getX(), baseY), new Square(pawn.getX(), baseY + 2 * step_forward), false);
                            valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                            valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), baseY).occupiedBy());
                            noMoves++;
                        }
                    }
                } else if (board.getSquare(pawn.getX(), pawn.getY() + step_forward).occupiedBy() == Color.NONE) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX(), pawn.getY() + step_forward), false);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                    noMoves++;
                }
            }

            if (pawn.getX() < 7 && pawn.getY() + step_forward < 8
                    && pawn.getY() + step_forward >= 0 && baseY + step_forward >=0) {
                if (board.getSquare(pawn.getX() + 1, pawn.getY() + step_forward).occupiedBy() == opponentColor) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() + 1, pawn.getY() + step_forward), true);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                    noMoves++;
                }

                if (board.getSquare(pawn.getX() + 1, pawn.getY() + step_forward).occupiedBy() == Color.NONE &&
                        board.getSquare(pawn.getX() + 1, pawn.getY()).occupiedBy() == opponentColor &&
                        pawn.getY() == 7 - baseY - (2 * step_forward)) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() + 1, pawn.getY() + step_forward), true);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                    noMoves++;
                }
            }

            if (pawn.getX() > 0 && pawn.getY() + step_forward < 8
                    && pawn.getY() + step_forward >= 0 && baseY + step_forward >=0) {
                if (board.getSquare(pawn.getX() - 1, pawn.getY() + step_forward).occupiedBy() == opponentColor) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() - 1, pawn.getY() + step_forward), true);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                    noMoves++;
                }

                if (board.getSquare(pawn.getX() - 1, pawn.getY() + step_forward).occupiedBy() == Color.NONE &&
                        board.getSquare(pawn.getX() - 1, pawn.getY()).occupiedBy() == opponentColor &&
                        pawn.getY() == 7 - baseY - (2 * step_forward)) {
                    valid_moves[noMoves] = new Move(new Square(pawn.getX(), pawn.getY()), new Square(pawn.getX() - 1, pawn.getY() + step_forward), true);
                    valid_moves[noMoves].getFrom().setOccupier(Color.NONE);
                    valid_moves[noMoves].getTo().setOccupier(board.getSquare(pawn.getX(), pawn.getY()).occupiedBy());
                    noMoves++;
                }
            }
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
        int moveStep = 1;
        int border = 7;
        if (color == Color.BLACK) {
            moveStep = -1;
            border = 0;
        }

        for (int y = square.getY(); y <= border; y += moveStep) {
            if (y < 8 && y >= 0) {
                if (board.getSquare(square.getX(), y).occupiedBy() == opponentColor) {
                    return false;
                }
            }
        }

        for (int y = square.getY() + 1; y <= border; y += moveStep) {
            if (square.getX() < 7 && y >= 0 && y < 8) {
                if (board.getSquare(square.getX() + 1, y).occupiedBy() == opponentColor) {
                    return false;
                }
            }

            if (square.getX() > 0 && y >= 0 && y < 8) {
                if (board.getSquare(square.getX() - 1, y).occupiedBy() == opponentColor) {
                    return false;
                }
            }
        }

        return true;
    }

    public void makeMove() {
        Random rg = new Random();
        Move[] moves = getAllValidMoves();
        game.applyMove(moves[rg.nextInt(moves.length)]);
    }
}
