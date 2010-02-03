package net.anzix.kogutowicz.style.parser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import net.anzix.kogutowicz.datasource.EmptyDatasource;
import net.anzix.kogutowicz.style.Cartographer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class MapnikMapStyleTest {

    public MapnikMapStyleTest() {
    }


    @Test
    public void testConvertScaleToZoomLevel(){
        MapnikMapStyle t = new MapnikMapStyle();
        assertEquals(13, t.convertScaleToZoomLevel(50000));
        assertEquals(11, t.convertScaleToZoomLevel(200000));
        assertEquals(12, t.convertScaleToZoomLevel(100000));
        assertEquals(13, t.convertScaleToZoomLevel(50000));
    }
    /**
     * Test of setSource method, of class MapnikMapStyle.
     */
    @Test
    public void testParse() throws IOException {

        MapnikMapStyle parser = new MapnikMapStyle();
        parser.setSource(new File("src/test/osm-template.xml"));
        Cartographer c = new Cartographer(new EmptyDatasource());
        parser.applyStyle(c);
        int zoom = 6;
        CsvWriter writer = new CsvWriter();
        writer.write(new OutputStreamWriter(System.out), c);
System.out.flush();
        //        assertTrue(c.getLayers().size() > 0);

//        for (Layer l : c.getLayers()) {
//            System.out.println("-------" + l.getName()+"------------");
//            System.out.println("-------" + l.getWeight()+"------------");
//            for (Figure f : l.getFigures()) {
//                System.out.println(f.getStartZoom());
//                System.out.println(f.getEndZoom());
//                System.out.println(f.getFilter());
//                System.out.println(f.getClass());
//                System.out.println("zindex "+f.getZindex());
//                if (f instanceof PolygonFigure) {
//                    PolygonFigure pf = (PolygonFigure) f;
//                    System.out.println(pf.getColor(Zoom.zoom(zoom)));
//                    System.out.println(pf.getStroke(Zoom.zoom(zoom)));
//                } else if (f instanceof LineFigure) {
//                    LineFigure pf = (LineFigure) f;
//                    System.out.println(pf.getStroke(Zoom.zoom(zoom)));
//                    System.out.println(pf.getColor(Zoom.zoom(zoom)));
//                }
//            }
//        }
    }

}
