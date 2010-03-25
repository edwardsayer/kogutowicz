/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import net.anzix.kogutowicz.style.PolygonStyle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class PolygonTest {

    @Test
    public void testGetBoundingBox() {
        Polygon p = new Polygon(new Style<PolygonStyle>());
        p.addPoint(new Point(0, 5));
        p.addPoint(new Point(5, 10));
        p.addPoint(new Point(10, 5));
        p.addPoint(new Point(5, 0));
        p.addPoint(new Point(0, 5));
        assertEquals(new CoordBox(0, 10, 10, 0), p.getBoundingBox());
    }
}
