package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordPair;

/**
 *
 * @author elek
 */
public interface Transformation {

    public CoordPair transform(CoordPair pair);
}
