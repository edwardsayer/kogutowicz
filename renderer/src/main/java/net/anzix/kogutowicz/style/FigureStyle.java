/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elek
 */
public class FigureStyle<E> {

    private Map<E, StyleValue> styles = new HashMap();

    public <T extends Object> void addStyle(E key, StyleValue<T> style) {
        styles.put(key, style);
    }

    public <T extends Object> StyleValue<T> getStyle(E key, Class<T> type) {
        return (StyleValue<T>) styles.get(key);
    }

    public Map<E, StyleValue> getStyles() {
        return styles;
    }
}
