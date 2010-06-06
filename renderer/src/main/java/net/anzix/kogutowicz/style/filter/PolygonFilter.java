/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.filter;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Way;

/**
 * Filter closed ways.
 * @author elek
 */
public class PolygonFilter implements Filter {

    @Override
    public boolean is(Element element, Zoom zoom) {
        if (element instanceof Way) {
            Way w = (Way) element;
            return ((w.getNodes().size() > 1) &&
                    w.getNodes().get(0).equals(w.getNodes().get(w.getNodes().size() - 1)));
        } else {
            return false;
        }
    }
}
