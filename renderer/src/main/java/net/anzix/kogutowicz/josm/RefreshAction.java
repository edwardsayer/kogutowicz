/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.josm;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openstreetmap.josm.Main;

/**
 *
 * @author elek
 */
public class RefreshAction extends AbstractAction {

    private RenderLayer layer;

    public RefreshAction(RenderLayer layer) {
        this.layer = layer;
        putValue(SHORT_DESCRIPTION, "Repaint the current data.");
        putValue(NAME, "Repaint");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.map.mapView.repaint();
    }
}
