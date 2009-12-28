/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class SystemOutputRenderer implements Renderer {

    @Override
    public void renderGeometry(Layer layer, GeometryElement element, Transformation t, CoordBox clip) {
        System.out.println("Layer:" + layer + "Render line: " + element.getClass() + " widh style ");
        if (element instanceof Line) {
            for (Point p : ((Line) element).getPoints()) {
                System.out.println(p.transform(t));
            }
        }
    }

    @Override
    public void release() {
        System.out.println("release");
    }

    @Override
    public void initSpace(double width, double height) {
        System.out.println("init " + width + " " + height);
    }
}
