/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class BufferedRenderer {}
//implements Renderer {
//
//    private Map<Layer, List<GeometryElement>> elements = new HashMap();
//
//    private Collection<Layer> layerOrder;
//
//    private Renderer renderer;
//
//    public BufferedRenderer(Renderer renderer, Collection<Layer> layerOrder) {
//        this.renderer = renderer;
//        this.layerOrder = layerOrder;
//    }
//
//    @Override
//    public void initSpace(long startLat, long stopLat, long startLang, long stopLang, double aspect) {
//        renderer.initSpace(startLat, stopLat, startLang, stopLang, aspect);
//    }
//
//    @Override
//    public void renderGeometry(Layer layer, GeometryElement element, Transformation t) {
//        List<GeometryElement> layerElements = elements.get(layer);
//        if (layerElements == null) {
//            elements.put(layer, new ArrayList());
//            layerElements = elements.get(layer);
//        }
//        layerElements.add(element);
//    }
//
//    @Override
//    public void release() {
//        for (Layer l : elements.keySet()) {
//            List<GeometryElement> layerElements = elements.get(l);
//            Collections.sort(layerElements, new Comparator<GeometryElement>() {
//
//                @Override
//                public int compare(GeometryElement o1, GeometryElement o2) {
//                    return new Integer(o1.getZindex()).compareTo(new Integer(o2.getZindex()));
//                }
//            });
//        }
//        prerenderHooks();
//        for (Layer l : layerOrder) {
//            if (elements.containsKey(l)) {
//                for (GeometryElement e : elements.get(l)) {
//                    renderer.renderGeometry(l, e,new BaseTransformation());
//
//
//                }
//            }
//        }
//        renderer.release();
//        elements = new HashMap();
//    }
//
//    private void prerenderHooks() {
//       new RadiusAction().action(elements,layerOrder);
//    }
//}
