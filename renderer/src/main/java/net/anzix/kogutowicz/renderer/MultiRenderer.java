/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Style;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class MultiRenderer{}
//implements Renderer {
//
//    private List<Renderer> renderers = new ArrayList();
//
//    public MultiRenderer addRenderer(Renderer renderer) {
//        renderers.add(renderer);
//        return this;
//
//    }
//
//    @Override
//    public void initSpace(long startLat, long stopLat, long startLang, long stopLang, double aspect) {
//        for (Renderer renderer : renderers) {
//            renderer.initSpace(startLat, stopLat, startLang, stopLang, aspect);
//        }
//
//    }
//
//    @Override
//    public void renderGeometry(Layer layer, GeometryElement element, Transformation t) {
//        for (Renderer renderer : renderers) {
//            renderer.renderGeometry(layer, element, t);
//        }
//    }
//
//    @Override
//    public void release() {
//        for (Renderer renderer : renderers) {
//            renderer.release();
//        }
//    }
//}
