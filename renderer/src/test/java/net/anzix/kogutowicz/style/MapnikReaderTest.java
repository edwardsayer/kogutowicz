/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style;

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
public class MapnikReaderTest {

    public MapnikReaderTest() {
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
     * Test of convertScaleToZoomLevel method, of class MapnikReader.
     */
    @Test
    public void testConvertScaleToZoomLevel() {
        MapnikReader mr = new MapnikReader();

        assertEquals(10, mr.convertScaleToZoomLevel(300000));
        assertEquals(10, mr.convertScaleToZoomLevel(500000));
        assertEquals(9, mr.convertScaleToZoomLevel(600000));
    }

}