/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.filter;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 * <> filter.
 * 
 * @author elek
 */
public class NotEqualFilter implements Filter {

    private String key;

    private String value;

    public NotEqualFilter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        String current = element.getTagValue(key);
        if (value.equals("#EMPTY#")) {
            return current != null && current.length() > 0;
        }
        return current == null || !current.equals(key);
    }

    @Override
    public String toString() {
        return key + " <> " + value;
    }
}
