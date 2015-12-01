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

}
