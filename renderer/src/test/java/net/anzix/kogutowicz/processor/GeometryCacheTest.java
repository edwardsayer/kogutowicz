/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.style.Layer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class GeometryCacheTest {

    public GeometryCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testAddGeometry() {
        Set<GeometryElementOnLayer> geos = new TreeSet(new Comparator<GeometryElementOnLayer>() {

            @Override
            public int compare(GeometryElementOnLayer o1, GeometryElementOnLayer o2) {
                int i = new Integer(o1.getLayer().getWeight()).compareTo(new Integer(o2.getLayer().getWeight()));
                if (i != 0) {
                    return i;
                } else {
                    return -1;
                }
            }
        });
        assertFalse(new GeometryElementOnLayer(new Point(1, 1), Layer.ZERO).equals(new GeometryElementOnLayer(new Point(1, 2), Layer.ZERO)));
        geos.add(new GeometryElementOnLayer(new Point(1, 1), Layer.ZERO));
        geos.add(new GeometryElementOnLayer(new Point(1, 2), Layer.ZERO));
        assertEquals(2, geos.size());

    }
}
