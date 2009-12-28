/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import net.anzix.kogutowicz.TileCoord;

/**
 *
 * @author elek
 */
public interface TileStateListener {

    public void init(ProcessMatrix matrix);

    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState);

    public void release(ProcessMatrix matrix);
}
