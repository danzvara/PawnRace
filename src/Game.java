/**
 * Created by daniel on 01/12/15.
 */
public class Game {
    private Move[] moves;
    private int move_index;
    private COLOR currentPlayer;
    private Board gameBoard;

    public Game(Board board) {
        moves = new Move[400];
        move_index = 0;
        currentPlayer = WHITE;
        gameBoard = board;
    }

    public Color getPlayerColor() {
        return currentPlayer;
    }

    public Move getLastMove() {
        if (move_index == 0) {
            return NULL;
        }

        return moves[move_index - 1];
    }

    public void applyMove(Move move) {
        moves[move_index] = move;
        move_index++;
        gameBoard.applyMove(move);
        if (currentPlayer == BLACK) {
            currentPlayer = WHITE;
        } else {
            currentPlayer = BLACK;
        }
    }

    public void unapplyMove(Move move) {
        if (move_index == 0) {
            return;
        }
        gameBoard.unApplyMove(move);
        moves[move_index - 1] = NULL;
        move_index--;
        if (currentPlayer == WHITE) {
            currentPlayer = BLACK;
        } else {
            currentPlayer = WHITE;
        }
    }

    public boolean isFinished() {
        Move lastMove = getLastMove();
        int whites = 0;
        int blacks = 0;
        COLOR player;

        if (lastMove.getTo().getY() == 7 || lastMove.getTo().getY == 0) {
            return true;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard.getSquare(i, j).occupiedBy() == WHITE) {
                    whites++;
                } else if (gameBoard.getSquare(i, j).occupiedBy() == BLACK)
                    blacks++;
                }
            }
        }

        if (whites == 0 || blacks == 0) {
            return true;
        }

        whites = 0;
        blacks = 0;
        for (int j = 1; j < 7; j++) {
            for (int i = 0; i < 8; i++) {
                player = this.gameBoard.getSquare(i, j);
                if (player == NONE) {
                    continue;
                }

                if (player == BLACK) {
                    blacks += isBlocked(j, i, BLACK);
                } else {
                    blacks += isBlocked(j, i, WHITE);
                }
            }
        }

        if (whites == 0 || blacks == 0) {
            return true;
        }

        return false;
    }

    //returns zero if the pawn is blocked, one if is free 
    private int isBlocked(int y, int x, COLOR player) {
        if (player == WHITE) {
            if (gameBoard.getSquare(x, y+1).occupiedBy() == NONE) {
                return 1;
            } 
            //TODO:Implement en passant move
            //else if (x < 7 && gameBoard.getSquare(x+1, y) == BLACK && gameBoard.getSquare(x+1, y+1) == NONE) {
            //    return 1;
            //} else if (x > 0 && gameBoard.getSquare(x-1, y) == BLACK && gameBoard.getSquare(x-1, y+1) == NONE) {
        } else if (player == BLACK) {
            if (gameBoard.getSquare(x, y-1).occupiedBy() == NONE) {
                return 1;
            }
        }
        return 0;
    }

    public COLOR getGameResult() {
        if (currentPlayer == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }

    public Move parseMove(String san) {
        int fromX = (int) san.charAt(0) - (int) 'a';
        int fromY = (int) san.charAt(1) - (int) '0';
        int toX = (int) san.charAt(2) - (int) 'a';
        int toY = (int) san.charAt(3) - (int) '0';
        Square sqF, sqT;
        Move move;
        COLOR player = gameBoard.getSquare(fromX, fromY).occupiedBy();
        boolean isCapture;
        if (player == WHITE) {
            if (gameBoard.getSquare(toX, toY).occupiedBy() == BLACK) {
                isCapture = true;
            } else if(gameBoard.getSquare(toX, toY).occupiedBy() == WHITE) {
                return NULL;
            } else {
                isCapture = false;
            }
        }

        if (player == BLACK) {
            if (gameBoard.getSquare(toX, toY).occupiedBy() == WHITE) {
                isCapture = true;
            } else if(gameBoard.getSquare(toX, toY).occupiedBy() == BLACK) {
                return NULL;
            } else {
                isCapture = false;
            }
        } else {
            return NULL;
        }

        sqF = new Square(fromX, fromY);
        sqF.setOccupier(NONE);
        sqT = new Square(toX, toY);
        sqT.setOccupier(player);
        move = new Move(sqF, sqT, isCapture);
        return move;
    }
}
















