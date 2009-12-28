/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class ProcessMatrix {

    private Logger logger = Logger.getLogger(ProcessMatrix.class.getCanonicalName());

    private TileCoord tileSize;
    //private Map<Integer, Map<Integer, TileRectangle>> tiles = new HashMap();

    private Zoom zoom;

    private Map<TileCoord, TileState> tiles = new HashMap();

    private List<TileStateListener> listeners = new ArrayList();

    private int tileWidth = 256;

    private GeometryCache geometries;

    private Box boundary;

    private Projection projecion = new Mercator();

    private TileDivision division;

    /**
     * Width of one tile.
     */
    private double width;

    /**
     * Width of one tile.
     */
    private double height;

    private TileCoord tileStart;

    private TileCoord tileEnd;

    public ProcessMatrix(Box box, Zoom zoom) {
        this(box.getTopLeft(), box.getBottomRight(), new OSMTileDivision(zoom), 1, 1);
    }

    public ProcessMatrix(Box box, TileDivision div, int noXTile, int noYTile) {
        this(box.getTopLeft(), box.getBottomRight(), div, noXTile, noYTile);
    }

    public ProcessMatrix(Box box, Zoom zoom, int noXTile, int noYTile) {
        this(box.getTopLeft(), box.getBottomRight(), new OSMTileDivision(zoom), noXTile, noYTile);
    }

    public ProcessMatrix(Node topLeft, Node bottomRight, TileDivision division, int noXTile, int noYTile) {
        tileSize = new TileCoord(noXTile, noYTile);
        this.zoom = division.getZoom();
        this.boundary = new Box(topLeft, bottomRight);
        height = (topLeft.getLatitude() - bottomRight.getLatitude()) / noYTile;
        width = (bottomRight.getLongitude() - topLeft.getLongitude()) / noXTile;
//        for (int x = 0; x < noXTile; x++) {
//            for (int y = 0; y < noYTile; y++) {
//                Node tl = new Node(topLeft.getLongitude() + x * width, topLeft.getLatitude() - y * height);
//                Node br = new Node(topLeft.getLongitude() + (x + 1) * width, topLeft.getLatitude() - (y + 1) * height);
//                setTileRectangle(x, y, new TileRectangle(tl, br, zoom));
//            }
//        }
        listeners.add(new RenderableListener());
        listeners.add(new ReleaseResourceListener());
        listeners.add(new LabelDensityCorrectorListener());
        this.geometries = new GeometryCache(this);

        this.division = division;
        tileStart = division.getTileCoord(topLeft);
        tileEnd = division.getTileCoord(bottomRight);
    }

    public void addListener(TileStateListener listener) {
        listeners.add(listener);
    }

    public TileCoord getTileSize() {
        return tileSize;
    }

    public void setTileSize(TileCoord tileSize) {
        this.tileSize = tileSize;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public Zoom getZoom() {
        return zoom;
    }

    public void setZoom(Zoom zoom) {
        this.zoom = zoom;
    }

    public TileState getTileState(int x, int y) {
        return tiles.get(new TileCoord(x, y));
    }

    public void setTileState(TileCoord c, TileState state) {
        logger.log(Level.FINE, "Changing tile state (" + c.getX() + "," + c.getY() + "): " + state);
        tiles.put(c, state);
        fireStateChange(c, state);
    }

    protected void fireStateChange(TileCoord coord, TileState newState) {
        for (TileStateListener listener : listeners) {
            listener.onStateChange(this, coord, newState);
        }
    }

//    public void setTileRectangle(int x, int y, TileRectangle tile) {
//        Map<Integer, TileRectangle> column = tiles.get(x);
//        if (column == null) {
//            column = new HashMap<Integer, TileRectangle>();
//            tiles.put(x, column);
//        }
//        column.put(y, tile);
//    }
//
//    public TileRectangle getTileRectangle(int x, int y) {
//
//    }
//
//    public TilePosition getTilePosition(TileRectangle rectangle) {
//        for (Integer x : tiles.keySet()) {
//            for (Integer y : tiles.get(x).keySet()) {
//                if (tiles.get(x).get(y) == rectangle) {
//                    return new TilePosition(x, y);
//                }
//            }
//        }
//        return null;
//    }
    public void addGeometry(TileCoord coord, Layer layer, GeometryElement element) {
        geometries.addGeometry(coord, layer, element);
    }

    public void addGeometries(TileCoord coord, Layer layer, List<GeometryElement> elements) {
        for (GeometryElement element : elements) {
            geometries.addGeometry(coord, layer, element);
        }
    }

    public GeometryCache getGeometries() {
        return geometries;
    }

    public Projection getProjecion() {
        return projecion;
    }

//    public Box getBoundingBox(int x, int y) {
//        return getTileRectangle(x, y).getBox();
//    }
    public void releaseListeners() {
        for (TileStateListener listener : listeners) {
            listener.release(this);
        }
    }

    public void initListeners() {
        for (TileStateListener listener : listeners) {
            listener.init(this);
        }
    }

    /**
     *
     * @return
     * @todo
     */
    public double getProjectedWidth() {
        return 0;
        //return projecion.getX(getBoundary().getBottomRight().getLongitude()) - projecion.getX(getBoundary().getTopLeft().getLongitude());
    }

    /**
     *
     * @return
     * @todo
     */
    public double getProjectedHeight() {
        return 0;
        //return projecion.getY(getBoundary().getTopLeft().getLatitude()) - projecion.getY(getBoundary().getBottomRight().getLatitude());
    }

//    public Collection<TileRectangle> getRectangles(CoordBox boundingBox) {
//        double[] tl = projecion.getLanLon(boundingBox.getTopLeft().getX(), boundingBox.getTopLeft().getY());
//        double[] br = projecion.getLanLon(boundingBox.getBottomRight().getX(), boundingBox.getBottomRight().getY());
//        TileCoord start = (division.getTileCoord(new Node(tl[0], tl[1]))).substract(tileStart);
//
//        TileCoord stop = division.getTileCoord(new Node(br[0], br[1])).substract(tileStart);
//        List<TileRectangle> result = new ArrayList();
//        for (int x = start.getX(); x <= stop.getX(); x++) {
//            for (int y = start.getY(); y <= stop.getY(); y++) {
//                if (x >= 0 && y >= 0 && tiles.get(new Integer(x)) != null && tiles.get(new Integer(x)).get(new Integer(y)) != null) {
//                    result.add(getTileRectangle(new Integer(x), new Integer(y)));
//                }
//            }
//        }
//        return result;
//    }
    public static class TilePosition {

        private int x;

        private int y;

        public TilePosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TilePosition other = (TilePosition) obj;
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
            int hash = 7;
            hash = 23 * hash + this.x;
            return hash;
        }
    }

    public Box getBoundary() {
        return boundary;
    }

    public void setProjecion(Projection projecion) {
        this.projecion = projecion;
    }

    public TileCoord getTileEnd() {
        return tileEnd;
    }

    public void setTileEnd(TileCoord tileEnd) {
        this.tileEnd = tileEnd;
    }

    public TileCoord getTileStart() {
        return tileStart;
    }

    public void setTileStart(TileCoord tileStart) {
        this.tileStart = tileStart;
    }

    public TileDivision getDivision() {
        return division;
    }

    public void setDivision(TileDivision division) {
        this.division = division;
    }
    public Box getBox(TileCoord coord){
        return getDivision().getBox(coord);
    }
}
