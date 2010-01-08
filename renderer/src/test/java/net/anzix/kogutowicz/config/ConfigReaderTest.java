package net.anzix.kogutowicz.config;

import java.util.Properties;
import net.anzix.kogutowicz.app.TilesMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class ConfigReaderTest {

    /**
     * Test of start method, of class ConfigReader.
     */
    @Test
    public void testConvertToString() {
        //given
        ConfigReader instance = new ConfigReader();

        //then
        assertEquals(new Float(12), instance.convertFromString("NONE","NONE", "12", Float.class));
        assertEquals(new String("STRING"), instance.convertFromString("NONE","NONE", "STRING", String.class));
        Object o = instance.convertFromString("MAP","MAP", TilesMap.class.getCanonicalName(), TilesMap.class);
        assertNotNull(o);
        assertTrue(o instanceof TilesMap);

    }

    @Test
    public void testStart() {
        //given

        Properties p = new Properties();
        p.put("map", TestApp.class.getCanonicalName());
        p.put("map.parent", Parent.class.getCanonicalName());
        p.put("map.parent.child", Child.class.getCanonicalName());
        ConfigReader instance = new ConfigReader(p);

        //then
        TestApp app = (TestApp) instance.start();
        assertNotNull(app);
        app = app.getThiz();
        assertNotNull(app.getParent());
        assertNotNull(app.getParent().getChild());

    }
}
