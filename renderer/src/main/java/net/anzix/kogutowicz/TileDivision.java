/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;

/**
 * Split a map to working units (smaller rectangles).
 * 
 * @author elek
 */
public interface TileDivision {

    public TileCoord getTileCoord(Node node);

    public Node getTopLeft(TileCoord coord);

    public Node getBottomRight(TileCoord coord);

    public Zoom getZoom();

    public TileCoord getTileSize();

    public Box getBox(TileCoord coord);
}
