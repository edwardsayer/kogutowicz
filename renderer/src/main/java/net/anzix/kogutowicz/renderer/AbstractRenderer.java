/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;


import net.anzix.kogutowicz.Size;

/**
 *
 * @author elek
 */
public abstract class AbstractRenderer implements Renderer {

    private Transformation currentTransformation = new EqualTransformation();


    @Override
    public void initSpace(Size size) {

    }

    @Override
    public void setTransformation(Transformation trafo) {
        this.currentTransformation = trafo;
    }
   
    public Transformation getCurrentTransformation() {
        return currentTransformation;
    }

    public void setCurrentTransformation(Transformation currentTransformation) {
        this.currentTransformation = currentTransformation;
    }
}
