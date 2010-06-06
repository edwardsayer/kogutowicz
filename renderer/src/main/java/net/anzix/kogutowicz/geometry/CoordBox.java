package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.Geometry;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.element.Box;

/**
 * Pair of geometrical coords.
 * @author elek
 */
public class CoordBox {

    private CoordPair topLeft;

    private CoordPair bottomRight;

    public CoordBox(CoordPair topLeft, CoordPair bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public CoordBox(Point topLeft, Point bottomRight) {
        this.topLeft = new CoordPair(topLeft.getX(), topLeft.getY());
        this.bottomRight = new CoordPair(bottomRight.getX(), bottomRight.getY());
    }

    public CoordBox(double tlx, double tly, double brx, double bry) {
        this.topLeft = new CoordPair(tlx, tly);
        this.bottomRight = new CoordPair(brx, bry);
    }

    public CoordPair getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(CoordPair bottomRight) {
        this.bottomRight = bottomRight;
    }

    public CoordPair getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(CoordPair topLeft) {
        this.topLeft = topLeft;
    }

    public static CoordBox projectFromBox(Projection proj, Box box) {
        double[] p1 = proj.getXY(box.getTopLeft().getLongitude(), box.getTopLeft().getLatitude());
        double[] p2 = proj.getXY(box.getBottomRight().getLongitude(), box.getBottomRight().getLatitude());
        return new CoordBox(p1[0], p1[1], p2[0], p2[1]);


    }

    public boolean intersect(CoordBox boundingBox) {
        CoordPair tl = boundingBox.getTopLeft();
        CoordPair br = boundingBox.getBottomRight();
        return Geometry.isRectangleIntersectingRectangle(tl.getX(), tl.getY(), br.getX(), br.getY(), getTopLeft().getX(), getTopLeft().getY(), getBottomRight().getX(), getBottomRight().getY());
    }

    @Override
    public String toString() {
        return "[" + topLeft + ":" + bottomRight + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoordBox other = (CoordBox) obj;
        if (this.topLeft != other.topLeft && (this.topLeft == null || !this.topLeft.equals(other.topLeft))) {
            return false;
        }
        if (this.bottomRight != other.bottomRight && (this.bottomRight == null || !this.bottomRight.equals(other.bottomRight))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.topLeft != null ? this.topLeft.hashCode() : 0);
        return hash;
    }
}
