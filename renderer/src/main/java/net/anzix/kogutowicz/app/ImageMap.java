package net.anzix.kogutowicz.app;

import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.Renderer;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;

/**
 * Render one image block.
 *
 * @author elek
 */
public class ImageMap implements MapApplication {

    private File outputFile;

    @NotNull
    @Valid
    private DataSource datasource;

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

    @Override
    public void run() {

        Node tl = Node.valueOf(inputProjection, west, north);
        Node br = Node.valueOf(inputProjection, east, south);

        Cartographer c = new Cartographer(datasource);
        mapStyle.applyStyle(c);

        RectangleTileDivision division = new RectangleTileDivision(tl, br, 10, 10);
        ProcessMatrix testMatrix = new ProcessMatrix(tl, br, division, 10, 10);
        QuadraticProcessor p = new QuadraticProcessor(inputProjection, testMatrix, c);
        p.setRenderer(renderer);
        double aspect = Math.abs((tl.getLongitude() - br.getLongitude()) / (tl.getLatitude() - br.getLatitude()));
        System.out.println(aspect);
        p.setWidth(800);
        p.setHeight((int) Math.round(aspect * p.getWidth()));
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
