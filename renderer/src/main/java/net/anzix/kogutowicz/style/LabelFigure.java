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
import net.anzix.kogutowicz.geometry.Label;
import net.anzix.kogutowicz.geometry.Point;

/**
 *  Simple Label element
 *
 * @author elek
 */
public class LabelFigure extends Figure {

    private String tagName = "name";

    public LabelFigure() {
        // By default, support both Node and Way elements
    }

    @Override
    public List<GeometryElement> drawElements(Element element, Zoom zoom) {
        List<GeometryElement> result = new ArrayList();
        
        // Get the tag value to display
        String labelText = element.tagValue(tagName);
        if (labelText == null || labelText.isEmpty()) {
            return result; // No label to display
        }
        
        // Create a label at the appropriate position
        Label label = null;
        
        if (element instanceof Way) {
            // For ways, position the label at the middle of the way
            Way way = (Way) element;
            Node middleNode = way.getHalfPosition();
            if (middleNode != null) {
                label = new Label(middleNode.getLongitude(), middleNode.getLatitude());
            }
        } else if (element instanceof Node) {
            // For nodes, position the label at the node's position
            Node node = (Node) element;
            label = new Label(node.getLongitude(), node.getLatitude());
        }
        
        if (label != null) {
            label.setMessages(labelText);
            result.add(label);
        }
        
        return result;
    }

    @Override
    public Figure init(String... parameters) {
        if (parameters.length > 0) {
            this.tagName = parameters[0];
        }
        return this;
    }
    
    /**
     * Set the tag name to use for the label text
     * 
     * @param tagName the tag name to use
     * @return this figure for method chaining
     */
    public LabelFigure setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
}