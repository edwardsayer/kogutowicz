/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

/**
 *
 * @author elek
 */
public abstract class GeometryElement {

    private int zindex = 0;

    public GeometryElement() {
    }

    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    public abstract CoordBox getBoundingBox();
}
