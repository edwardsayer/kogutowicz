package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.GeometryElement;

/**
 * Figure combined from other figures.
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
    public List<GeometryElement> drawElements(Element e, Zoom zoom) {
        List<GeometryElement> results = new ArrayList();
        int zind = getZindex();
        for (Figure figure : figures) {
            if (figure.getStartZoom() <= zoom.getLevel() && figure.getEndZoom() >= zoom.getLevel()) {
                List<GeometryElement> ges = figure.drawElements(e, zoom);
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
    public Figure init(String... parameters) {
        return this;
        
    }
}
