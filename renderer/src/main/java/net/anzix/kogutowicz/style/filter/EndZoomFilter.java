/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.filter;

import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 * Filter by the highest zoom level.
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
