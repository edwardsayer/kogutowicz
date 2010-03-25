/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import net.anzix.kogutowicz.Zoom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class ZoomStyleValueTest {

    public ZoomStyleValueTest() {
    }

    @Test
    public void testGetValue() {
        ZoomStyleValue instance = new ZoomStyleValue("1:2,3:4");
        assertEquals(2, instance.getValues().size());
        assertEquals(new Double(2), instance.getValue(new Zoom(1)), 0.00001);
        assertEquals(new Double(4), instance.getValue(new Zoom(3)), 0.00001);


        instance = new ZoomStyleValue("1:2.2,3-5:4");
        assertEquals(4, instance.getValues().size());
        assertEquals(new Double(2.2), instance.getValue(new Zoom(1)), 0.00001);
        assertEquals(new Double(4), instance.getValue(new Zoom(3)), 0.00001);
        assertEquals(new Double(4), instance.getValue(new Zoom(4)), 0.00001);
        assertEquals(new Double(4), instance.getValue(new Zoom(5)), 0.00001);
    }
}
