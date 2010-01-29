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
 * Composite OR filter.
 *
 * @author elek
 */
public class OrFilter implements Filter {

    private List<Filter> filters = new ArrayList();

    public OrFilter(Filter... fs) {
        for (Filter f : fs) {
            filters.add(f);
        }
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        for (Filter filter : filters) {
            if (filter.is(element, zoom)) {
                return true;
            }
        }

        return false;
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        String sep = "";
        for (Filter f : filters) {
            b.append(sep);
            b.append(f.toString());
            sep = " OR ";

        }
        return b.toString();
    }
}
