/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elek
 */
public class Style<E> {

    private Map<E, Object> styles = new HashMap();

    public <T extends Object> void addStyle(E key, T style) {
        styles.put(key, style);
    }

    public <T> T getStyle(E key, Class<T> type) {
        return (T) styles.get(key);
    }
}
