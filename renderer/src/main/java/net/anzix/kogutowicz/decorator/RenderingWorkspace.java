/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.decorator;

import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.renderer.CompositeTransformation;
import net.anzix.kogutowicz.renderer.OffsetTransformation;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.renderer.Transformation;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class RenderingWorkspace implements Renderer {

    private Size originSize;

    private Renderer renderer;

    private Point origin = new Point(0, 0);

    private Point size;

    public void init() {
        renderer.initSpace(originSize);
    }

    public void release() {
        renderer.release();
    }

    public void setTransformation(Transformation trafo) {
        renderer.setTransformation(new CompositeTransformation(trafo, new OffsetTransformation(origin.getX(), origin.getY())));
    }

    public void renderGeometry(Layer layer, GeometryElement element) {
        renderer.renderGeometry(layer, element);
    }

    public RenderingWorkspace(Size size, Renderer renderer) {
        this.size = new Point(size.getWidth(), size.getHeight());
        this.originSize = size;
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public void setClip(CoordBox coordBox) {
        renderer.setClip(coordBox);
    }

    @Override
    public void initSpace(Size size) {
        renderer.initSpace(size);
    }
}
