
package net.anzix.kogutowicz.decorator;

import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;

/**
 *
 * @author elek
 */
public class MapRender implements Decorator {

    private Node tl;

    private Node br;

    private Projection projection;

    private Cartographer c;

    public MapRender(Node tl, Node br, Projection projection, Cartographer c) {
        this.tl = tl;
        this.br = br;
        this.projection = projection;
        this.c = c;
    }

    @Override
    public void render(RenderingWorkspace work) {
        RectangleTileDivision division = new RectangleTileDivision(tl, br, 10, 10);
        ProcessMatrix testMatrix = new ProcessMatrix(tl, br, division, 10, 10);

        QuadraticProcessor p = new QuadraticProcessor(projection, testMatrix, c);
//        {
//
//            @Override
//            protected void initRenderer(Renderer renderer, Size size) {
//                super.initRenderer(renderer, size);
//                renderer.initSpace(size);
//                BaseTransformation transf = new BaseTransformation(matrix.getBoundary().getCoordBox(), size.getWidth(), size.getHeight());
//                renderer.setTransformation(transf);
//
//            }
//        };
        BaseTransformation transf = new BaseTransformation(testMatrix.getBoundary().getCoordBox(), work.getSize().getX(), work.getSize().getY());
        work.setTransformation(transf);
        p.setRenderer(work);
        p.setWidth((int) Math.round(work.getSize().getX()));
        p.setHeight((int) Math.round(work.getSize().getY()));
//        double aspect = Math.abs((tl.getLongitude() - br.getLongitude()) / (tl.getLatitude() - br.getLatitude()));
//        System.out.println(aspect);
//        p.setWidth(800);
//        p.setHeight((int) Math.round(aspect * p.getWidth()));
        p.process();
        p.release();
    }
}
