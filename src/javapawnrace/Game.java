package javapawnrace;

public class Game {
    private Move[] moves;
    private int move_index;
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

        if (lastMove.getTo().getY() == 7 || lastMove.getTo().getY() == 0) {
            return true;
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
                player = this.gameBoard.getSquare(i, j).occupiedBy();
                if (player == Color.NONE) {
                    continue;
                }

                if (player == Color.BLACK) {
                    blacks += isBlocked(j, i, Color.BLACK);
                } else {
                    blacks += isBlocked(j, i, Color.WHITE);
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
        if (player == Color.WHITE) {
            if (gameBoard.getSquare(x, y + 1).occupiedBy() == Color.NONE) {
                return 1;
            }
            //TODO:Implement en passant move
            //else if (x < 7 && gameBoard.getSquare(x+1, y) == BLACK && gameBoard.getSquare(x+1, y+1) == NONE) {
            //    return 1;
            //} else if (x > 0 && gameBoard.getSquare(x-1, y) == BLACK && gameBoard.getSquare(x-1, y+1) == NONE) {
        } else if (player == Color.BLACK) {
            if (gameBoard.getSquare(x, y - 1).occupiedBy() == Color.NONE) {
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
        int fromX = (int) san.charAt(0) - (int) 'a';
        int fromY = (int) san.charAt(1) - (int) '0';
        int toX = (int) san.charAt(2) - (int) 'a';
        int toY = (int) san.charAt(3) - (int) '0';
        Square sqF, sqT;
        Move move;
        Color player = gameBoard.getSquare(fromX, fromY).occupiedBy();
        boolean isCapture;
        if (player == Color.WHITE) {
            if (gameBoard.getSquare(toX, toY).occupiedBy() == Color.BLACK) {
                isCapture = true;
            } else if (gameBoard.getSquare(toX, toY).occupiedBy() == Color.WHITE) {
                return null;
            } else {
                isCapture = false;
            }
        }

        if (player == Color.BLACK) {
            if (gameBoard.getSquare(toX, toY).occupiedBy() == Color.WHITE) {
                isCapture = true;
            } else if (gameBoard.getSquare(toX, toY).occupiedBy() == Color.BLACK) {
                return null;
            } else {
                isCapture = false;
            }
        } else {
            return null;
        }

        sqF = new Square(fromX, fromY);
        sqF.setOccupier(Color.NONE);
        sqT = new Square(toX, toY);
        sqT.setOccupier(player);
        move = new Move(sqF, sqT, isCapture);
        return move;
    }
}











