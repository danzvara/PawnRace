package javapawnrace;

public class Square {

    private int y;
    private int x;
    private Color color;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.NONE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color occupiedBy() {
        return color;
    }

    public void setOccupier(Color new_color) {
        color = new_color;
    }
}
