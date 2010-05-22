/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import java.io.OutputStreamWriter;
import java.io.Writer;
import net.anzix.kogutowicz.datasource.EmptyDatasource;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.PolygonFigure;
import net.anzix.kogutowicz.style.filter.TagFilter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class CsvWriterTest {

    /**
     * Test of writer method, of class CsvWriter.
     */
    @Test
    public void testWriter() throws Exception {
        Writer writer = new OutputStreamWriter(System.out);
        Cartographer c = new Cartographer(new EmptyDatasource());
        Layer l = new Layer("l1");

        PolygonFigure f1 = new PolygonFigure(Color.WHITE);
        f1.setFilter(new TagFilter("highway", "residential"));
        l.addFigure(f1);

        PolygonFigure f2 = new PolygonFigure(Color.BLACK);
        f2.setFilter(new TagFilter("tag", "value"));
        l.addFigure(f2);
        c.addLayer("l1",l);

        CsvWriter instance = new CsvWriter();
        instance.write(writer, c);
        writer.flush();
        assertNotNull(c);
    }
}
