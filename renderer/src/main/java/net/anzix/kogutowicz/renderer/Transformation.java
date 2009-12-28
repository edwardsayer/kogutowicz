/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordPair;

/**
 *
 * @author elek
 */
public interface Transformation {

    public CoordPair transform(CoordPair pair);
}
