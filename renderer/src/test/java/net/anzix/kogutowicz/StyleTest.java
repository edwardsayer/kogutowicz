/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Figure;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.parser.CsvMapStyle;
import net.anzix.kogutowicz.style.parser.CsvWriter;

/**
 *
 * @author elek
 */
public class StyleTest extends AbstratStyleTester {

    @Override
    public void initMap(InMemory mem) {

        Way w = new Way();
        w.addNode(new Node(25, 25));
        w.addNode(new Node(70, 75));
        w.addTag("highway", "residential");
        mem.addWay(w);

        Node n = new Node(50, 50);
        n.addTag("amenity", "cafe");
        mem.addNode(n);

    }

    @Override
    public void initStyles(Cartographer c) {
        FileWriter f = null;

        CsvMapStyle ms = new CsvMapStyle();
        ms.setSource(new File("../samples/src/main/resources/styles/osmstyle.csv"));

        ms.applyStyle(c);
        for (Layer l : c.getLayers()) {
            for (Figure fig : l.getFigures()) {
                fig.setStartZoom(fig.getStartZoom() + 1);
                if (fig.getEndZoom() > 0) {
                    fig.setEndZoom(fig.getEndZoom() + 2);
                }
            }
        }


    }

    @Override
    public File getOutputFile() {
        return new File("target/style.png");
    }

    @Override
    public Zoom createZoom() {
        return Zoom.zoom(14);
    }
}
