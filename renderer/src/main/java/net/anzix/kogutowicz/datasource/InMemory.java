/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.anzix.kogutowicz.EqualProjection;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;

/**
 * In memory datasource for testing.
 * @author elek
 */
public class InMemory implements Datasource {

    private Projection projection = new EqualProjection();

    private Map<TileCoord, List<Element>> elementMap = new HashMap();

    public void add(TileCoord coord, Element element) {
        List<Element> elements = elementMap.get(coord);
        if (elements == null) {
            elements = new ArrayList();
            elementMap.put(coord, elements);
        }
        elements.add(element);
    }

    @Override
    public void init(TileDivision division, Projection targetProjection) {
    }

    @Override
    public void reset() {
        elementMap.clear();
    }

    @Override
    public Collection<Element> getElements(TileCoord coord) {
        List<Element> es = elementMap.get(coord);
        return es == null ? new ArrayList<Element>() : es;
    }
}
