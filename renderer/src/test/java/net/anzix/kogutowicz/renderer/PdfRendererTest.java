/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.renderer;


import java.io.File;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.geometry.Style;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineStyle;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class PdfRendererTest {

    public PdfRendererTest() {
    }

 
    /**
     * Test of release method, of class PdfRenderer.
     */
    @Test
    public void testRender() {
        PdfRenderer renderer = new PdfRenderer();
        renderer.setOutputFile(new File("target/pdf-test.pdf"));
        renderer.initSpace(new Size(100,100));

        Style<LineStyle> style = new Style<LineStyle>();
        style.addStyle(LineStyle.WIDTH, 4f);
        style.addStyle(LineStyle.COLOR, Color.BLACK);

        Line line = new Line(style);
        line.addPoint(new Point(0,0));
        line.addPoint(new Point(100,100));

        renderer.renderGeometry(Layer.ZERO, line);

        line = new Line(style);
        line.addPoint(new Point(0,100));
        line.addPoint(new Point(100,0));
        
        renderer.renderGeometry(Layer.ZERO, line);

        renderer.release();
        assertNotNull(renderer);
    }

  

}
