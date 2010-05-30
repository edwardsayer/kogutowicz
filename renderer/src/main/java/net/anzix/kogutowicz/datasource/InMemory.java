/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;

/**
 * In memory datasource for testing.
 * @author elek
 */
public class InMemory extends AbstractDatasource {

    @Deprecated
    public void add(TileCoord coord, Element element) {
        if (element instanceof Way) addWay((Way) element);
        else if (element instanceof Node) addNode((Node)element);
    }

    public void addWay(Way w) {
        indexWay(w);
    }

    public void addNode(Node n) {
        indexNode(n);
    }

    @Override
    public void init(TileDivision division, Projection targetProjection) {
    }


   
}
