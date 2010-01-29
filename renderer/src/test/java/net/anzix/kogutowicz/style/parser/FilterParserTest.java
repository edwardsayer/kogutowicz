/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.*;
import java.util.List;
import javax.lang.model.element.Element;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Node;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class FilterParserTest {

    @Test
    public void testshuttingYard() {
        String fltr = "landuse = residential";
        FilterParser fp = new FilterParser();
        List tokens = fp.shuttingYard(fltr);
        assertEquals(3, tokens.size());
        assertEquals("landuse", tokens.get(0));
        assertEquals("residential", tokens.get(1));
        assertEquals(EqualOperator.class, tokens.get(2).getClass());


        fltr = "landuse = residential OR area = natural";
        tokens = fp.shuttingYard(fltr);
        assertEquals(7, tokens.size());
        assertEquals("landuse", tokens.get(0));
        assertEquals("residential", tokens.get(1));
        assertEquals(EqualOperator.class, tokens.get(2).getClass());
        assertEquals("area", tokens.get(3));
        assertEquals("natural", tokens.get(4));
        assertEquals(EqualOperator.class, tokens.get(5).getClass());
        assertEquals(OrOperator.class, tokens.get(6).getClass());

        fltr = "landuse = residential OR ( area = natural )";
        tokens = fp.shuttingYard(fltr);
        for (Object s :fp.shuttingYard(fltr)){
            System.out.println(s);
        }
        assertEquals(7, tokens.size());
        assertEquals("landuse", tokens.get(0));
        assertEquals("residential", tokens.get(1));
        assertEquals(EqualOperator.class, tokens.get(2).getClass());
        assertEquals("area", tokens.get(3));
        assertEquals("natural", tokens.get(4));
        assertEquals(EqualOperator.class, tokens.get(5).getClass());
        assertEquals(OrOperator.class, tokens.get(6).getClass());

        fltr = "landuse = residential OR NOT ( area = natural )";
        tokens = fp.shuttingYard(fltr);
        assertEquals(8, tokens.size());
        assertEquals("landuse", tokens.get(0));
        assertEquals("residential", tokens.get(1));
        assertEquals(EqualOperator.class, tokens.get(2).getClass());
        assertEquals("area", tokens.get(3));
        assertEquals("natural", tokens.get(4));
        assertEquals(EqualOperator.class, tokens.get(5).getClass());
        assertEquals(NotFunction.class, tokens.get(6).getClass());
        assertEquals(OrOperator.class, tokens.get(7).getClass());
    }

    @Test
    public void testParse() {
        String fltr = "landuse = residential";
        FilterParser fp = new FilterParser();
        Filter f = fp.parse(fltr);
        assertEquals(TagFilter.class, f.getClass());
        assertEquals("landuse", ((TagFilter) f).getKey());
        assertEquals("residential", ((TagFilter) f).getValues().get(0));


        fltr = "landuse = residential OR second = valami";
        f = fp.parse(fltr);
        assertEquals(OrFilter.class, f.getClass());

        fltr = "landuse=residential";
        f = fp.parse(fltr);
    }

    @Test
    public void testParseNot() {
        String fltr = "highway = motorway_link and not (tunnel = yes or tunnel = true)";
        FilterParser fp = new FilterParser();
        Filter f = fp.parse(fltr);
        assertEquals(AndFilter.class, f.getClass());
        assertEquals(NotFilter.class, ((AndFilter) f).getFilters().get(1).getClass());


    }

//    @Test
//    public void mapnikFilter1() {
//        FilterParser fp = new FilterParser();
//        Filter f = fp.parse("[building] <> 'station' and [building] <> 'supermarket' and [building] <> ''");
//        System.out.println(f);
//        Node n = new Node(12L,12L);
//        n.addTag("building", "asd");
//        assertFalse(f.is(n, Zoom.zoom(12)));
//    }
    @Test
    public void validFilter() {
        FilterParser fp = new FilterParser();
        fp.parse("building <> station AND building <> supermarket AND building <> #EMPTY#");
    }
}
