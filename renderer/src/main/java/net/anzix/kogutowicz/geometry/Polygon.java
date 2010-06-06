/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

/**
 * Polygonal form.
 * 
 * @author elek
 */
public class Polygon extends GeometryElement {

    private Color color = Color.BLACK;

    private float width = 1f;

    private Line outline = new Line();

    private CoordBox box;

    public void addPoint(Point p) {
        outline.addPoint(p);
    }

    public Line getOutline() {
        return outline;
    }

    public void setOutline(Line outline) {
        this.outline = outline;
    }

    @Override
    public CoordBox getBoundingBox() {
        if (box == null) {
            box = outline.getBoundingBox();
        }
        return box;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
}
