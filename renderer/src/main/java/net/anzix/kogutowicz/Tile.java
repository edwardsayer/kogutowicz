package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Node;

/**
 * Object represents a tile.
 *
 * Tile is the subpart of tile division. It you use OSMTileDivision then a tile
 * object represent a slipy map tile
 *
 * @see http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
 * @author elek
 */
public class Tile {

    private TileCoord coord;

    private Projection projection;

    private TileDivision type;

    public Tile(int xtile, int ytile, TileDivision type) {
        coord = new TileCoord(xtile, ytile);
        this.type = type;
    }

    public Node getTopLeftNode() {
        return type.getTopLeft(coord);
    }

    @Override
    public String toString() {
        return "(" + coord.getX() + "," + coord.getY() + ":" + type.getZoom() + ")";
    }

    public int getXtile() {
        return coord.getX();
    }

    public int getYtile() {
        return coord.getY();
    }

    public int getZoom() {
        return type.getZoom().getLevel();
    }
}
