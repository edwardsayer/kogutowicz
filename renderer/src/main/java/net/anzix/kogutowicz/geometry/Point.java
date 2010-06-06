package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.renderer.Transformation;

/**
 * Simple point object.
 * @author elek
 */
public class Point extends GeometryElement {

    private CoordPair coords;

    public Point(double x, double y) {
        this.coords = new CoordPair(x, y);
    }

    public Point(double coord[]) {
        this.coords = new CoordPair(coord[0], coord[1]);
    }

    public Point(CoordPair p) {
        this.coords = p;
    }

    public Point(Point p) {
        this(p.getX(), p.getY());
    }

    public Point transform(Transformation t) {
        return new Point(coords.transform(t));
    }

    public double getX() {
        return coords.getX();
    }

    public void setX(double x) {
        this.coords.setX(x);
    }

    @Override
    public String toString() {
        return "[" + coords.getX() + "," + coords.getY() + "]";
    }

    public double getY() {
        return coords.getY();
    }

    public void setY(double y) {
        coords.setY(y);
    }

    @Override
    public CoordBox getBoundingBox() {
        return new CoordBox(coords, coords);
    }

    public static Point projectFromNode(Projection projection, Node node) {
        return new Point(projection.getXY(node.getLongitude(), node.getLatitude()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.coords != other.coords && (this.coords == null || !this.coords.equals(other.coords))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.coords != null ? this.coords.hashCode() : 0);
        return hash;
    }

}
