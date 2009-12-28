/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.datasource;

import java.io.File;
import java.util.Collection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class OsmFileTest {

    public OsmFileTest() {
    }

    /**
     * Test of getElements method, of class OsmFile.
     */
    @Test
    public void testGetElements() {
        OsmFile file = new OsmFile(new File("src/test/simple.osm"));
        TileDivision div = new RectangleTileDivision(new Node(18.5, 48), new Node(19.5, 47), 10, 10);
        file.init(div);
        Collection<Element> es = file.getElements(new TileCoord(5, 4));
        assertEquals(4, es.size());
        for (Element e : es) {
            System.out.println("X" + e);
        }
        System.out.println("asd");
    }
}
