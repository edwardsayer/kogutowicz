/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.CoordPair;

/**
 *
 * @author elek
 */
public class BaseTransformation implements Transformation {

    double basex;

    double basey;

    double width;

    double height;

    double aspect = 1;

    CoordBox box;

    public BaseTransformation(CoordBox box, double width, double height) {
        basex = box.getTopLeft().getX();
        basey = box.getBottomRight().getY();
        this.width = width;
        this.height = height;
        this.box = box;
        double aspectx = width / Math.abs(box.getTopLeft().getX() - box.getBottomRight().getX());
        double aspecty = height / Math.abs(box.getTopLeft().getY() - box.getBottomRight().getY());
        aspect = aspectx > aspecty ? aspectx : aspecty;
        //aspect = aspecty;

    }

    @Override
    public CoordPair transform(CoordPair pair) {
        return new CoordPair((pair.getX() - basex) * aspect, height - (pair.getY() - basey) * aspect);
    }

    public double getAspect() {
        return aspect;
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
    }

    
}
