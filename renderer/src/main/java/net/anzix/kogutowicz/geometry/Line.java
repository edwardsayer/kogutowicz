/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.style.FigureStyle;
import net.anzix.kogutowicz.style.LineStyle;
import net.anzix.kogutowicz.style.LineStyle;

/**
 *
 * @author elek
 */
public class Line extends GeometryElement<Style<LineStyle>> {

    private List<Point> points = new ArrayList();

    private CoordBox box = null;

    public Line(Style<LineStyle> style) {
        super(style);
    }

    public Line() {
    }

    void addPoint(double x, double y) {
        points.add(new Point(x, y));
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public CoordBox getBoundingBox() {
        if (box == null) {
            double minx = 0;
            double miny = 0;
            double maxx = 0;
            double maxy = 0;
            boolean start = true;
            for (Point point : points) {
                if (start) {
                    minx = point.getX();
                    maxx = point.getX();
                    miny = point.getY();
                    maxy = point.getY();
                    start = false;
                } else {
                    if (point.getX() < minx) {
                        minx = point.getX();
                    }
                    if (point.getX() > maxx) {
                        maxx = point.getX();
                    }
                    if (point.getY() < miny) {
                        miny = point.getY();
                    }
                    if (point.getY() > maxy) {
                        maxy = point.getY();
                    }
                }

            }
            box = new CoordBox(new CoordPair(minx, maxy), new CoordPair(maxx, miny));
        }
        return box;
    }
}
