/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.josm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.datasource.DataSource;
import net.anzix.kogutowicz.element.Element;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;

/**
 *
 * @author elek
 */
public class JosmDataSource implements DataSource {

    private DataSet datas;
    private TileDivision div;

    public JosmDataSource(DataSet data) {
        datas = data;
    }

    @Override
    public void init(TileDivision div) {
    }

    @Override
    public void reset() {
    }

    @Override
    public Collection<Element> getElements(TileCoord c) {
        List<Element> result = new ArrayList();
        for (Way way : datas.ways) {
            net.anzix.kogutowicz.element.Way w = new net.anzix.kogutowicz.element.Way();
            for (Node node : way.nodes) {
                net.anzix.kogutowicz.element.Node n = new net.anzix.kogutowicz.element.Node();
                LatLon ll = node.getCoor();
                n.setLatitude(ll.getY());
                n.setLongitude(ll.getX());
                for (Entry<String, String> entry : way.entrySet()) {
                    w.addTag(entry.getKey(), entry.getValue());
                }
                w.addNode(n);
            }
            result.add(w);
        }
        return result;
    }

    
}
