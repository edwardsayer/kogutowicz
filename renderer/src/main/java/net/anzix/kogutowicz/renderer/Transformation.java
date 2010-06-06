package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordPair;

/**
 * Fix calulation function to a coordnate pairs.
 *
 * @author elek
 */
public interface Transformation {

    public CoordPair transform(CoordPair pair);
}
