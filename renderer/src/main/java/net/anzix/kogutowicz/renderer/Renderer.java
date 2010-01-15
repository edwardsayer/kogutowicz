package net.anzix.kogutowicz.renderer;


import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public interface Renderer {

    public void initSpace(Size size);

    public void setTransformation(Transformation trafo);

    public void setClip(CoordBox clip);

    public void renderGeometry(Layer layer, GeometryElement element);

    public void release();
}
