package net.anzix.kogutowicz.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;

/**
 * Base class to inmemory datasources.
 * @author elek
 */
public abstract class AbstractDatasource implements Datasource {

    protected Map<TileCoord, Collection<Element>> index = new HashMap();

    protected TileDivision division;

    private void putIndex(TileCoord coord, Element e) {
        Collection<Element> elem = index.get(coord);
        if (elem == null) {
            elem = new ArrayList<Element>();
            index.put(coord, elem);
        }
        elem.add(e);
    }

    protected void indexNode(net.anzix.kogutowicz.element.Node node) {
        TileCoord coord = division.getTileCoord(node);
        int x = coord.getX();
        int y = coord.getY();
        putIndex(coord, node);

        putIndex(new TileCoord(x - 1, y), node);
        putIndex(new TileCoord(x + 1, y), node);
        putIndex(new TileCoord(x, y + 1), node);
        putIndex(new TileCoord(x, y - 1), node);

        putIndex(new TileCoord(x - 1, y - 1), node);
        putIndex(new TileCoord(x + 1, y - 1), node);
        putIndex(new TileCoord(x + 1, y + 1), node);
        putIndex(new TileCoord(x - 1, y + 1), node);



    }

    protected void indexWay(net.anzix.kogutowicz.element.Way w) {
        TileCoord min = null;
        TileCoord max = null;
        for (net.anzix.kogutowicz.element.Node n : w.getNodes()) {
            TileCoord curr = division.getTileCoord(n);
            if (min == null) {
                min = new TileCoord(curr);
                max = new TileCoord(curr);
            } else {
                if (min.getX() > curr.getX()) {
                    min.setX(curr.getX());
                }
                if (min.getY() > curr.getY()) {
                    min.setY(curr.getY());
                }
                if (max.getX() < curr.getX()) {
                    max.setX(curr.getX());
                }
                if (max.getY() < curr.getY()) {
                    max.setY(curr.getY());
                }
            }
        }
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                putIndex(new TileCoord(x, y), w);
            }
        }

    }

    @Override
    public Collection<Element> getElements(TileCoord c) {
        return index.get(c) == null ? new ArrayList<Element>() : index.get(c);
    }

    public void setDivision(TileDivision division) {
        this.division = division;
    }

    @Override
    public void reset() {
        index.clear();
    }
}
