/**
 * Created by daniel on 01/12/15.
 */
public class Board {
    private Square[][] board = new Square[8][8];

    public Board(char whiteGap, char blackGap) {
        boolean black = true;
        int whiteGapI = (int) whiteGap - (int) 'a';
        int blackGapI = (int) blackGap - (int) 'a';

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (j == 1 && i != whiteGap) {
                    board[j][i].setOccupier(WHITE);
                }
                else if (j == 6 && i != blackGap) {
                    board[j][i].setOccupier(BLACK);
                }
            }
        }
    }

    public Square getSquare(int x, int y) {
        return board[y][x];
    }

    public void applyMove(Move move) {
        from = move.getFrom;
        to = move.getTo;
        board[from.getY][from.getX].setOccupier(NONE);
        board[to.getY][to.getX].setOccupier(from.occupiedBy());
    }

    public void unApplyMove(Move move) {
        from = move.getFrom;
        to = move.getTo;
        if (move.isCaptured()) {
            switch (to.occupiedBy()) {
                case BLACK:
                              board[to.getY][to.getX].setOccupier(WHITE);
                              break;
                case WHITE:
                              board[to.getY][to.getX].setOccupier(BLACK);
                              break;
            }
        } else {
            board[to.getY][to.getX].setOccupier(NONE);
        }

        board[from.getY][from.getX].setOccupier(from.occupiedBy());
    }

    public void display() {
        for (int j = 12; j > 0; j--] {
            for (int i = 0; i < 12; i++) {
                if (j == 12) {
                    System.out.print(" ");
                } else if (j == 0 && i > 2 && i < 11) {
                    System.out.print((char) ((int) 'a' + i - 3));
                } else if (j < 11 && j > 1 && i == 0) {
                    System.out.print(j - 2);
                } else if (j < 11 && j > 1 && i > 1 && i < 11) {
                    switch ((board[j-2][i-2].getSquare()).occupiedBy()) {
                        case BLACK:
                                      System.out.print("B");
                                      break;
                        case WHITE:
                                      System.out.print("W");
                                      break;
                        case NONE:
                                      System.out.print(".");
                                      break;
                    }
                }
            }
            System.out.print("\n");
    }
}
