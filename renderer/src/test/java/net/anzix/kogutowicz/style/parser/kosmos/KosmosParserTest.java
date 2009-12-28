/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser.kosmos;

import java.io.File;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Figure;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.PolygonFigure;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class KosmosParserTest {

    /**
     * Test of applyStyle method, of class KosmosParser.
     */
    @Test
    public void testApplyStyle() throws Exception {
        Cartographer c = new Cartographer(null);
        KosmosParser p = new KosmosParser(new File("src/test/kosmos/Kosmos_clean_style.html"));
        p.applyStyle(c);
        System.out.println(c.getLayers().size());
        Layer l = c.getLayers().get(0);
        assertNotNull(l.getFigures());
        Figure f = l.getFigures().get(0);
        assertEquals(PolygonFigure.class,f.getClass());
        PolygonFigure pf = (PolygonFigure) f;


    }

   
}
