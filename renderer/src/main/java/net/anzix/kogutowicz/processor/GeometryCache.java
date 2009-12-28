/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class GeometryCache {

    private Logger logger = Logger.getLogger(GeometryCache.class.getCanonicalName());

    private Map<TileCoord, SortedSet<GeometryElementOnLayer>> geometries = new HashMap();

    private Comparator<GeometryElementOnLayer> comparator = new Comparator<GeometryElementOnLayer>() {

        @Override
        public int compare(GeometryElementOnLayer o1, GeometryElementOnLayer o2) {
            int i = new Integer(o1.getLayer().getWeight()).compareTo(new Integer(o2.getLayer().getWeight()));
            if (i != 0) {
                return i;
            }
            i = new Integer(o1.getElement().getZindex()).compareTo(new Integer(o2.getElement().getZindex()));
            if (i != 0) {
                return i;
            }

            return -1;

        }
    };

    private ProcessMatrix matrix;

    public GeometryCache(ProcessMatrix matrix) {
        this.matrix = matrix;
    }

    public void addGeometry(TileCoord coord, Layer layer, GeometryElement geometry) {
        SortedSet<GeometryElementOnLayer> elements = geometries.get(coord);
        if (elements == null) {
            elements = new TreeSet<GeometryElementOnLayer>(comparator);
            geometries.put(coord, elements);
        }
        elements.add(new GeometryElementOnLayer(geometry, layer));

    }

    public int size() {
        return geometries.size();
    }

    public Collection<GeometryElementOnLayer> getElements(TileCoord coord) {
        return geometries.get(coord) == null ? new ArrayList() : geometries.get(coord);
    }

    public Collection<GeometryElementOnLayer> getGeometries(TileCoord coord) {
        return geometries.get(coord);
    }

}
