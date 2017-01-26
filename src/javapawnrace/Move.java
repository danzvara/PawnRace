package javapawnrace;

public class Move {

    private Square from;
    private Square to;
    private boolean captured;

    public Move(Square from, Square to, boolean captured) {
        this.from = from;
        this.to = to;
        this.captured = captured;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public boolean isCaptured() {
        return captured;
    }

    public String getSAN() {
        String originColumn = "";

        if (isCaptured()) {
            originColumn = Character.toString((char) ((int) 'a' + this.from.getX()));
            originColumn += "x";
        }

        String letter = Character.toString((char) ((int) 'a' + this.to.getX()));
        String number = Character.toString((char) ((int) '1' + this.to.getY()));
        return (originColumn + letter + number);
    }
}
