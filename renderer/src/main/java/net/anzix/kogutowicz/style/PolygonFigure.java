/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Area;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Polygon;

/**
 *
 * @author elek
 */
public class PolygonFigure extends Figure {

    private FigureStyle<PolygonStyle> style = new FigureStyle<PolygonStyle>();

    public PolygonFigure() {
        setColor(Color.BLACK);
        setStroke(1f);
        setType(Area.class);
    }

    public PolygonFigure(Color color) {
        setColor(color);
        setStroke(1f);
        setType(Area.class);
    }

    public PolygonFigure(int color) {
        setColor(new Color(color));
        setStroke(1f);
        setType(Area.class);
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Way) {
            Polygon p = new Polygon(convertStyle(style, zoom));
            for (Node n : ((Way) element).getNodes()) {
                p.addPoint(convertNodeToPoint(n));
            }
            p.setZindex(getZindex());
            elements.add(p);
            return elements;

        } else {
            throw new IllegalArgumentException("PolygonFigure can draw only relations");
        }
    }

    public FigureStyle<PolygonStyle> getStyle() {
        return style;
    }

    public void setColor(Color color) {
        style.addStyle(PolygonStyle.COLOR, new ConstantStyleValue<Color>(color));
    }

    public void setColor(String color) {
        style.addStyle(PolygonStyle.COLOR, new ConstantStyleValue<Color>(new Color(color)));
    }

    public Color getColor(Zoom zoom) {
        return (Color) ((StyleValue) style.getStyles().get(PolygonStyle.COLOR)).getValue(zoom);
    }

    public Float getStroke(Zoom zoom) {
        return (Float) ((StyleValue) style.getStyles().get(PolygonStyle.WIDTH)).getValue(zoom);
    }

    public PolygonFigure setStroke(Float stroke) {
        style.addStyle(PolygonStyle.WIDTH, new ConstantStyleValue<Float>(stroke));
        return this;
    }

    @Override
    public void init(String... parameters) {
        if (parameters.length > 0) {
            String color = parameters[0];
            if (!color.contains(":")) {
                setColor(new Color(color));
            }
        }
        if (parameters.length > 1) {
            if (!parameters[1].contains(":")) {
                setStroke(Float.valueOf(parameters[1]));
            }
        }
    }
}


