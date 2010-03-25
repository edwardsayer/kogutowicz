/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import net.anzix.kogutowicz.Zoom;

/**
 *
 * @author elek
 */
public interface StyleValue<T> {

    public T getValue(Zoom zoom);
}
