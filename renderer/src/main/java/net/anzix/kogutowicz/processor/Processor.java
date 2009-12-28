/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Element;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.SelectedFigure;
import net.anzix.kogutowicz.style.SimpleSelector;

/**
 * Main controller.
 *
 * Iterate over the tiles and process the datas from the datasource.
 * @author elek
 */
@Deprecated
public class Processor {

    private Logger logger = Logger.getLogger(Processor.class.getCanonicalName());

    private ProcessMatrix matrix;

    private Cartographer cartographer;

    private SimpleSelector selector;

    public Processor(ProcessMatrix matrix, Cartographer cartographer) {
        this.matrix = matrix;
        this.cartographer = cartographer;
        this.selector = new SimpleSelector(cartographer);

    }

    public void process() {
        Date start = new Date();
        logger.finer("Starting process.");
        init();
        TileCoord from = matrix.getTileStart();
        TileCoord to = matrix.getTileEnd();
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                TileCoord tile = new TileCoord(x, y);
                processDataSource(tile);
                matrix.setTileState(tile, TileState.PROCESSED);
            }
        }
        release();
        logger.finer("processed in " + (new Date().getTime() - start.getTime()) / 3600);
    }

    private void processDataSource(TileCoord tile) {
        for (DataSource ds : cartographer.getDataSources()) {
            for (Element element : ds.getElements(tile)){
                List<SelectedFigure> figures = selector.getItem(element, matrix.getZoom());
                for (SelectedFigure figure : figures) {
                    List<GeometryElement> geometries = figure.getFigure().drawElements(matrix.getProjecion(), element, matrix.getZoom());
                    matrix.addGeometries(tile, figure.getLayer(), geometries);
                }
            }
        }
    }

    private void init() {
        for (DataSource ds : cartographer.getDataSources()) {
            ds.init(matrix.getDivision());
        }
        matrix.initListeners();
    }

    public void release() {
        matrix.releaseListeners();
    }
}
