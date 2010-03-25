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

    private FigureStyle<LineStyle> style = new FigureStyle();

    public LineFigure(int color) {
        setStroke(1f);
        setColor(Color.BLACK);
        setPattern(null);
        setType(Way.class);
    }

    public LineFigure(int color, float width) {
        setStroke(width);
        setColor(new Color(color));
        setPattern(null);
        setType(Way.class);
    }

    public LineFigure(int color, float width, float[] pattern) {
        setStroke(width);
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure(int color, String width, float[] pattern) {
        style.addStyle(LineStyle.WIDTH, new ZoomStyleValue(width));
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure(int color, String width) {
        style.addStyle(LineStyle.WIDTH, new ZoomStyleValue(width));
        setColor(new Color(color));
        setPattern(null);
        setType(Way.class);
    }

    public LineFigure(int color, float width, float pattern) {
        setStroke(width);
        setColor(new Color(color));
        setPattern(pattern);
        setType(Way.class);
    }

    public LineFigure() {
        setStroke(1f);
        setColor(Color.BLACK);
        setPattern(null);
        setType(Way.class);
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> elements = new ArrayList();
        if (element instanceof Way) {
            Line l = new Line(convertStyle(style, zoom));
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
        style.addStyle(LineStyle.WIDTH, new ConstantStyleValue<Float>(stroke));
        return this;
    }

    public Float getStroke(Zoom zoom) {
        return (Float) style.getStyles().get(LineStyle.WIDTH).getValue(zoom);
    }

    public Color getColor(Zoom zoom) {
        return (Color) style.getStyles().get(LineStyle.COLOR).getValue(zoom);
    }

    public LineFigure setColor(Color color) {
        style.addStyle(LineStyle.COLOR, new ConstantStyleValue<Color>(color));
        return this;
    }

    public LineFigure setColor(String color) {
        style.addStyle(LineStyle.COLOR, new ConstantStyleValue<Color>(new Color(color)));
        return this;
    }

    public LineFigure setPattern(float[] f) {
        style.addStyle(LineStyle.PATTERN, new ConstantStyleValue<float[]>(f));
        return this;
    }

    public LineFigure setPattern(float f) {
        return setPattern(new float[]{f});
    }

    @Override
    public void init(String... parameters) {
        if (parameters.length > 0) {
            String color = parameters[0];
            if (!color.contains(":")) {
                setColor(new Color(color));
            } else {
                style.addStyle(LineStyle.COLOR, new ZoomStyleValue(color));
            }

        }
        if (parameters.length > 1) {
            if (!parameters[1].contains(":")) {
                setStroke(Float.valueOf(parameters[1]));
            } else {
                style.addStyle(LineStyle.WIDTH, new ZoomStyleValue(parameters[1]));
            }
        }
        if (parameters.length > 2) {
            String pattern[] = parameters[2].split(",");
            float[] p = new float[pattern.length];
            for (int i = 0; i < pattern.length; i++) {
                p[i] = Float.parseFloat(pattern[i]);
            }
            style.addStyle(LineStyle.PATTERN, new ConstantStyleValue<float[]>(p));

        }
    }

    public FigureStyle<LineStyle> getStyle() {
        return style;
    }    
}
