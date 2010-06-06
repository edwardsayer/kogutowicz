/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.filter;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 * True filter to composite operations.
 * @author elek
 */
public class TrueFilter implements Filter {

    @Override
    public boolean is(Element element, Zoom zoom) {
        return true;
    }
}
