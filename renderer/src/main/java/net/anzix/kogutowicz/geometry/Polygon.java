/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.style.PolygonStyle;

/**
 *
 * @author elek
 */
public class Polygon extends GeometryElement<Style<PolygonStyle>> {

    private Line outline = new Line();

    private CoordBox box;

    public Polygon(Style<PolygonStyle> style) {
        super(style);
    }

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
}
