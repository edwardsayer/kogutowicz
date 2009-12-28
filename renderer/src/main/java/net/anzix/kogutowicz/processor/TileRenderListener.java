/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.io.File;
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
public class TileRenderListener implements TileStateListener {

    private Logger logger = Logger.getLogger(ImageRenderListener.class.getCanonicalName());

    private Renderer renderer = new Java2DFileRenderer();

    private Transformation transformation;

    double width = 256;

    double height = 256;

    private TileCoord startTile;

    private int zoom;

    private File outputDirectory;

    public TileRenderListener(File output, TileCoord tl, int zoom) {
        this.outputDirectory = output;
        this.startTile = tl;
        this.zoom = zoom;
    }

    @Override
    public void onStateChange(ProcessMatrix matrix, TileCoord coord, TileState newState) {

        if (newState != TileState.RENDERABLE) {
            return;
        }

        CoordBox transform_box = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getDivision().getBox(coord));

        renderer = initRenderer(width, height, transform_box, createFile(outputDirectory, matrix, coord));
        CoordBox box = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getBoundary());
        renderRectangle(box, coord, matrix);
        renderer.release();        
        matrix.setTileState(coord, TileState.RENDERED);

    }

    public void renderRectangle(CoordBox box, TileCoord coord, ProcessMatrix matrix) {
        CoordBox b = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getDivision().getBox(coord));
        logger.log(Level.FINE, "Rendering " + coord);
        GeometryCache cache = matrix.getGeometries();

        Collection<GeometryElementOnLayer> elements = cache.getElements(coord);
        for (GeometryElementOnLayer element : elements) {
            renderer.renderGeometry(element.getLayer(), element.getElement(), transformation, null);
        }

//        Line l = new Line();
//
//        l.addPoint(new Point(b.getTopLeft().getX(), b.getTopLeft().getY()));
//        l.addPoint(new Point(b.getBottomRight().getX(), b.getBottomRight().getY()));
//        renderer.renderGeometry(Layer.TOP, l, transformation, b);
//
//        l = new Line();
//        l.addPoint(new Point(b.getTopLeft().getX(), b.getBottomRight().getY()));
//        l.addPoint(new Point(b.getBottomRight().getX(), b.getTopLeft().getY()));
//        renderer.renderGeometry(Layer.TOP, l, transformation, b);

    }

    public Renderer initRenderer(double width, double height, CoordBox box, File file) {
        Java2DFileRenderer r2d = new Java2DFileRenderer();
        r2d.initSpace(width, height);
        this.transformation = new BaseTransformation(box, width, height);

        r2d.setOutputFile(file);
        return r2d;
    }

    @Override
    public void init(ProcessMatrix matrix) {
    }

    @Override
    public void release(ProcessMatrix matrix) {
    }

    private File createFile(File root, ProcessMatrix matrix, TileCoord coord) {
        File file = new File(root, "/" + zoom + "/" + coord.getX() + "/" +  coord.getY() + ".png");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}
