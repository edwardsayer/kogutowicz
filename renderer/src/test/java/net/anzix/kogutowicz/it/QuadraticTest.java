/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.it;

import java.io.File;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.EqualProjection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.style.TuhuStyleFactory;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.TagFilter;

/**
 *
 * @author elek
 */
public class QuadraticTest {

    @Test
    public void testRender() {
        Node no1 = new Node(18.87, 47.59);
        Node no2 = new Node(18.9, 47.57);
        RectangleTileDivision division = new RectangleTileDivision(no1, no2, 2, 2);

        InMemory m = new InMemory();
        List<Node> nodes = new ArrayList();
        nodes.add(new Node(18.899, 47.588));
        nodes.add(new Node(18.872, 47.572));
        nodes.add(new Node(18.882, 47.582));
        nodes.add(new Node(18.872, 47.589));
        for (Node n1 : nodes) {
            m.add(division.getTileCoord(n1), n1);
            for (Node n2 : nodes) {
                if (n1 != n2) {
                    Way w = new Way();
                    w.addNode(n1);
                    w.addNode(n2);
                    w.addTag("highway", "residential");
                    addWay(m, division, w);

                }
            }
        }


        Cartographer c = new Cartographer(m);
        Layer streets = c.createLayer("way");
        LineFigure footway = new LineFigure();
        footway.addFilter(new TagFilter("highway", "residential")).startZoom(14);
        streets.addFigure(footway);
        c = new TuhuStyleFactory().applyStyle(c);


        ProcessMatrix testMatrix = new ProcessMatrix(no1, no2, division, 2, 2);        

        QuadraticProcessor p = new QuadraticProcessor(new EqualProjection(),testMatrix, c);
        Java2DFileRenderer renderer = new Java2DFileRenderer();
        renderer.initSpace(new Size(800,800));
        renderer.setOutputFile(new File("target/test.png"));
        p.setRenderer(renderer);
        p.setWidth(800);
        p.setHeight(800);
        p.process();
        p.release();

    }

    protected void addWay(InMemory m, RectangleTileDivision division, Way w) {
        TileCoord min = null;
        TileCoord max = null;
        for (Node n : w.getNodes()) {
            TileCoord curr = division.getTileCoord(n);
            if (min == null) {
                min = curr;
                max = curr;
            } else if (min.getX() > curr.getX()) {
                min.setX(curr.getX());
            } else if (min.getY() > curr.getY()) {
                min.setY(curr.getY());
            } else if (max.getX() < curr.getX()) {
                max.setX(curr.getX());
            } else if (max.getY() < curr.getY()) {
                max.setY(curr.getY());
            }
        }
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                m.add(new TileCoord(x, y), w);
            }
        }
    }
}
