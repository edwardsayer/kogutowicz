/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.app;

import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.RectangleTileDivision;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
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
    @Valid
    private MapStyle mapStyle;

    private String output;

    @Override
    public void run() {
        Node n1 = new Node(west, north);
        Node n2 = new Node(east, south);
        Cartographer c = new Cartographer(datasource);
        mapStyle.applyStyle(c);

        RectangleTileDivision division = new RectangleTileDivision(n1, n2, 10, 10);
        ProcessMatrix testMatrix = new ProcessMatrix(n1, n2, division, 10, 10);
        QuadraticProcessor p = new QuadraticProcessor(testMatrix, c);
        Java2DFileRenderer renderer = new Java2DFileRenderer();
        renderer.setOutputFile(new File(output));
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
}
