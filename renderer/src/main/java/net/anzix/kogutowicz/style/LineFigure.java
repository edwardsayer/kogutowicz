package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Line;

/**
 * Draw a line based on source map element and a given style.
 * 
 * @author elek
 */
public class LineFigure extends Figure {

    private StyleValue<Color> color = new ConstantStyleValue<Color>(Color.BLACK);

    private StyleValue<Float> width = new ConstantStyleValue<Float>(1f);

    private StyleValue<float[]> pattern = new ConstantStyleValue<float[]>(new float[0]);

    public LineFigure(int color) {
        setColor(new Color(color));
        setType(Way.class);
    }

    public LineFigure(int color, float width) {
        setStroke(width);
        setColor(new Color(color));
        setType(Way.class);
    }

    public LineFigure(int color, float width, float[] pattern) {
        setStroke(width);
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure(int color, String width, float[] pattern) {
        this.width = new ZoomStyleValue(width);
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure(int color, String width) {
        this.width = new ZoomStyleValue(width);
        setColor(new Color(color));
        setType(Way.class);
    }

    public LineFigure(int color, float width, float pattern) {
        setStroke(width);
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure() {
        setType(Way.class);
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Way) {
            Line l = new Line();
            if (width != null) {
                l.setWidth(width.getValue(zoom));
            }
            if (zoom != null) {
                l.setPattern(pattern.getValue(zoom));
            }
            if (color != null) {
                l.setColor(color.getValue(zoom));
            }
            for (Node n : ((Way) element).getNodes()) {
                l.addPoint(convertNodeToPoint(n));
            }
            l.setZindex(getZindex());
            elements.add(l);
            return elements;
        } else {
            return new ArrayList();
            //throw new IllegalArgumentException("LineFigure can draw only ways");
        }
    }

    public LineFigure setStroke(Float stroke) {
        this.width = new ConstantStyleValue<Float>(stroke);
        return this;
    }

    public Float getStroke(Zoom zoom) {
        return width.getValue(zoom);
    }

    public Color getColor(Zoom zoom) {
        return color.getValue(zoom);
    }

    public LineFigure setColor(Color color) {
        this.color = new ConstantStyleValue<Color>(color);
        return this;
    }

    public LineFigure setColor(String color) {
        this.color = new ConstantStyleValue<Color>(new Color(color));
        return this;
    }

    public LineFigure setPattern(float[] f) {
        this.pattern = new ConstantStyleValue<float[]>(f);
        return this;
    }

    public LineFigure setPattern(float f) {
        return setPattern(new float[]{f});
    }

    @Override
    public Figure init(String... parameters) {
        if (parameters.length > 0) {
            String color = parameters[0];
            if (!color.contains(":")) {
                setColor(new Color(color));
            } else {
                throw new IllegalArgumentException("Not implemented");
            }

        }
        if (parameters.length > 1) {
            if (!parameters[1].contains(":")) {
                setStroke(Float.valueOf(parameters[1]));
            } else {
                this.width = new ZoomStyleValue(parameters[1]);
            }
        }
        if (parameters.length > 2) {
            String pattern[] = parameters[2].split(",");
            float[] p = new float[pattern.length];
            for (int i = 0; i < pattern.length; i++) {
                p[i] = Float.parseFloat(pattern[i]);
            }
            this.pattern = new ConstantStyleValue<float[]>(p);

        }
        return this;
    }

    public StyleValue<Color> getColor() {
        return color;
    }

    public StyleValue<float[]> getPattern() {
        return pattern;
    }

    public StyleValue<Float> getWidth() {
        return width;
    }


}
