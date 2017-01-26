package javapawnrace;

public class Game {
    Move[] moves;
    int move_index;
    private Color currentPlayer;
    private Board gameBoard;

    public Game(Board board) {
        moves = new Move[400];
        move_index = 0;
        currentPlayer = Color.WHITE;
        gameBoard = board;
    }

    public Color getPlayerColor() {
        return currentPlayer;
    }

    public Move getLastMove() {
        if (move_index == 0) {
            return null;
        }

        return moves[move_index - 1];
    }

    public void applyMove(Move move) {
        moves[move_index] = move;
        move_index++;
        gameBoard.applyMove(move);
        if (currentPlayer == Color.BLACK) {
            currentPlayer = Color.WHITE;
        } else {
            currentPlayer = Color.BLACK;
        }
    }

    public void unapplyMove(Move move) {
        if (move_index == 0) {
            return;
        }
        gameBoard.unApplyMove(move);
        moves[move_index - 1] = null;
        move_index--;
        if (currentPlayer == Color.WHITE) {
            currentPlayer = Color.BLACK;
        } else {
            currentPlayer = Color.WHITE;
        }
    }

    public boolean isFinished() {
        Move lastMove = getLastMove();
        int whites = 0;
        int blacks = 0;
        Color player;

        if(lastMove != null) {
            if (lastMove.getTo().getY() == 7 || lastMove.getTo().getY() == 0) {
                return true;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard.getSquare(i, j).occupiedBy() == Color.WHITE) {
                    whites++;
                } else if (gameBoard.getSquare(i, j).occupiedBy() == Color.BLACK) {
                    blacks++;
                }
            }
        }

        if ((whites == 0) || (blacks == 0)) {
            return true;
        }

        whites = 0;
        blacks = 0;
        for (int j = 1; j < 7; j++) {
            for (int i = 0; i < 8; i++) {
                player = gameBoard.getSquare(i, j).occupiedBy();
                if (player == Color.BLACK) {
                    blacks += isBlocked(j, i, Color.BLACK);
                } else if (player == Color.WHITE){
                    whites += isBlocked(j, i, Color.WHITE);
                }
            }
        }

        if (whites == 0 || blacks == 0) {
            return true;
        }

        return false;
    }

    //returns zero if the pawn is blocked, one if is free 
    private int isBlocked(int y, int x, Color player) {
        Color oppositePlayer = Color.BLACK;
        int borderLine = 4;
        int moveStep = 1;
        if (currentPlayer == Color.BLACK) {
            oppositePlayer = Color.WHITE;
            borderLine = 3;
            moveStep = -1;
        }

        if (gameBoard.getSquare(x, y + moveStep).occupiedBy() == Color.NONE) {
            return 1;
        } else if (x < 7) {
            if (gameBoard.getSquare(x + 1, y + moveStep).occupiedBy() == oppositePlayer) {
                return 1;
            }

            if (gameBoard.getSquare(x + 1, y + moveStep).occupiedBy() == Color.NONE &&
                    gameBoard.getSquare(x + 1, y).occupiedBy() == oppositePlayer &&
                    y == borderLine) {
                return 1;
            }
        } else if (x > 0) {
            if (gameBoard.getSquare(x - 1, y + moveStep).occupiedBy() == oppositePlayer) {
                return 1;
            }

            if (gameBoard.getSquare(x - 1, y + moveStep).occupiedBy() == Color.NONE &&
                    gameBoard.getSquare(x - 1, y).occupiedBy() == oppositePlayer &&
                    y == borderLine) {
                return 1;
            }
        }
        return 0;
    }

    public Color getGameResult() {
        if (currentPlayer == Color.WHITE) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    public Move parseMove(String san) {

        boolean isCapture = true;
        boolean enPassant = false;
        int toX = 0, toY = 0, fromX = 0, fromY = 0;
        int firstMoveBorder = 3;
        int moveStep = -1;
        Color player = getPlayerColor();
        Color oppositePlayer;

        if (player == Color.WHITE) {
            oppositePlayer = Color.BLACK;
        } else {
            oppositePlayer = Color.WHITE;
            firstMoveBorder = 4;
            moveStep = 1;
        }

        if (san.equals("back")) {
            unapplyMove(moves[move_index - 1]);
            unapplyMove(moves[move_index - 1]);
        } else if (san.length() == 2) {
            isCapture = false;
            toX = (int) san.charAt(0) - (int) 'a';
            toY = (int) san.charAt(1) - (int) '1';
            fromX = toX;
            if (toY == firstMoveBorder &&
                    gameBoard.getSquare(toX, toY + 2 * moveStep).occupiedBy() == player) {
                fromY = toY + 2 * moveStep;
            } else if (toY == firstMoveBorder &&
                    gameBoard.getSquare(toX, toY + moveStep).occupiedBy() == player) {
                fromY = toY + moveStep;
            } else if (toY == firstMoveBorder + moveStep &&
                    gameBoard.getSquare(toX, toY + moveStep).occupiedBy() == player) {
                fromY = toY + moveStep;
            } else if (toY > firstMoveBorder &&
                    gameBoard.getSquare(toX, toY + moveStep).occupiedBy() == player) {
                fromY = toY + moveStep;
            } else {
                System.out.print("Wrong move");
                return null;
            }
        } else {
            toX = (int) san.charAt(2) - (int) 'a';
            toY = (int) san.charAt(3) - (int) '1';
            fromX = (int) san.charAt(0) - (int) 'a';
            if (gameBoard.getSquare(toX, toY).occupiedBy() == oppositePlayer
                && gameBoard.getSquare(fromX, toY + moveStep).occupiedBy() == player) {
                fromY = toY + moveStep;
            } else if (gameBoard.getSquare(toX, toY).occupiedBy() == Color.NONE &&
                        gameBoard.getSquare(toX, toY + moveStep).occupiedBy() == oppositePlayer &&
                        gameBoard.getSquare(fromX, toY + moveStep).occupiedBy() == player &&
                        toY + moveStep == 7 - firstMoveBorder) {
                fromY = toY + moveStep;
                enPassant = true;
            } else {
                System.out.print("Wrong move");
                return null;
            }

        }

        Square sqF, sqT;
        Move move;
        sqF = new Square(fromX, fromY);
        sqF.setOccupier(Color.NONE);
        sqT = new Square(toX, toY);
        sqT.setOccupier(player);
        move = new Move(sqF, sqT, isCapture);
        if (enPassant) {
            gameBoard.getSquare(toX, toY + moveStep).setOccupier(Color.NONE);
        }

        return move;
    }
}