/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
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

    private String icon;

    public PngLabelFigure() {
        setType(Node.class);
    }

    private String convertSignToPngName(String signName) {
        return signName.replaceAll("\\+", "plus").toLowerCase();
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Node) {
            Icon i = new Icon(convertNodeToPoint((Node) element));
            i.addSource(icon);
            elements.add(i);
            //PngIcon i = new Icon();
//            Node point = ((Way) element).getHalfPosition();
//            Icon i = new Icon(convertNodeToPoint(point));
//            String signTag = element.getTagValue("tourism");
//            String[] signs = signTag.trim().split(" ");
//            for (String sign : signs) {
//                i.addSource(convertSignToPngName(sign));
//            }
//            i.setZindex(10);
//            elements.add(i);
//            return elements;

//            Line l = new Line(getStyle());
//            for (Node n : ((Way) element).getNodes()) {
//                l.addPoint(space.convertNodeToPoint(n));
//            }
//
//            elements.add(l);
//            return elements;
            return elements;
        } else {
            throw new IllegalArgumentException("PngLabel can draw only nodes");
        }
    }

    public void setStroke(float f) {
    }

    public void setPattern(float[] f) {
    }

    @Override
    public Figure init(String... parameters) {
        if (parameters.length > 0) {
            icon = parameters[0];
        }
        return this;

    }
}
