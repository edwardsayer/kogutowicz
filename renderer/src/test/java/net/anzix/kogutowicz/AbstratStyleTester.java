/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import java.io.File;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.PngLabelFigure;
import net.anzix.kogutowicz.style.filter.TagFilter;
import org.junit.Test;

/**
 *
 * @author elek
 */
public abstract class AbstratStyleTester {

    @Test
    public void testRender() {
        QuadraticProcessor proc = new QuadraticProcessor();
        RenderContext context = new RenderContext();
        context.setTopLeft(new Node(0, 100));
        context.setBottomRight(new Node(100, 0));
        context.setProjection(new EqualProjection());
        context.setBasedir(new File("/tmp"));
        context.setDivision(new SimpleTileDivision(Zoom.zoom(13), context.getTopLeft(), context.getBottomRight()));
        context.setZoom(Zoom.zoom(14));
        context.setSize(new Size(200, 200));

        InMemory mem = new InMemory();
        initMap(mem);
        Cartographer c = new Cartographer(mem);
        initStyles(c);
        context.setCartographer(c);

        proc.setContext(context);

        Java2DFileRenderer renderer = new Java2DFileRenderer();
        renderer.setOutputFile(new File("/tmp/icon.png"));

        proc.setRenderer(renderer);


        proc.init();
        proc.process();
        proc.release();
        renderer.release();
    }

    public abstract void initMap(InMemory mem);

    public abstract void initStyles(Cartographer c);
}
