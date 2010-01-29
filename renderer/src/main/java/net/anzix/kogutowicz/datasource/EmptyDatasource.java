/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import java.util.ArrayList;
import java.util.Collection;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;

/**
 * Default NOOP emtpy datasource.
 *
 * @author elek
 */
public class EmptyDatasource implements Datasource {

    @Override
    public void init(TileDivision division, Projection targetProjection) {
    }

    @Override
    public void reset() {
    }

    @Override
    public Collection<Element> getElements(TileCoord coord) {
        return new ArrayList<Element>();
    }
}
