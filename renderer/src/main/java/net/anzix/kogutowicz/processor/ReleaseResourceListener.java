/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.element.Box;

/**
 *
 * @author elek
 */
public class ReleaseResourceListener implements TileStateListener {

    private Logger logger = Logger.getLogger(ReleaseResourceListener.class.getCanonicalName());

    @Override
    public void init(ProcessMatrix matrix) {
    }

    @Override
    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState) {
        if (newState != TileState.RENDERED) {
            return;
        }
        logger.log(Level.FINE, "GC: " + matrix.getGeometries().getElements(coord).size());
        //matrix.getGeometries().getGeometries().remove(coord);


    }

    @Override
    public void release(ProcessMatrix matrix) {
    }
}
