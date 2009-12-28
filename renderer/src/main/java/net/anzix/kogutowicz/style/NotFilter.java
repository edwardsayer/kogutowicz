/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 *
 * @author elek
 */
public class NotFilter implements Filter {

    private Filter filter;

    public NotFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        return !filter.is(element, zoom);
    }
}
