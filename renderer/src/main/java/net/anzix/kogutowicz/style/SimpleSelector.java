/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 * Select suitable figures for an osm entity.
 *
 * @author elek
 */
public class SimpleSelector {

    private Cartographer cartographer;

    public SimpleSelector(Cartographer cartographer) {
        this.cartographer = cartographer;
    }

    public List<SelectedFigure> getItem(Element entity, Zoom zoom) {
        List<SelectedFigure> result = new ArrayList();
        for (Layer layer : cartographer.getLayers()) {
            for (Figure f : layer.getFigures()) {
                if ((f.getType() == null || (f.getType().equals(entity.getClass())))
                        && f.getFilter().is(entity, zoom)
                        && f.getStartZoom() <= zoom.getLevel()
                        && f.getEndZoom() >= zoom.getLevel()) {
                    result.add(new SelectedFigure(layer, f));
                }
            }

        }
        return result;
    }
}
