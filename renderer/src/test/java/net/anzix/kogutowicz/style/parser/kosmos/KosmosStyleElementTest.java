/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser.kosmos;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class KosmosStyleElementTest {

    public KosmosStyleElementTest() {
    }

    /**
     * Test of parser method, of class KosmosStyleElement.
     */
    @Test
    public void testParser1() {

        String rawStyle = "Polygon (Color=#ffbc09)";
        List<KosmosStyleElement> elements = KosmosStyleElement.parse(rawStyle);
        assertEquals(1, elements.size());
        KosmosStyleElement e = elements.get(0);
        assertEquals(KosmosStyleType.POLYGON, e.getType());
        assertEquals("#ffbc09", e.getParameter("Color"));


    }

    @Test
    public void testPaser2() {
        String rawStyle = "Polyline (MinZoom=1, Color=#8BCCE5, Width=11:1;17:5, Curved=true)";
        List<KosmosStyleElement> elements = KosmosStyleElement.parse(rawStyle);
        assertEquals(1, elements.size());
        KosmosStyleElement e = elements.get(0);
        assertEquals(KosmosStyleType.POLYLINE, e.getType());
        assertEquals("#8BCCE5", e.getParameter("Color"));
        assertEquals("true", e.getParameter("Curved"));
    }

    @Test
    public void testPaser3() {
        String rawStyle = "Polygon (Color=#F0F0D8) Text (MinZoom=15, Color=black, TagToUse=name, FontName=Times New Roman, FontStyle=bold, FontSize=15:6;17:10, TextMode=AreaCenter)";
        List<KosmosStyleElement> elements = KosmosStyleElement.parse(rawStyle);
        assertEquals(2, elements.size());
        KosmosStyleElement e = elements.get(0);
        assertEquals(KosmosStyleType.POLYGON, e.getType());
        assertEquals("#F0F0D8", e.getParameter("Color"));

        e = elements.get(1);
        assertEquals(KosmosStyleType.TEXT, e.getType());
        assertEquals("15", e.getParameter("MinZoom"));
    }
}
