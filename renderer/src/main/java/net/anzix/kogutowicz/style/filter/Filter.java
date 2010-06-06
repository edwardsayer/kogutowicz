/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.filter;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 * Decision to draw en element at a given zoom level.
 * @author elek
 */
public interface Filter {

    public boolean is(Element element, Zoom zoom);
}
