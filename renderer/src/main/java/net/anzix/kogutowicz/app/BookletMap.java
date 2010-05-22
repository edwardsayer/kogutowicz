package net.anzix.kogutowicz.app;

import com.google.inject.Inject;
import java.io.File;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.processor.GeometryCache;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.FileOutputRenderer;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;
import net.anzix.kogutowicz.style.SelectedFigure;
import net.anzix.kogutowicz.style.SimpleSelector;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render a book style atlas (eg. road atlas).
 * @author elek
 */
@MetaInfServices
public class BookletMap implements MapApplication {

    private Renderer renderer;

    private Size size;

    private Logger logger = LoggerFactory.getLogger(MapApplication.class);

    @NotNull
    private File outputDir;

    @NotNull
    @Valid
    private Datasource datasource;

    private Integer zoom;

    @NotNull
    private Double west;

    @NotNull
    private Double east;

    @NotNull
    private Double north;

    @NotNull
    private Double south;

    private Boolean verbose = Boolean.FALSE;

    @Valid
    @NotNull
    private MapStyle mapStyle;

    private Projection projection = new Mercator();

    @Inject
    RenderContext context;

    private SimpleSelector selector = new SimpleSelector();

    private GeometryCache geoms = new GeometryCache();

    private int i = 0;

    @Override
    public void run() {
        renderer = new Java2DFileRenderer();
        //renderer = new SystemOutputRenderer();
        System.out.println(context.getBasedir());
        size = new Size(424, 600);
        int noOfX = 10;
        context.setZoom(new Zoom(10));
        Node tl = Node.valueOf(projection, west, north);
        Node br = Node.valueOf(projection, east, south);


        System.out.println(tl);
        System.out.println(br);

        context.setTopLeft(tl);
        context.setBottomRight(br);

        Cartographer c = new Cartographer(datasource);
        context.setCartographer(c);
        mapStyle.applyStyle(c);

        context.setProjection(projection);

        double projectedxsize = (br.getLongitude() - tl.getLongitude()) / noOfX;
        double projectedysize = (projectedxsize * size.getHeight()) / size.getWidth();
        System.out.println(projectedxsize);
        double ysize = (tl.getLatitude() - br.getLatitude()) / projectedxsize;
        System.out.println(ysize);
        System.out.println(projectedxsize);
        System.out.println(projectedysize);
        int noOfY = (int) Math.floor(ysize);
        br.setLatitude(tl.getLatitude() - noOfY * projectedysize);
        context.setDivision(new RectangleTileDivision(tl, br, noOfX, noOfY));




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
        for (int y = from.getY(); y <= to.getY(); y++) {
            for (int x = from.getX(); x <= to.getX(); x++) {
                TileCoord current = new TileCoord(x, y);
                beforeTileRender(current);
                render(current);
                afterTileRender(current);
            }
        }
    }

    protected void beforeTileRender(TileCoord coord) {
        renderer.initSpace(size);
        renderer.setTransformation(new BaseTransformation(context.getDivision().getBox(coord).getCoordBox(), size.getWidth(), size.getHeight()));
        if (renderer instanceof FileOutputRenderer) {
            ((FileOutputRenderer) renderer).setOutputFile(new File(context.getBasedir(), "atlas" + i++ + ".png"));
        }
        Box b = context.getDivision().getBox(coord);
        renderer.setClip(b.getCoordBox());
    }

    protected void afterTileRender(TileCoord coord) {
        renderer.release();
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
}
