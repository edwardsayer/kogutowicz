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
public class ConstantStyleValue<T> implements StyleValue<T> {

    private T value;

    public ConstantStyleValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue(Zoom zoom) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
