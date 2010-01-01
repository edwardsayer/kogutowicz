/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.renderer.Transformation;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.SelectedFigure;
import net.anzix.kogutowicz.style.SimpleSelector;

/**
 * Main controller.
 *
 * Iterate over the tiles and process the datas from the datasource.
 * @author elek
 */
public class QuadraticProcessor {

    private Transformation transformation;

    private Logger logger = Logger.getLogger(QuadraticProcessor.class.getCanonicalName());

    protected ProcessMatrix matrix;

    private Cartographer cartographer;

    private SimpleSelector selector;

    private Renderer renderer;

    private int height;

    private int width;

    public QuadraticProcessor(ProcessMatrix matrix, Cartographer cartographer) {
        this.matrix = matrix;
        this.cartographer = cartographer;
        this.selector = new SimpleSelector(cartographer);

    }

    public void process() {

        initRenderer(renderer);

        CoordBox box = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getBoundary());
        this.transformation = new BaseTransformation(box, width, height);

        Date start = new Date();
        logger.finer("Starting process.");
        init();
        TileCoord from = matrix.getTileStart();
        TileCoord to = matrix.getTileEnd();
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                processDataSource(new TileCoord(x, y));
            }
        }
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                TileCoord current = new TileCoord(x, y);
                beforeTileRender(current, renderer);
                render(current);
                afterTileRender(current, renderer);
            }
        }


        logger.finer("processed in " + (new Date().getTime() - start.getTime()) / 3600);
    }

    private void processDataSource(TileCoord tile) {
        for (DataSource ds : cartographer.getDataSources()) {
            for (Element element : ds.getElements(tile)) {
                List<SelectedFigure> figures = selector.getItem(element, matrix.getZoom());
                for (SelectedFigure figure : figures) {
                    List<GeometryElement> geometries = figure.getFigure().drawElements(matrix.getProjecion(), element, matrix.getZoom());
                    matrix.addGeometries(tile, figure.getLayer(), geometries);
                }
            }
        }
    }

    public void init() {
        for (DataSource ds : cartographer.getDataSources()) {
            ds.init(matrix.getDivision());
        }
    }

    public void release() {
        renderer.release();
    }

    private void render(TileCoord coord) {

        CoordBox b = CoordBox.projectFromBox(matrix.getProjecion(), matrix.getDivision().getBox(coord));

        logger.log(Level.FINE, "Rendering " + coord);
        GeometryCache cache = matrix.getGeometries();
        Collection<GeometryElementOnLayer> elements = cache.getElements(coord);
        for (GeometryElementOnLayer element : elements) {
            renderer.renderGeometry(element.getLayer(), element.getElement(), transformation, b);
        }

        matrix.setTileState(coord, TileState.RENDERED);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    protected void beforeTileRender(TileCoord coord, Renderer renderer) {
    }

    protected void afterTileRender(TileCoord coord, Renderer renderer) {
    }

    protected void initRenderer(Renderer renderer) {
        renderer.initSpace(width, height);
    }

    public TileDivision getDivision() {
        return matrix.getDivision();
    }
}
