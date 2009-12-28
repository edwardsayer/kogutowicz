/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class GeometryTest {

    @Test
    public void testIsPolygonIntersect() {

        assertTrue(Geometry.isRectangleIntersectingRectangle(0, 10, 10, 0, 2, 8, 8, 2));
        assertTrue(Geometry.isRectangleIntersectingRectangle(2, 8, 8, 2, 0, 10, 10, 0));
        assertFalse(Geometry.isRectangleIntersectingRectangle(0, 10, 10, 0, 20, 10, 30, 0));
    }
}
