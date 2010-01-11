/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.List;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.geometry.Style;

/**
 * Map element with style.
 *
 * A figure convert a mapl element to a GeometryElement.
 * @author elek
 */
public abstract class Figure {

    private int zindex = 0;

    private int startZoom = 0;

    private int endZoom = 20;

    private Filter filter = new AndFilter();

    private Class<? extends Element> type;

    // private Style style = new Style();
    public abstract List<GeometryElement> drawElements(Element element, Zoom zoom);

    public abstract void init(String... parameters);

    public Point convertNodeToPoint(Node n) {
        return new Point(n.getLongitude(), n.getLatitude());
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /* filter shortcuts */
    public Figure addFilter(Filter f) {
        if (this.filter instanceof AndFilter) {
            ((AndFilter) this.filter).addFilter(f);
        } else {
            throw new IllegalArgumentException("this.filter isn't the default AndFilter: " + this.filter.getClass());
        }
        return this;
    }

    public Figure startZoom(int level) {
        startZoom = level;
        return this;
    }

    public Figure endZoom(int level) {
        endZoom = level;
        return this;
    }

    public <T extends Object> Style<T> convertStyle(FigureStyle<T> from, Zoom zoom) {
        Style<T> st = new Style<T>();
        for (T key : from.getStyles().keySet()) {
            st.addStyle(key, from.getStyles().get(key).getValue(zoom));
        }
        return st;
    }

    public int getZindex() {
        return zindex;
    }

    public Figure setZindex(int zindex) {
        this.zindex = zindex;
        return this;
    }

    public int getStartZoom() {
        return startZoom;
    }

    public void setStartZoom(int startZoom) {
        this.startZoom = startZoom;
    }

    public int getEndZoom() {
        return endZoom;
    }

    public void setEndZoom(int endZoom) {
        this.endZoom = endZoom;
    }

    public Class<? extends Element> getType() {
        return type;
    }

    public void setType(Class<? extends Element> type) {
        this.type = type;
    }
}
