package net.anzix.kogutowicz.processor;

import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;

/**
 * Print out gc elements.
 * 
 * @author elek
 */
public class GeometryCachePrinter implements GeometryCacheProcessor {

    @Override
    public void process(GeometryCache cache, RenderContext context) {


        TileCoord from = context.getTileStart();
        TileCoord to = context.getTileEnd();
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                TileCoord tc = new TileCoord(x, y);
                System.out.println(x + "--" + y + "(" + context.getDivision().getBox(tc) + ")");
                for (GeometryElementOnLayer el : cache.getElements(tc)) {
                    System.out.println("{" + el.getLayer() + "}" + el.getElement().getClass() + " " + el.getElement().toString());
                }
            }
        }

    }
}
