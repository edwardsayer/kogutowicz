/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public interface Renderer {

    public void initSpace(double width, double height);

    public void renderGeometry(Layer layer, GeometryElement element, Transformation t, CoordBox clip);

    public void release();
}
