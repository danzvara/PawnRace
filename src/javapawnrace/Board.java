package javapawnrace;

public class Board {
    private Square[][] board = new Square[8][8];
    private int whiteGap;
    private int blackGap;

    public Board(char whiteGap, char blackGap) {
        this.whiteGap = (int) whiteGap - (int) 'A';
        this.blackGap = (int) blackGap - (int) 'A';

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                board[j][i] = new Square(i, j);
                if (j == 1 && i != this.whiteGap) {
                    board[j][i].setOccupier(Color.WHITE);
                } else if (j == 6 && i != this.blackGap) {
                    board[j][i].setOccupier(Color.BLACK);
                }
            }
        }
    }

    public Square getSquare(int x, int y) {
        return board[y][x];
    }

    public void applyMove(Move move) {
        Square from = move.getFrom();
        Square to = move.getTo();
        if (!move.isCaptured()) {
            board[to.getY()][to.getX()].setOccupier(to.occupiedBy());
            board[from.getY()][from.getX()].setOccupier(Color.NONE);
        } else {
            //Testing for en passant move
            if (board[to.getY()][to.getX()].occupiedBy() == Color.NONE) {
                board[to.getY()][to.getX()].setOccupier(to.occupiedBy());
                board[from.getY()][from.getX()].setOccupier(Color.NONE);
                board[from.getY()][to.getX()].setOccupier(Color.NONE);
            } else {
                board[to.getY()][to.getX()].setOccupier(to.occupiedBy());
                board[from.getY()][from.getX()].setOccupier(Color.NONE);
            }
        }
    }

    public void unApplyMove(Move move) {
        Square from = move.getFrom();
        Square to = move.getTo();
        board[from.getY()][from.getX()].setOccupier(to.occupiedBy());
        if (move.isCaptured()) {
            switch (to.occupiedBy()) {
                case BLACK:
                    board[to.getY()][to.getX()].setOccupier(Color.WHITE);
                    break;
                case WHITE:
                    board[to.getY()][to.getX()].setOccupier(Color.BLACK);
                    break;
                //En passant
                case NONE:
                    board[to.getY()][to.getX()].setOccupier(Color.NONE);
                    switch (from.occupiedBy()) {
                        case BLACK:
                            board[from.getY()][to.getX()].setOccupier(Color.WHITE);
                            break;
                        case WHITE:
                            board[from.getY()][to.getX()].setOccupier(Color.BLACK);
                            break;
                    }
                    break;
            }
        } else {
            board[to.getY()][to.getX()].setOccupier(Color.NONE);
        }
    }

    public void display() {
        for (int j = 12; j > 0; j--) {
            for (int i = 0; i < 12; i++) {
                if (j == 12) {
                    System.out.print(" ");
                } else if (j == 1 && i == 0) {
                    System.out.print("#");
                } else if (j == 1 && i > 2 && i < 11) {
                    System.out.print((char) ((int) 'a' + i - 3));
                } else if (j < 10 && j > 1 && i == 0) {
                    System.out.print(j - 1);
                } else if (j < 10 && j > 1 && i > 1 && i < 10) {
                    switch (board[j - 2][i - 2].occupiedBy()) {
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
}
