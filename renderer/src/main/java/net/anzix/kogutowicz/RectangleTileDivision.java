/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;

/**
 *
 * @author elek
 */
public class RectangleTileDivision implements TileDivision {

    private Node tl = new Node(18, 47);

    private int xSize = 10;

    private int ySize = 5;

    private Zoom zoom = Zoom.zoom(14);

    private double width;

    private double height;

    public RectangleTileDivision(Node tl, Node br, int xSize, int ySize) {
        this.tl = tl;
        this.xSize = xSize;
        this.ySize = ySize;
        width = (br.getLongitude() - tl.getLongitude()) / xSize;
        height = (tl.getLatitude() - br.getLatitude()) / ySize;
    }

    @Override
    public TileCoord getTileCoord(Node node) {
        int x = (int) Math.floor((node.getLongitude() - tl.getLongitude()) / width);
        int y = (int) Math.floor((tl.getLatitude() - node.getLatitude()) / height);
        return new TileCoord(x, y);
    }

    @Override
    public Node getTopLeft(TileCoord coord) {
        return new Node(tl.getLongitude() + coord.getX() * width, tl.getLatitude() - height * coord.getY());
    }

    @Override
    public Node getBottomRight(TileCoord coord) {
        return getTopLeft(new TileCoord(coord.getX() + 1, coord.getY() + 1));
    }

    @Override
    public Zoom getZoom() {
        return zoom;
    }

    @Override
    public TileCoord getTileSize() {
        return new TileCoord(xSize, ySize);
    }

    @Override
    public Box getBox(TileCoord coord) {
          return new Box(getTopLeft(coord),getBottomRight(coord));
    }
}
