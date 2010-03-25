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
public class MercatorTest {

    public MercatorTest() {
    }

    @Test
    public void testGetX() {
        Mercator m = new Mercator();
        double[] point = m.getXY(18, 47);


        assertEquals(2003750.8342789242, point[0], 0.0000001);
        assertEquals(5910809.619701102, point[1], 0.0000001);
        System.out.println(point[0] + " " + point[1]);
        double[] p2 = m.getLanLon(point[0], point[1]);
        System.out.println(p2[0] + " " + p2[1]);
        assertEquals(18, p2[0],0.00000001);
        assertEquals(47, p2[1],0.00000001);

    }

    @Test
    public void testGetXY() {
    }

    @Test
    public void testGetLanLon() {
    }
}
