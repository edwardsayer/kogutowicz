package net.anzix.kogutowicz.style.parser;

import java.io.File;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class MapnikLayerTest {

    public MapnikLayerTest() {
    }

    @Test
    public void testSetType() throws JDOMException, IOException {

        Document d = new SAXBuilder().build(new File("src/test/mapnik-layer.xml"));
        Element rootElement = d.getRootElement();
        MapnikLayer layer = new MapnikLayer(rootElement);
        assertEquals("postgis", layer.getSourceType());
        
    }
}
