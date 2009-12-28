/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.renderer.Transformation;

/**
 *
 * @author elek
 */
public class ImageRenderListener implements TileStateListener {

    private Logger logger = Logger.getLogger(ImageRenderListener.class.getCanonicalName());

    private Renderer renderer = new Java2DFileRenderer();

    private Transformation transformation;

    private int width;

    private int height;

    public ImageRenderListener(int width, int height) {
        this.width = width;
        this.height = height;
    }

   

    @Override
    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState) {
        if (newState != TileState.RENDERABLE) {
            return;
        }


        CoordBox b = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getDivision().getBox(coord));

        logger.log(Level.FINE, "Rendering " + coord);
        GeometryCache cache = matrix.getGeometries();
        Collection<GeometryElementOnLayer> elements = cache.getElements(coord);
        for (GeometryElementOnLayer element : elements) {
            renderer.renderGeometry(element.getLayer(), element.getElement(), transformation, b);
        }

        matrix.setTileState(coord, TileState.RENDERED);

    }

    public int getDefaultHeight(ProcessMatrix matrix) {
        int zoom = matrix.getZoom().getLevel();
        return (int) Math.round(256 * matrix.getProjectedHeight() / (85.051128770598 * 2) * Math.pow(2, zoom));
    }

    public int getDefaultWidth(ProcessMatrix matrix) {
        int zoom = matrix.getZoom().getLevel();
        return (int) Math.round(256 * matrix.getProjectedWidth() / 360 * Math.pow(2, zoom));
    }

    @Override
    public void init(ProcessMatrix matrix) {
//        renderer = new SystemOutputRenderer();
        CoordBox box = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getBoundary());
//        double w = getDefaultWidth(matrix);
//        double h = getDefaultHeight(matrix);
        
        renderer.initSpace(width, height);
        this.transformation = new BaseTransformation(box, width, height);
    }

    @Override
    public void release(ProcessMatrix matrix) {
        renderer.release();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }


}
