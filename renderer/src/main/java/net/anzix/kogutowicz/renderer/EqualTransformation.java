/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordPair;

/**
 * The most basic transformation.
 * @author elek
 */
public class EqualTransformation implements Transformation {

    @Override
    public CoordPair transform(CoordPair pair) {
        return pair;
    }
}
