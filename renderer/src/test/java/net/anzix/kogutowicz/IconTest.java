/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.PngLabelFigure;
import net.anzix.kogutowicz.style.filter.TagFilter;

/**
 *
 * @author elek
 */
public class IconTest extends AbstratStyleTester {

    @Override
    public void initMap(InMemory mem) {
        TileCoord t = new TileCoord(0, 0);
//        Way w = new Way();
//        w.addNode(new Node(25, 25));
//        w.addNode(new Node(50, 50));
//        w.addTag("highway", "trunk");
        Node n = new Node(50, 50);
        n.addTag("amenity", "cafe");
        mem.add(t, n);
//        mem.add(t, w);
    }

    @Override
    public void initStyles(Cartographer c) {
        Layer l = new Layer("base");
        c.addLayer(l);
//        l.addFigure(new LineFigure().addFilter(new TagFilter("highway", "trunk")));
        l.addFigure(new PngLabelFigure().addFilter(new TagFilter("amenity", "cafe")).init("cafe.p.16"));
    }
}
