/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordPair;

/**
 * Add fix values to a coordinate.
 * 
 * @author elek
 */
public class OffsetTransformation implements Transformation {

    private double offsety = 0;

    private double offsetx = 0;

    public OffsetTransformation(double offsety, double offsetx) {
        this.offsety = offsety;
        this.offsetx = offsetx;
    }

    @Override
    public CoordPair transform(CoordPair pair) {
        return new CoordPair(pair.getX() + offsetx, pair.getY() + offsety);
    }
}
