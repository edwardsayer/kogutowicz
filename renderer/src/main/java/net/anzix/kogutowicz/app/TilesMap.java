package net.anzix.kogutowicz.app;

import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.FileOutputRenderer;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;

import net.anzix.kogutowicz.style.MapStyle;

/**
 * Render an area to OSM tiles.
 * 
 * @author elek
 */
public class TilesMap implements MapApplication {

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

    @Override
    public void run() {
        Node n1 = new Node(west, north);
        Node n2 = new Node(east, south);
        Cartographer c = new Cartographer(datasource);
        mapStyle.applyStyle(c);

        OSMTileDivision division = new OSMTileDivision(Zoom.zoom(zoom));
        ProcessMatrix testMatrix = new ProcessMatrix(n1, n2, division, 10, 10);
        QuadraticProcessor p = new QuadraticProcessor(testMatrix, c) {

            @Override
            protected void afterTileRender(TileCoord coord, Renderer renderer) {
                renderer.release();

            }

            @Override
            protected void beforeTileRender(TileCoord coord, Renderer renderer) {
                renderer.initSpace(256, 256);
                setTransformation(new BaseTransformation(CoordBox.projectFromBox(matrix.getProjecion(), getDivision().getBox(coord)), 256, 256));
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
        double[] from = testMatrix.getProjecion().getProjected(n1.getLongitude(), n1.getLatitude());
        double[] to = testMatrix.getProjecion().getProjected(n2.getLongitude(), n2.getLatitude());
        double aspect = Math.abs((from[0] - to[0]) / (from[1] - to[1]));
        p.setWidth(800);
        p.setHeight((int) Math.round(aspect * 800));
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
