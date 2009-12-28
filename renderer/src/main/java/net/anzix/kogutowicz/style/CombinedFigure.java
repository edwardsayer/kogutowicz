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
import net.anzix.kogutowicz.geometry.GeometryElement;

/**
 *
 * @author elek
 */
public class CombinedFigure extends Figure {

    private List<Figure> figures = new ArrayList();

    public CombinedFigure() {
    }

    public CombinedFigure(Figure... pFigures) {
        for (Figure f : pFigures) {
            figures.add(f);
        }
    }

    @Override
    public List<GeometryElement> drawElements(Projection projection, Element e, Zoom zoom) {
        List<GeometryElement> results = new ArrayList();
        int zind = getZindex();
        for (Figure figure : figures) {
            if (figure.getStartZoom() <= zoom.getLevel() && figure.getEndZoom() >= zoom.getLevel()) {
                List<GeometryElement> ges = figure.drawElements(projection, e, zoom);
                for (GeometryElement el : ges) {
                    el.setZindex(zind);
                }
                results.addAll(ges);
            }
            zind += 100;
        }
        return results;

    }

    public void add(Figure figure) {
        figures.add(figure);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    @Override
    public void init(String... parameters) {
        
    }
}
