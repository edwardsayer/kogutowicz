package net.anzix.kogutowicz;

import java.io.File;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.GeometryCache;
import net.anzix.kogutowicz.processor.GeometryCachePrinter;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.style.Cartographer;
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
        context.setDivision(createTileDivision(context));
        context.setZoom(createZoom());
        context.setSize(new Size(200, 200));

        proc.addGeometryCacheProcessor(new GeometryCachePrinter());

        InMemory mem = new InMemory();
        mem.setDivision(context.getDivision());

        initMap(mem);
        Cartographer c = new Cartographer(mem);
        initStyles(c);
        context.setCartographer(c);

        proc.setContext(context);

        Java2DFileRenderer renderer = new Java2DFileRenderer();
        renderer.setOutputFile(getOutputFile());

        proc.setRenderer(renderer);


        proc.init();
        proc.process();
        proc.release();
        renderer.release();
    }

    public TileDivision createTileDivision(RenderContext context) {
        return new SimpleTileDivision(Zoom.zoom(13), context.getTopLeft(), context.getBottomRight());
    }

    public Zoom createZoom() {
        return Zoom.zoom(14);
    }

    public abstract void initMap(InMemory mem);

    public abstract void initStyles(Cartographer c);

    public abstract File getOutputFile();
}
