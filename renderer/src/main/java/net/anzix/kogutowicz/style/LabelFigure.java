/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Text;

/**
 *  Simple Label element
 *
 * @author elek
 */
public class LabelFigure extends Figure {

    private String message;

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> result = new ArrayList();
        Text t = new Text();
//        t.setText(element.tagValue(tag));
        t.setText("UNKNOWN");
        result.add(t);
        return result;
    }

    @Override
    public Figure init(String... parameters) {
        return this;
    }
}
