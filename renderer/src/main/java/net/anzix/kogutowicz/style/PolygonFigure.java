/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Area;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Polygon;

/**
 * Draw a polygon to the map.
 *
 * @author elek
 */
public class PolygonFigure extends Figure {

    private StyleValue<Color> color = new ConstantStyleValue<Color>(Color.BLACK);

    private StyleValue<Float> width  = new ConstantStyleValue<Float>(1f);

    public PolygonFigure() {
        setType(Area.class);
    }

    public PolygonFigure(Color color) {
        setColor(color);
        setType(Area.class);
    }

    public PolygonFigure(int color) {
        setColor(new Color(color));
        setType(Area.class);
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Way) {
            Polygon p = new Polygon();
            if (color != null) {
                p.setColor(color.getValue(zoom));
            }
            if (width != null) {
                p.setWidth(width.getValue(zoom));
            }
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

    public void setColor(Color color) {
        this.color = new ConstantStyleValue<Color>(color);
    }

    public void setColor(String color) {
        this.color = new ConstantStyleValue<Color>(new Color(color));
    }

    public Color getColor(Zoom zoom) {
        return color.getValue(zoom);
    }

    public Float getStroke(Zoom zoom) {
        return width.getValue(zoom);
    }

    public PolygonFigure setStroke(Float stroke) {
        this.width = new ConstantStyleValue<Float>(stroke);
        return this;
    }

    @Override
    public Figure init(String... parameters) {
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
        return this;
    }

    public StyleValue<Color> getColor() {
        return color;
    }

    public StyleValue<Float> getWidth() {
        return width;
    }

    
}


