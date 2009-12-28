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
public class RenderableListener implements TileStateListener {

    @Override
    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState) {
        if (newState != TileState.PROCESSED) {
            return;
        }
        if (coord.getX() > 0 && coord.getY() > 0) {
            matrix.setTileState(new TileCoord(coord.getX() - 1, coord.getY() - 1), TileState.RENDERABLE);
        }
        if (coord.getX() == matrix.getTileSize().getX() - 1 && coord.getY() > 0) {
            matrix.setTileState(new TileCoord(coord.getX(), coord.getY() - 1), TileState.RENDERABLE);
        }
//        if (position.getX() == matrix.getTileSize().getX() - 1 && position.getY() == 1) {
//            matrix.setTileState(position.getX(), position.getY(), TileState.RENDERABLE);
//        }
    }

    @Override
    public void init(ProcessMatrix matrix) {
    }

    @Override
    public void release(ProcessMatrix matrix) {
        TileCoord size = matrix.getTileSize();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                if (matrix.getTileState(x, y) == TileState.PROCESSED) {
                    matrix.setTileState(new TileCoord(x, y), TileState.RENDERABLE);
                }
            }
        }
    }
}
