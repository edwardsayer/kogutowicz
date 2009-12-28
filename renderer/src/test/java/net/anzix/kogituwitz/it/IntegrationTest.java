/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogituwitz.it;

import java.io.File;
import org.junit.Test;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.style.TuhuStyleFactory;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.datasource.OsmFile;
import net.anzix.kogutowicz.element.Node;
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
public class IntegrationTest {

    @Test
    public void testRender() {


        Node n1 = new Node(18.87, 47.59);
        Node n2 = new Node(18.9, 47.57);
        DataSource ds = new OsmFile(new File("TODO"));
        Cartographer c = new Cartographer(ds);
        Layer streets = c.createLayer("way");
        LineFigure footway = new LineFigure();
        footway.setStroke(1f).setPattern(new float[]{1.0f}).addFilter(
                new TagFilter("highway", "residential")).setStartZoom(14);
        streets.addFigure(footway);
        c = new TuhuStyleFactory().applyStyle(c);

        RectangleTileDivision division = new RectangleTileDivision(n1, n2, 10, 10);
        ProcessMatrix testMatrix = new ProcessMatrix(n1, n2, division, 10, 10);
        QuadraticProcessor p = new QuadraticProcessor(testMatrix, c);
        Java2DFileRenderer renderer = new Java2DFileRenderer();
        renderer.setOutputFile(new File("target/test2.png"));
        p.setRenderer(renderer);
        p.setWidth(800);
        p.setHeight(800);
        p.process();
        p.release();
    }
//    @Test
//    public void testRender() {
//
//
//        Node n1 = new Node(18.89, 47.6);
//        Node n2 = new Node(19.2, 47.5);
//        DataSource ds = new OsmFile(new File("src/test/simple.osm"));
//        Cartographer c = new Cartographer(ds);
//        c = new TuhuStyleFactory().applyStyle(c);
//
//        RectangleTileDivision division = new RectangleTileDivision(n1, n2, 10, 10);
//        ProcessMatrix testMatrix = new ProcessMatrix(n1, n2, division, 10, 10);
//        QuadraticProcessor p = new QuadraticProcessor(testMatrix, c);
//        Java2DFileRenderer renderer = new Java2DFileRenderer();
//        renderer.setOutputFile(new File("target/test2.png"));
//        p.setRenderer(renderer);
//        p.setWidth(1600);
//        p.setHeight(800);
////        p.init();
////        for (int x = testMatrix.getTileStart().getX(); x <= testMatrix.getTileEnd().getX(); x++) {
////            for (int y = testMatrix.getTileStart().getY(); y <= testMatrix.getTileEnd().getY(); y++) {
////                TileCoord t = new TileCoord(x, y);
////                for (DataSource d : c.getDataSources()) {
////                    System.out.println("elements " + t + " " + d.getElements(t));
////                }
////            }
////        }
//        p.process();
//        p.release();
//    }
}
