package net.anzix.kogutowicz.geometry;

/**
 * Simplme text element on the map.
 * @author elek
 */
public class Label extends Point {

    private String messages;

    private int size = 10;

    public Label(Point p) {
        super(p);
    }

    public Label(CoordPair p) {
        super(p);
    }

    public Label(double[] coord) {
        super(coord);
    }

    public Label(double x, double y) {
        super(x, y);
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    
}
