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
public class NotEqualFilter implements Filter {

    private String key;

    private String value;

    public NotEqualFilter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        String value = element.getTagValue(key);
        return value == null || !value.equals(key);
    }

    @Override
    public String toString() {
        return key +" <> "+value;
    }

}
