package net.anzix.kogutowicz;

import java.io.File;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.processor.RenderContext;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.PngLabelFigure;
import net.anzix.kogutowicz.style.filter.TagFilter;

/**
 *
 * @author elek
 */
public class CollisionDetectorTest extends AbstratStyleTester {

    @Override
    public void initMap(InMemory mem) {
        TileCoord t = new TileCoord(0, 0);
        Way w = new Way();
        w.addNode(new Node(25, 25));
        w.addNode(new Node(75, 75));
        w.addTag("highway", "trunk");
        mem.addWay(w);

        for (int x = 0; x < 100; x += 8) {
            for (int y = 3; y < 100; y += 15) {
                Node n = new Node(x, y);
                n.addTag("amenity", "cafe");
                mem.addNode(n);
            }
        }



        Node n = new Node(25, 25);
        n.addTag("amenity", "cafe");
        mem.addNode(n);
    }

    @Override
    public void initStyles(Cartographer c) {
        Layer l = new Layer("base");
        c.addLayer(l);
        l.addFigure(new LineFigure().addFilter(new TagFilter("highway", "trunk")));
        l.addFigure(new PngLabelFigure().addFilter(new TagFilter("amenity", "cafe")).init("cafe.p.16"));
    }

    @Override
    public File getOutputFile() {
        return new File("target/collision.png");
    }

    @Override
    public TileDivision createTileDivision(RenderContext context) {
        return new RectangleTileDivision(context.getTopLeft(), context.getBottomRight(), 2, 2);
    }
}
