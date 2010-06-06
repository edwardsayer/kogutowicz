package net.anzix.kogutowicz.geometry;

/**
 * Dump drawable geometric shape.
 * 
 * @author elek
 */
public abstract class GeometryElement {

    private boolean hidden = false;

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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    
}
