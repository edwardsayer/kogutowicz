/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;


import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class SystemOutputRenderer extends AbstractRenderer {

    @Override
    public void renderGeometry(Layer layer, GeometryElement element) {
        System.out.println("Layer:" + layer + "Render line: " + element.getClass() + " widh style ");
        if (element instanceof Line) {
            for (Point p : ((Line) element).getPoints()) {
                System.out.println(p.transform(getCurrentTransformation()));
            }
        }
    }

    @Override
    public void release() {
        System.out.println("release");
    }

    @Override
    public void initSpace(Size size) {
        System.out.println("init " + size.getWidth() + " " + size.getHeight());
    }

    @Override
    public void setClip(CoordBox clip) {
        System.out.println("set clip");
    }

    
}
