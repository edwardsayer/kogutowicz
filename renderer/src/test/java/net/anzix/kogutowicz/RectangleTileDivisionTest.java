/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Node;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class RectangleTileDivisionTest {

    public static RectangleTileDivision division;

    public RectangleTileDivisionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        division = new RectangleTileDivision(new Node(18, 47), new Node(19, 46.5), 10, 5);
    }

    @Test
    public void testGetTileCoord() {
        assertEquals(new TileCoord(0, 0), division.getTileCoord(new Node(18, 47)));
        assertEquals(new TileCoord(3, 1), division.getTileCoord(new Node(18.3, 46.85)));
        assertEquals(new TileCoord(3, 2), division.getTileCoord(new Node(18.3, 46.8)));
        assertEquals(new TileCoord(3, 1), division.getTileCoord(new Node(18.35, 46.85)));
    }

    @Test
    public void testGetTopLeft() {
        Node n = division.getTopLeft(new TileCoord(2, 1));
        assertEquals(18.2, n.getLongitude(), 0.00000001);
        assertEquals(46.9, n.getLatitude(), 0.00000001);
    }

    @Test
    public void testGetBottomRight() {
        Node n = division.getBottomRight(new TileCoord(2, 1));
        assertEquals(18.3, n.getLongitude(), 0.00000001);
        assertEquals(46.8, n.getLatitude(), 0.00000001);
    }

    @Test
    public void testOne() {
        TileDivision t = new RectangleTileDivision(new Node(18, 47), new Node(19, 46.5), 1, 1);
        assertEquals(new TileCoord(0, 0), t.getTileCoord(new Node(18.35, 46.85)));
    }
}

