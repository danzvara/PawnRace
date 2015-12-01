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
 
        if (lastMove.getTo().getY() == 7 || lastMove.getTo().getY == 0) {
            return true;
        }

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (board[j][i].occupiedBy() == 
        for (int i = -1; i < 2; i++) {
             
    }
}
















