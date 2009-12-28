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
    }
}
