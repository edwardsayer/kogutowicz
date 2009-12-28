/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.app;

import java.io.File;
import net.anzix.kogutowicz.SimpleTileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.ImageRenderFileListener;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.Processor;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;

/**
 *
 * @author elek
 */
public class ImageMap implements Runnable {

    private File outputFile;

    private DataSource datasource;

    private Integer zoom;

    private Double west;

    private Double east;

    private Double north;

    private Double south;

    private Boolean verbose = Boolean.FALSE;

    private MapStyle mapStyle;

    @Override
    public void run() {
//        OSMTileDivision odiv = new OSMTileDivision(new Zoom(14));
//        TileCoord coord = new TileCoord(9054,5725);
//        west = odiv.getTopLeft(coord).getLongitude();
//        east = odiv.getBottomRight(coord).getLongitude();
//
//        north = odiv.getTopLeft(coord).getLatitude();
//        south = odiv.getBottomRight(coord).getLatitude();

        Box box = new Box(new Node(west, north), new Node(east, south));
        SimpleTileDivision div = new SimpleTileDivision(new Zoom(14), box.getTopLeft(), box.getBottomRight());

        ProcessMatrix matrix = new ProcessMatrix(box, div, 1, 1);

        Cartographer c = mapStyle.applyStyle(new Cartographer(datasource));

        int wp = (int) Math.round(Math.abs(east - west) / (360 / Math.pow(2, zoom)) * 256);
        int hp = (int) Math.round(Math.abs(north - south) / (121 * 2 / Math.pow(2, zoom)) * 256);
        System.out.println(wp + " " + hp);
        ImageRenderFileListener lis = new ImageRenderFileListener(outputFile, wp, hp);
        matrix.addListener(lis);
        Processor processor = new Processor(matrix, c);
        processor.process();
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
}
