/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.anzix.kogutowicz.TileCoord;

import net.anzix.kogutowicz.geometry.CoordPair;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.geometry.Icon;

/**
 *
 * @author elek
 */
public class LabelDensityCorrectorListener implements TileStateListener {

    @Override
    public void init(ProcessMatrix matrix) {
       
    }

    @Override
    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState) {
        
    }

    @Override
    public void release(ProcessMatrix matrix) {
        
    }

//    private List<CoordPair> renderedLabel = new ArrayList();
//
//    int thereshold;
//
//    @Override
//    public void init(ProcessMatrix matrix) {
//        renderedLabel = new ArrayList();
//        thereshold = (int) Math.round(1000 / Math.pow(2, matrix.getZoom().getLevel() - 13));
//    }
//
//    @Override
//    public void onStateChange(ProcessMatrix matrix, TileRectangle rectangle, TileState newState) {
//        if (newState != TileState.RENDERABLE) {
//            return;
//        }
//
//        GeometryCache cache = matrix.getGeometries();
//        Collection<GeometryElementOnLayer> elements = cache.getElements(rectangle);
//        for (Iterator<GeometryElementOnLayer> it = elements.iterator(); it.hasNext();) {
//            GeometryElementOnLayer element = it.next();
//            if (element.getElement() instanceof Icon) {
//                Icon i = (Icon) element.getElement();
//                if (findPointNearTo(i.getX(), i.getY(), thereshold)) {
//                    it.remove();
//                } else {
//                    renderedLabel.add(new CoordPair(i.getX(), i.getY()));
//                }
//            }
//        }
//    }
//
//    private boolean findPointNearTo(double x, double y, int thereshold) {
//
//        for (CoordPair p : renderedLabel) {
//            if (Math.abs(x - p.getX()) < thereshold) {
//                if (Math.abs(p.getY() - y) < thereshold) {
//                    return true;
//                }
//
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void release(ProcessMatrix matrix) {
//    }
}
