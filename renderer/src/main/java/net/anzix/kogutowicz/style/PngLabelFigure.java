/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Icon;

/**
 *
 * @author elek
 */
public class PngLabelFigure extends Figure {

    public PngLabelFigure() {
        setType(Way.class);
    }

    private String convertSignToPngName(String signName) {
        return signName.replaceAll("\\+", "plus").toLowerCase();
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Way) {
            Node point = ((Way) element).getHalfPosition();
            Icon i = new Icon(convertNodeToPoint(point));
            String signTag = element.getTagValue("tourism");
            String[] signs = signTag.trim().split(" ");
            for (String sign : signs) {
                i.addSource(convertSignToPngName(sign));
            }
            i.setZindex(10);
            elements.add(i);
            return elements;

//            Line l = new Line(getStyle());
//            for (Node n : ((Way) element).getNodes()) {
//                l.addPoint(space.convertNodeToPoint(n));
//            }
//
//            elements.add(l);
//            return elements;
        } else {
            throw new IllegalArgumentException("PngLabel can draw only ways");
        }
    }

    public void setStroke(float f) {
        
    }

    public void setPattern(float[] f) {

    }

    @Override
    public void init(String... parameters) {
        
    }
}
