/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Collection;
import java.util.Iterator;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.processor.ProcessMatrix.TilePosition;
import net.anzix.kogutowicz.style.Layer;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class ProcessMatrixTest {

//    private static ProcessMatrix testMatrix;
//
//    @BeforeClass
//    public static void init() {
//        RectangleTileDivision division = new RectangleTileDivision(new Node(19, 47), new Node(20, 46), 10, 5);
//        testMatrix = new ProcessMatrix(new Node(19, 47), new Node(20, 46), division, 10, 10);
//    }
//
//    public ProcessMatrixTest() {
//    }
//
//    @Test
//    public void testInit() {
//
//        TileRectangle rectangle = testMatrix.getTileRectangle(1, 2);
//        assertEquals(46.8, rectangle.getTopLeft().getLatitude(), 0.0001);
//        assertEquals(19.1, rectangle.getTopLeft().getLongitude(), 0.0001);
//    }
//
////    @Test
////    public void testRectangles() {
////        Point n1 = Point.projectFromNode(testMatrix.getProjecion(), new Node(19.35, 46.45));
////        Point n2 = Point.projectFromNode(testMatrix.getProjecion(), new Node(19.36, 46.46));
////        Collection<TileRectangle> rectangles = testMatrix.getRectangles(new CoordBox(n1, n2));
////        assertEquals(1, rectangles.size());
////        assertEquals(new TilePosition(4, 2), testMatrix.getTilePosition(rectangles.iterator().next()));
////        assertEquals(testMatrix.getTileRectangle(4, 2), rectangles.iterator().next());
////
////        n1 = Point.projectFromNode(testMatrix.getProjecion(), new Node(19.35, 46.45));
////        n2 = Point.projectFromNode(testMatrix.getProjecion(), new Node(19.46, 46.46));
////        rectangles = testMatrix.getRectangles(new CoordBox(n1, n2));
////        assertEquals(2, rectangles.size());
////        Iterator<TileRectangle> it = rectangles.iterator();
////        assertEquals(new TilePosition(4, 2), testMatrix.getTilePosition(it.next()));
////        assertEquals(new TilePosition(5, 2), testMatrix.getTilePosition(it.next()));
////
////
////    }
//
////    @Test
////    public void addGeometries() {
////
////        Line line = new Line();
////        line.addPoint(Point.projectFromNode(testMatrix.getProjecion(), new Node(19.35, 46.45)));
////        line.addPoint(Point.projectFromNode(testMatrix.getProjecion(), new Node(19.36, 46.46)));
////        testMatrix.addGeometry(Layer.ZERO, line);
////
////        line = new Line();
////        line.addPoint(Point.projectFromNode(testMatrix.getProjecion(), new Node(19.38, 46.45)));
////        line.addPoint(Point.projectFromNode(testMatrix.getProjecion(), new Node(19.36, 46.49)));
////        testMatrix.addGeometry(Layer.ZERO, line);
////
////
////        assertEquals(1, testMatrix.getGeometries().getGeometries().size());
////        TileRectangle rect = testMatrix.getTileRectangle(4, 2);
////        assertEquals(2, testMatrix.getGeometries().getElements(rect).size());
////    }
//
//    @Test
//    public void testInitOSM() {
//        ProcessMatrix matrix = new ProcessMatrix(new Node(19, 47), new Node(20, 46), new OSMTileDivision(Zoom.zoom(14)), 10, 10);
//
//        TileRectangle tile = matrix.getTileRectangle(1, 1);
//        assertEquals(46.9, tile.getTopLeft().getLatitude(), 0.0001);
//        assertEquals(19.1, tile.getTopLeft().getLongitude(), 0.0001);
//    }
//
//    /**
//     * Test of getBoundingBox method, of class ProcessMatrix.
//     */
//    @Test
//    public void testGetBoundingBox() {
//        ProcessMatrix matrix = new ProcessMatrix(new Node(19, 47), new Node(20, 46), new OSMTileDivision(new Zoom(14)), 10, 10);
//        assertEquals(new Box(19.1d, 46.9d, 19.2d, 46.8d), matrix.getBoundingBox(1, 1));
//
//    }
}
