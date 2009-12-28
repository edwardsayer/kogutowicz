/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.*;
import net.anzix.kogutowicz.style.parser.CsvMapStyle;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class CsvMapStyleTest {

    /**
     * Test of applyStyle method, of class CsvMapStyle.
     */
    @Test
    public void testApplyStyle() {
        CsvMapStyle st = new CsvMapStyle();
        st.setSource(new File("src/test/osmstyle.csv"));
        Cartographer c = new Cartographer(null);
        st.applyStyle(c);
        assertEquals(2, c.getLayers().size());

        Layer area = c.getLayer("area");
        assertEquals(5, area.getFigures().size());
        
        Layer streets = c.getLayer("streets");
        assertEquals(1, streets.getFigures().size());        
        assertEquals(CombinedFigure.class, streets.getFigures().get(0).getClass());
        CombinedFigure cf = (CombinedFigure) streets.getFigures().get(0);
        assertEquals(2, cf.getFigures().size());

        LineFigure l1 = (LineFigure) cf.getFigures().get(0);
        LineFigure l2 = (LineFigure) cf.getFigures().get(1);

        assertNotNull(cf.getFilter());


    }
}
