package net.anzix.kogutowicz.app;

import com.google.inject.Inject;
import java.io.File;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.decorator.MapRender;
import net.anzix.kogutowicz.decorator.RenderingWorkspace;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render one image block.
 *
 * @author elek
 */
@MetaInfServices
public class ImageMap implements MapApplication {

    @Inject
    @NotNull
    private RenderContext ctx;

    private Logger logger = LoggerFactory.getLogger(ImageMap.class);

    private File outputFile;

    @NotNull
    @Valid
    private Datasource datasource;

    private Integer zoom;

    private Double west;

    private Double east;

    private Double north;

    private Double south;

    private Boolean verbose = Boolean.FALSE;

    @NotNull
    private Projection inputProjection = new Mercator();

    @Valid
    private Renderer renderer;

    @NotNull
    @Valid
    private MapStyle mapStyle;

    private String output;

    @Inject
    private MapRender map;

    @Inject
    RenderContext context;

    @Override
    public void run() {

        Node tl = Node.valueOf(inputProjection, west, north);
        Node br = Node.valueOf(inputProjection, east, south);

        context.setTopLeft(tl);
        context.setBottomRight(br);
        Cartographer c = new Cartographer(datasource);
        context.setCartographer(c);
        context.setProjection(inputProjection);
        mapStyle.applyStyle(c);

        double aspect = Math.abs((tl.getLatitude() - br.getLatitude()) / (tl.getLongitude() - br.getLongitude()));
        int width = 800;

        int height = (int) Math.round(aspect * width);
        Size size = new Size(width, height);
        logger.debug("rendering map to size " + size);
        RenderingWorkspace workspace = new RenderingWorkspace(size, renderer);
        workspace.init();
        map.render(workspace);
        workspace.release();

//        RectangleTileDivision division = new RectangleTileDivision(tl, br, 10, 10);
//        ProcessMatrix testMatrix = new ProcessMatrix(tl, br, division, 10, 10);
//
//                QuadraticProcessor p = new QuadraticProcessor(inputProjection, testMatrix, c) {
//
//            @Override
//            protected void initRenderer(Renderer renderer, Size size) {
//                super.initRenderer(renderer, size);
//                renderer.initSpace(size);
//                BaseTransformation transf = new BaseTransformation(matrix.getBoundary().getCoordBox(), size.getWidth(), size.getHeight());
//                renderer.setTransformation(transf);
//                renderer.setClip(new CoordBox(30, 30, 100, 100));
//
//            }
//        };
//        p.setRenderer(renderer);
////        double aspect = Math.abs((tl.getLongitude() - br.getLongitude()) / (tl.getLatitude() - br.getLatitude()));
////        System.out.println(aspect);
////        p.setWidth(800);
////        p.setHeight((int) Math.round(aspect * p.getWidth()));
//        p.process();
//        p.release();
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public Double getEast() {
        return east;
    }

    public void setEast(Double east) {
        this.east = east;
    }

    public Double getNorth() {
        return north;
    }

    public void setNorth(Double north) {
        this.north = north;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public Double getSouth() {
        return south;
    }

    public void setSouth(Double south) {
        this.south = south;
    }

    public Boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    public Double getWest() {
        return west;
    }

    public void setWest(Double west) {
        this.west = west;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public MapStyle getMapStyle() {
        return mapStyle;
    }

    public void setMapStyle(MapStyle mapStyle) {
        this.mapStyle = mapStyle;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
