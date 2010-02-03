package net.anzix.kogutowicz.config;

import java.io.IOException;
import java.util.Properties;
import net.anzix.kogutowicz.app.MapApplication;
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
    public void testConvertFromString() throws IOException, ClassNotFoundException {
        Properties p = new Properties();
        p.put("map", "ImageMap");
        assertNotNull(getClass().getResourceAsStream("/META-INF/services/" + MapApplication.class.getCanonicalName()));
        ConfigReader instance = new ConfigReader(p);
        Class clz = instance.findImplementation(MapApplication.class, "ImageMap");


        assertNotNull(clz);
    }

    @Test
    public void testConvertToString() {
        //given
        Properties p = new Properties();
        p.put("MAP", TilesMap.class.getCanonicalName());
        ConfigReader instance = new ConfigReader(p);

        //then
        assertEquals(new Float(12), instance.convertFromString("NONE", "NONE", "12", Float.class));
        assertEquals(new String("STRING"), instance.convertFromString("NONE", "NONE", "STRING", String.class));
        Object o = instance.convertFromString("MAP", "MAP", TilesMap.class.getCanonicalName(), TilesMap.class);
        assertNotNull(o);
        assertTrue(o instanceof TilesMap);

    }

    @Test
    public void testStart() {
        //given

        Properties p = new Properties();
        p.put("map", SampleApp.class.getCanonicalName());
        p.put("map.parent", Parent.class.getCanonicalName());
        p.put("map.parent.child", Child.class.getSimpleName());
        ConfigReader instance = new ConfigReader(p);

        //then
        SampleApp app = (SampleApp) instance.start();
        assertNotNull(app);
        app = app.getThiz();
        assertNotNull(app.getParent());
        assertNotNull(app.getParent().getChild());

    }
}
