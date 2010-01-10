/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz;

import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class SimpleTileDivisionTest {

    public SimpleTileDivisionTest() {
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

    @After
    public void tearDown() {
    }

    /**
     * Test of getTileCoord method, of class SimpleTileDivision.
     */
    @Test
    public void testGetTileCoord() {
        TileDivision div = new RectangleTileDivision(new Node(18.5, 48), new Node(19.5, 47), 10, 10);
        
        assertEquals(new TileCoord(4,5), div.getTileCoord(new Node(18.95,47.45)));
        
    }



}