/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.decorator.RenderingWorkspace;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.SelectedFigure;
import net.anzix.kogutowicz.style.SimpleSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main controller.
 *
 * Iterate over the tiles and process the datas from the datasource.
 * @author elek
 */
public class QuadraticProcessor {

    private Logger logger = LoggerFactory.getLogger(QuadraticProcessor.class);

    protected ProcessMatrix matrix;

    private Cartographer cartographer;

    private SimpleSelector selector;

    private Renderer renderer;

    private Projection projection;

    private Size size = new Size();

    public QuadraticProcessor(Projection projection, ProcessMatrix matrix, Cartographer cartographer) {
        this.projection = projection;
        this.matrix = matrix;
        this.cartographer = cartographer;
        this.selector = new SimpleSelector(cartographer);

    }

    public void process() {
//        initRenderer(renderer, size);
        Date start = new Date();
        logger.debug("Starting process (zoom " + matrix.getZoom()+")");
        init();
        logger.debug("datasources are loaded");
        TileCoord from = matrix.getTileStart();
        TileCoord to = matrix.getTileEnd();
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                processDataSource(new TileCoord(x, y));
            }
        }
        logger.debug("datasources are processed");
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                TileCoord current = new TileCoord(x, y);
                beforeTileRender(current);
                render(current);
                afterTileRender(current);
            }
        }


        logger.debug("processed in " + (new Date().getTime() - start.getTime()) / 3600);
    }

    private void processDataSource(TileCoord tile) {
        for (Datasource ds : cartographer.getDataSources()) {
            for (Element element : ds.getElements(tile)) {
                List<SelectedFigure> figures = selector.getItem(element, matrix.getZoom());
                for (SelectedFigure figure : figures) {
                    List<GeometryElement> geometries = figure.getFigure().drawElements(element, matrix.getZoom());
                    matrix.addGeometries(tile, figure.getLayer(), geometries);
                }
            }
        }
    }

    public void init() {
        for (Datasource ds : cartographer.getDataSources()) {
            ds.init(matrix.getDivision(), projection);
        }
    }

    public void release() {
    }

    private void render(TileCoord coord) {
        logger.debug("Rendering " + coord);
        GeometryCache cache = matrix.getGeometries();
        Collection<GeometryElementOnLayer> elements = cache.getElements(coord);
        for (GeometryElementOnLayer element : elements) {
            renderer.renderGeometry(element.getLayer(), element.getElement());
        }

        matrix.setTileState(coord, TileState.RENDERED);
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void setHeight(int height) {
        size.setHeight(height);
    }

    public int getWidth() {
        return size.getWidth();
    }

    public void setWidth(int width) {
        size.setWidth(width);
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    protected void beforeTileRender(TileCoord coord) {
        Box b = matrix.getDivision().getBox(coord);
        renderer.setClip(b.getCoordBox());
    }

    protected void afterTileRender(TileCoord coord) {
    }

    protected void initRenderer(Renderer renderer, Size size) {
        renderer.initSpace(size);
        renderer.setTransformation(new BaseTransformation(matrix.getBoundary().getCoordBox(), size.getWidth(), size.getHeight()));
    }

    public TileDivision getDivision() {
        return matrix.getDivision();
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
