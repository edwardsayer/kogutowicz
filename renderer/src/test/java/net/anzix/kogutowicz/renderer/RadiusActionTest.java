/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Icon;
import net.anzix.kogutowicz.style.Layer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class RadiusActionTest {

    private Map<Layer, List<GeometryElement>> elements;

    private Collection<Layer> layerOrder;

    private Layer l1 = Layer.ZERO;

    @Before
    public void setUp() {

        layerOrder = new ArrayList();
        layerOrder.add(l1);
        elements = new HashMap();
        List ics = new ArrayList();
        ics.add(new Icon(10, 10));
        ics.add(new Icon(50, 50));
        ics.add(new Icon(200, 200));
        ics.add(new Icon(110, 110));
        elements.put(l1, ics);

    }

    @Test
    public void testAction() {
        assertEquals(4, elements.get(l1).size());
        new RadiusAction().action(elements, layerOrder);
        assertEquals(2, elements.get(l1).size());
    }
}
