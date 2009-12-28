/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;

/**
 * Simple 1x1 division.
 * 
 * @author elek
 */
public class SimpleTileDivision implements TileDivision {

    private Zoom zoom;

    private Node topLeft;

    private Node bottomRight;

    public SimpleTileDivision(Zoom zoom, Node topLeft, Node bottomRight) {
        this.zoom = zoom;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public TileCoord getTileCoord(Node node) {
        return new TileCoord(0, 0);
    }

    @Override
    public Node getTopLeft(TileCoord coord) {
        return topLeft;
    }

    @Override
    public Node getBottomRight(TileCoord coord) {
        return bottomRight;
    }

    @Override
    public Zoom getZoom() {
        return zoom;
    }

    @Override
    public TileCoord getTileSize() {
        return new TileCoord(0, 0);
    }

    @Override
    public Box getBox(TileCoord coord) {
         return new Box(getTopLeft(coord),getBottomRight(coord));
    }
}
