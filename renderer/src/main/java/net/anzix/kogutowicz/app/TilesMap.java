package net.anzix.kogutowicz.app;

import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.FileOutputRenderer;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;

import net.anzix.kogutowicz.style.MapStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render an area to OSM tiles.
 * 
 * @author elek
 */
public class TilesMap implements MapApplication {

    private Logger logger = LoggerFactory.getLogger(MapApplication.class);

    @NotNull
    private File outputDir;

    @NotNull
    @Valid
    private DataSource datasource;

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

    @Override
    public void run() {
        Node n1 = Node.valueOf(projection, west, north);
        Node n2 = Node.valueOf(projection, east, south);
        Cartographer c = new Cartographer(datasource);
        mapStyle.applyStyle(c);

        OSMTileDivision division = new OSMTileDivision(Zoom.zoom(zoom));
        

        ProcessMatrix testMatrix = new ProcessMatrix(n1, n2, division, 10, 10);
        QuadraticProcessor p = new QuadraticProcessor(projection, testMatrix, c) {

            @Override
            protected void afterTileRender(TileCoord coord, Renderer renderer) {
                renderer.release();
            }

            @Override
            protected void beforeTileRender(TileCoord coord, Renderer renderer) {
                renderer.initSpace(256, 256);
                setTransformation(new BaseTransformation(getDivision().getBox(coord).getCoordBox(), 256, 256));
                String fileName = zoom + "/" + coord.getX() + "/" + coord.getY() + ".png";
                System.out.println(fileName);
                ((FileOutputRenderer) renderer).setOutputFile(new File(outputDir, fileName));

            }

            @Override
            protected void initRenderer(Renderer renderer) {
                //NOOP
            }

            @Override
            public void release() {
                //NOOP
            }
        };


        Java2DFileRenderer renderer = new Java2DFileRenderer();
        p.setRenderer(renderer);
        p.setWidth(256);
        p.setHeight(256);
        p.process();
        p.release();
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
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
