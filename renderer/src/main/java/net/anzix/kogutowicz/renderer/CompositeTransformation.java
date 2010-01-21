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
public class CompositeTransformation implements Transformation {

    private Transformation[] trafos;

    public CompositeTransformation(Transformation... trafos) {
        this.trafos = trafos;
    }

    @Override
    public CoordPair transform(CoordPair pair) {
        CoordPair result = pair;
        for (Transformation traf : trafos) {
            result = traf.transform(result);
        }
        return result;
    }
}
