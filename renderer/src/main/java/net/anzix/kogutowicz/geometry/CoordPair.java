package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.renderer.Transformation;

/**
 * Location of the geometry space.
 * 
 * @author elek
 */
public class CoordPair {

    double x;

    double y;

    public CoordPair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public CoordPair transform(Transformation t) {
        return t.transform(this);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";


    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoordPair other = (CoordPair) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        return hash;
    }
}
