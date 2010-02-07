package net.anzix.kogutowicz.decorator;

import com.google.inject.Inject;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.style.Cartographer;

/**
 *
 * @author elek
 */
public class MapRender implements Decorator {

    @Inject
    RenderContext context;

    @Inject
    private QuadraticProcessor p;

    public MapRender() {
    }

    @Override
    public void render(RenderingWorkspace work) {
        RectangleTileDivision division = new RectangleTileDivision(context.getTopLeft(), context.getBottomRight(), 10, 10);
        context.setDivision(division);
        BaseTransformation transf = new BaseTransformation(context.getBoundary().getCoordBox(), work.getSize().getX(), work.getSize().getY());
        work.setTransformation(transf);
        p.setRenderer(work);
        p.setWidth((int) Math.round(work.getSize().getX()));
        p.setHeight((int) Math.round(work.getSize().getY()));
        p.process();
        p.release();
    }
}
