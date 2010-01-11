/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

import net.anzix.kogutowicz.geometry.CoordBox;

/**
 *
 * @author elek
 */
public class Box {

    private Node topLeft;

    private Node bottomRight;

    public Box(Node topLeft, Node bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Box(double x1, double y1, double x2, double y2) {
        this.topLeft = new Node(x1, y1);
        this.bottomRight = new Node(x2, y2);
    }

    public boolean isContains(Node node) {
        return node.getLatitude() <= topLeft.getLatitude() && node.getLatitude() >= bottomRight.getLatitude() &&
                node.getLongitude() >= topLeft.getLongitude() && node.getLongitude() <= bottomRight.getLongitude();
    }

    public Node getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Node bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Node getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Node topLeft) {
        this.topLeft = topLeft;
    }

    public CoordBox getCoordBox(){
        return new CoordBox(topLeft.getCoordPair(), bottomRight.getCoordPair());
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Box other = (Box) obj;
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
        int hash = 7;
        hash = 29 * hash + (this.topLeft != null ? this.topLeft.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Box(" + topLeft + "," + bottomRight + ")";
    }
}
