package net.anzix.kogutowicz.app;

import com.google.inject.Inject;
import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.QuadraticTileProcessor;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.style.Cartographer;

import net.anzix.kogutowicz.style.MapStyle;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render an area to OSM tiles.
 * 
 * @author elek
 */
@MetaInfServices
public class TilesMap implements MapApplication {

    private Logger logger = LoggerFactory.getLogger(MapApplication.class);

    @NotNull
    private File outputDir;

    @NotNull
    @Valid
    private Datasource datasource;

    private Integer zoom;

    private Integer startZoom;

    private Integer endZoom;

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
    private QuadraticTileProcessor processor;

    @Inject
    RenderContext context;

    @Override
    public void run() {
        if (startZoom == null) {
            startZoom = zoom;

        }
        if (endZoom == null) {
            endZoom = zoom;
        }

        Node n1 = Node.valueOf(projection, west, north);
        Node n2 = Node.valueOf(projection, east, south);
        Cartographer c = new Cartographer(datasource);
        mapStyle.applyStyle(c);
        context.setProjection(projection);
        context.setTopLeft(n1);
        context.setBottomRight(n2);
        context.setCartographer(c);
        processor.setOutputDir(outputDir);
        for (int z = startZoom; z <= endZoom; z++) {



            Zoom zZoom = Zoom.zoom(z);

            OSMTileDivision division = new OSMTileDivision(zZoom);
            context.setDivision(division);
            context.setZoom(zZoom);
            Java2DFileRenderer renderer = new Java2DFileRenderer();

            processor.setRenderer(renderer);
            processor.setWidth(256);
            processor.setHeight(256);
            processor.process();
            processor.release();
        }
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

    public MapStyle getMapStyle() {
        return mapStyle;
    }

    public void setMapStyle(MapStyle mapStyle) {
        this.mapStyle = mapStyle;
    }

    public Double getNorth() {
        return north;
    }

    public void setNorth(Double north) {
        this.north = north;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public Double getSouth() {
        return south;
    }

    public void setSouth(Double south) {
        this.south = south;
    }

    public Boolean getVerbose() {
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
}
