/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

/**
 * Element represents an OSM node object.
 *
 * @author elek
 */
public class Node extends Element {

    /**
     * S-W --- Y
     */
    private double latitude;

    /**
     * E-W --- X
     */
    private double longitude;

    public Node() {
    }

    public Node(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * return latitude (y coord).
     *
     * 
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean isIntersect(Box box) {
        return box.isContains(this);
    }

    public double getDistanceFrom(Node node) {
        return Math.sqrt(Math.pow(this.getLatitude() - node.getLatitude(), 2) + Math.pow(this.getLongitude() - node.getLongitude(), 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.latitude != other.latitude) {
            return false;
        }
        if (this.longitude != other.longitude) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "Node(" + getLongitude() + "," + getLatitude() + ")";
    }
}
