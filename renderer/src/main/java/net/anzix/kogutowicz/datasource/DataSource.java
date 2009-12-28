/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import java.util.Collection;
import java.util.Iterator;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;

/**
 *
 * @author elek
 */
public interface DataSource {

    public void init(TileDivision division);

    public void reset();

    public Collection<Element> getElements(TileCoord coord);
}
