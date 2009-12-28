/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;
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
public class TagFilterTest {

    public TagFilterTest() {
    }

    /**
     * Test of getKey method, of class TagFilter.
     */
    @Test
    public void testGetKey() {
        TagFilter tf = new TagFilter("key", "value1,value2");
        assertEquals("key", tf.getKey());
        assertEquals(2, tf.getValues().size());
        assertEquals("value1", tf.getValues().get(0));
        assertEquals("value2", tf.getValues().get(1));

    }
}
