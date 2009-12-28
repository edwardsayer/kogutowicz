/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.style.Layer;

/**
 *
 * @author elek
 */
public class GeometryElementOnLayer {

    private GeometryElement element;

    private Layer layer;

    public GeometryElementOnLayer(GeometryElement element, Layer layer) {
        this.element = element;
        this.layer = layer;
    }

    public GeometryElement getElement() {
        return element;
    }

    public void setElement(GeometryElement element) {
        this.element = element;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeometryElementOnLayer other = (GeometryElementOnLayer) obj;
        if (this.element != other.element && (this.element == null || !this.element.equals(other.element))) {
            return false;
        }
        if (this.layer != other.layer && (this.layer == null || !this.layer.equals(other.layer))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.element != null ? this.element.hashCode() : 0);
        hash = 61 * hash + (this.layer != null ? this.layer.hashCode() : 0);
        return hash;
    }

}
