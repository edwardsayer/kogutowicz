/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.app;

import java.io.File;
import java.util.logging.Level;
import net.anzix.kogutowicz.App;
import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.Processor;
import net.anzix.kogutowicz.processor.TileRenderListener;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;

/**
 *
 * @author elek
 */
public class TilesMapInMemory implements Runnable {

	private File outputDirectory;

	private DataSource datasource;

	private Integer startZoom;

	private Integer stopZoom;

	private Double west;

	private Double east;

	private Double north;

	private Double south;

	private Boolean verbose = Boolean.FALSE;

    private MapStyle mapStyle;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Integer getStartZoom() {
		return startZoom;
	}

	public void setStartZoom(Integer startZoom) {
		this.startZoom = startZoom;
	}

	public Integer getStopZoom() {
		return stopZoom;
	}

	public void setStopZoom(Integer stopZoom) {
		this.stopZoom = stopZoom;
	}

	@Override
	public void run() {
		if (verbose) {
//			App.setLogLevel(Level.FINE);
		}
		Cartographer c = mapStyle.applyStyle(new Cartographer(datasource));

		for (int zoom = startZoom; zoom < stopZoom + 1; zoom++) {
			datasource.reset();
			TileDivision div = new OSMTileDivision(new Zoom(zoom));

			TileCoord startCoord = div.getTileCoord(new Node(west, north));
			TileCoord stopCoord = div.getTileCoord(new Node(east, south));
			System.out.println(startCoord + " " + stopCoord);
			Node start = div.getTopLeft(startCoord);
			Node stop = div.getBottomRight(stopCoord);
			int tilex = stopCoord.getX() - startCoord.getX() + 1;
			int tiley = stopCoord.getY() - startCoord.getY() + 1;//     
			ProcessMatrix matrix = new ProcessMatrix(start, stop, div, tilex, tiley);
			matrix.addListener(new TileRenderListener(outputDirectory, startCoord, zoom));

			Processor processor = new Processor(matrix, c);
			processor.process();
		}
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

	public Double getSouth() {
		return south;
	}

	public void setSouth(Double south) {
		this.south = south;
	}

	public Double getWest() {
		return west;
	}

	public void setWest(Double west) {
		this.west = west;
	}

    public Boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    public MapStyle getMapStyle() {
        return mapStyle;
    }

    public void setMapStyle(MapStyle mapStyle) {
        this.mapStyle = mapStyle;
    }

    
}
