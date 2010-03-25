package net.anzix.kogutowicz.processor;

import com.google.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Renderer;
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

    private SimpleSelector selector;

    private Renderer renderer;

    @Inject
    protected RenderContext context;

    private GeometryCache geoms = new GeometryCache();

    private Size size;

    public QuadraticProcessor() {
        this.selector = new SimpleSelector();

    }

    public void process() {
        if (size == null) {
            size = context.getSize();
        }
        initRenderer(renderer, size);
        Date start = new Date();
        logger.debug("Starting process (zoom " + context.getZoom() + ")");
        init();
        logger.debug("datasources are loaded");
        TileCoord from = context.getTileStart();
        TileCoord to = context.getTileEnd();
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
        for (Datasource ds : context.getCartographer().getDataSources()) {
            for (Element element : ds.getElements(tile)) {
                List<SelectedFigure> figures = selector.getItem(context.getCartographer(), element, context.getZoom());
                for (SelectedFigure figure : figures) {
                    List<GeometryElement> geometries = figure.getFigure().drawElements(element, context.getZoom());
                    geoms.addGeometries(tile, figure.getLayer(), geometries);
                }
            }
        }
    }

    public void init() {
        for (Datasource ds : context.getCartographer().getDataSources()) {
            ds.init(context.getDivision(), context.getProjection());
        }
    }

    public void release() {
    }

    private void render(TileCoord coord) {
        logger.debug("Rendering " + coord);
        Collection<GeometryElementOnLayer> elements = geoms.getElements(coord);
        for (GeometryElementOnLayer element : elements) {
            renderer.renderGeometry(element.getLayer(), element.getElement());
        }
    }

    public int getHeight() {
        if (size == null) {
            size = new Size();
        }
        return size.getHeight();
    }

    public void setHeight(int height) {
        if (size == null) {
            size = new Size();
        }
        size.setHeight(height);
    }

    public int getWidth() {
        if (size == null) {
            size = new Size();
        }
        return size.getWidth();
    }

    public void setWidth(int width) {
        if (size == null) {
            size = new Size();
        }
        size.setWidth(width);
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    protected void beforeTileRender(TileCoord coord) {
        Box b = context.getDivision().getBox(coord);
        renderer.setClip(b.getCoordBox());
    }

    protected void afterTileRender(TileCoord coord) {
    }

    protected void initRenderer(Renderer renderer, Size size) {
        renderer.initSpace(size);
        renderer.setTransformation(new BaseTransformation(context.getBoundary().getCoordBox(), size.getWidth(), size.getHeight()));
    }

    public TileDivision getDivision() {
        return context.getDivision();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public RenderContext getContext() {
        return context;
    }

    public void setContext(RenderContext context) {
        this.context = context;
    }
}
