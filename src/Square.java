/**
 * Created by daniel on 01/12/15.
 */
public class Square {

    public enum COLOR {
        BLACK, WHITE, NONE
    }

    private int y;
    private int x;
    private COLOR color;

    public Square(int inx, int iny) {
        x = inx;
        y = iny;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public COLOR occupiedBy() {
        return color;
    }

    public void setOccupier(COLOR new_color) {
        color = new_color;
    }
}