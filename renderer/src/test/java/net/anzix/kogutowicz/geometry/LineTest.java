/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class LineTest {

    @Test
    public void testGetBoundingBox() {
        Line l = new Line();
        l.addPoint(new Point(0, 5));
        l.addPoint(new Point(5, 10));
        l.addPoint(new Point(10, 5));
        l.addPoint(new Point(5, 0));
        l.addPoint(new Point(0, 5));
        assertEquals(new CoordBox(0, 10, 10, 0), l.getBoundingBox());
    }
}
