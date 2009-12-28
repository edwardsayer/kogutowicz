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
public class OSMTileDivision implements TileDivision {

    private Zoom zoom;

    public OSMTileDivision(Zoom zoom) {
        this.zoom = zoom;
    }

    @Override
    public TileCoord getTileCoord(Node node) {
        int xtile = (int) Math.floor((node.getLongitude() + 180) / 360 * (1 << zoom.getLevel()));
        int ytile = (int) Math.floor((1 - Math.log(Math.tan(node.getLatitude() * Math.PI / 180) + 1 / Math.cos(node.getLatitude() * Math.PI / 180)) / Math.PI) / 2 * (1 << zoom.getLevel()));
        return new TileCoord(xtile, ytile);
    }

    @Override
    public Node getTopLeft(TileCoord coord) {
        double n = Math.scalb(1, zoom.getLevel());
        double lon_deg = coord.getX() / n * 360.0 - 180.0;
        double lat_rad = Math.atan(Math.sinh(Math.PI * (1 - 2 * coord.getY() / n)));
        double lat_deg = lat_rad * 180.0 / Math.PI;
        return new Node(lon_deg, lat_deg);
    }

    @Override
    public Zoom getZoom() {
        return zoom;
    }

    @Override
    public Node getBottomRight(TileCoord coord) {
        return getTopLeft(new TileCoord(coord.getX() + 1, coord.getY() + 1));
    }

    @Override
    public TileCoord getTileSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Box getBox(TileCoord coord) {
        return new Box(getTopLeft(coord),getBottomRight(coord));
    }
}
