/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.element.Element;

/**
 * The cartographer who know the styles of the map.
 *
 * @author elek
 */
public class Cartographer {

    public static final String AUTO_NAME = "layer";

    private List<Layer> layers = new ArrayList();

    private List<Datasource> dataSources = new ArrayList();

    private Datasource defaultDataSource;

    public Cartographer(Datasource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
        dataSources.add(defaultDataSource);
    }

    public void addLayer(Layer l) {
        addLayer(l.getName(), l);
    }

    public Layer addLayer(String name, Layer layer) {
        layers.add(layer);
        layer.setName(name);
        if (layer.getSource() == null) {
            layer.setSource(defaultDataSource);
        } else {
            if (!dataSources.contains(layer.getSource())) {
                dataSources.add(layer.getSource());
            }
        }
        return layer;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public Layer getLayer(String str) {
        for (Layer layer : layers) {
            if (layer.getName().equals(str)) {
                return layer;
            }
        }
        return null;
    }

    public Layer createLayer(String name) {
        Layer l = new Layer();
        addLayer(name, l);
        return l;
    }

    public List<Datasource> getDataSources() {
        return dataSources;
    }
}
