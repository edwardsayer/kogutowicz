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
public class EndZoomFilter implements Filter {

    private int level;

    public EndZoomFilter(int level) {
        this.level = level;
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        return zoom.getLevel() <= level;
    }
}
