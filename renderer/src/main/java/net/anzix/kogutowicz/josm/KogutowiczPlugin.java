/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.josm;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.plugins.Plugin;

/**
 *
 * @author elek
 */
public class KogutowiczPlugin extends Plugin {

    public KogutowiczPlugin() {
        System.out.println("kogutowicz loading");
    }

    @Override
    public void mapFrameInitialized(MapFrame arg0, MapFrame arg1) {
        super.mapFrameInitialized(arg0, arg1);
        RenderLayer layer = new RenderLayer("Kogutowicz");
        Main.main.addLayer(layer);
        Main.map.mapView.repaint();
    }
}
