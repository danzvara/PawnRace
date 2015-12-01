/**
 * Created by daniel on 01/12/15.
 */
public class Move {

    private Square from;
    private Square to;
    private boolean captured;

    public Move(Square inFrom, Square inTo, boolean inCaptured) {
        from = inFrom;
        to = inTo;
        captured = inCaptured;
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
        String SAN;
        String letter = Character.toString((char) ((int)'a' + to.x));
        String number = Character.toString((char) ((int)'0' + to.y));
        return (letter + number);
    }
}
