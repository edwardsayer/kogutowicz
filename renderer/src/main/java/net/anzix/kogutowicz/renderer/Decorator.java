/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordBox;

/**
 * Decorate rendering process with additional elements (eg. page numbers).
 * @author elek
 */
public interface Decorator {

    public Transformation decorateTransformation();

    public CoordBox decorateClip();

    public void renderBefore(Renderer renderer);

    public void renderAfter(Renderer renderer);
}
