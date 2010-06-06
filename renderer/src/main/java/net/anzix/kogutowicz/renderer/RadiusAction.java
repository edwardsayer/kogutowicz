/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Icon;
import net.anzix.kogutowicz.style.Layer;

/**
 * Deprecated.
 * 
 * @author elek
 */
@Deprecated
public class RadiusAction {

    private Map<Double, Icon> xToElement = new HashMap();

    public void action(Map<Layer, List<GeometryElement>> elements, Collection<Layer> layerOrder) {
        for (Layer l : layerOrder) {
            if (elements.containsKey(l)) {
                for (Iterator<GeometryElement> it = elements.get(l).iterator(); it.hasNext();) {
                    GeometryElement e = it.next();
                    if (e instanceof Icon) {
                        Icon i = (Icon) e;
                        Icon ic = findPointNearTo(i.getX(), i.getY());
                        if (ic != null) {
                            it.remove();
                        } else {
                            xToElement.put(i.getX(), i);
                        }
                    }
                }
            }
        }
    }

    private Icon findPointNearTo(double x, double y) {
        for (Double xe : xToElement.keySet()) {
            if (Math.abs(x - xe) < 100) {
                if (Math.abs(xToElement.get(xe).getY() - y) < 100) {
                    return xToElement.get(xe);
                }
            }
        }
        return null;
    }
}
