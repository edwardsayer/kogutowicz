/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class ColorTest {

    @Test
    public void testConst() {
        Color c = new Color(0xA0B0C0);
        assertEquals(0xA0, c.getRed());
        assertEquals(0xB0, c.getGreen());
        assertEquals(0xC0, c.getBlue());

        c = new Color("#A0B0C0");
        assertEquals(0xA0, c.getRed());
        assertEquals(0xB0, c.getGreen());
        assertEquals(0xC0, c.getBlue());


//        c = new Color("#A0B0C010");
//        assertEquals(0xA0, c.getRed());
//        assertEquals(0xB0, c.getGreen());
//        assertEquals(0xC0, c.getBlue());
//        assertEquals(0x10, c.getAlpha());
//
//        c = new Color("#00AABBCC");
//        assertEquals(0x00, c.getRed());
//        assertEquals(0xAA, c.getGreen());
//        assertEquals(0xBB, c.getBlue());
//        assertEquals(0xCC, c.getAlpha());
    }
}
