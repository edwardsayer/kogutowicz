/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;

/**
 *
 * @author elek
 */
public class TileRectangle {

    private Node topLeft;

    private Node bottomRight;

    private Zoom zoom;

    private TileState state = TileState.INIT;

    public TileRectangle(Node topLeft, Node bottomRight, Zoom zoom) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.zoom = zoom;
    }

    public Node getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Node bottomRight) {
        this.bottomRight = bottomRight;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public Node getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Node topLeft) {
        this.topLeft = topLeft;
    }

    public Zoom getZoom() {
        return zoom;
    }

    public void setZoom(Zoom zoom) {
        this.zoom = zoom;
    }

    public Box getBox() {
        return new Box(topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileRectangle other = (TileRectangle) obj;
        if (this.topLeft != other.topLeft && (this.topLeft == null || !this.topLeft.equals(other.topLeft))) {
            return false;
        }
        if (this.bottomRight != other.bottomRight && (this.bottomRight == null || !this.bottomRight.equals(other.bottomRight))) {
            return false;
        }
        if (this.zoom != other.zoom && (this.zoom == null || !this.zoom.equals(other.zoom))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.topLeft != null ? this.topLeft.hashCode() : 0);
        return hash;
    }
}
