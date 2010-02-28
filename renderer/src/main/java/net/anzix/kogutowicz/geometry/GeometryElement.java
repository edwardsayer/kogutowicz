/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

/**
 *
 * @author elek
 */
public abstract class GeometryElement<T extends Style> {

    private int zindex = 0;

    protected T style;

    public GeometryElement() {
    }

    public GeometryElement(T style) {
        this.style = style;
    }

    public T getStyle() {
        return style;
    }
   
    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    public abstract CoordBox getBoundingBox();
}
