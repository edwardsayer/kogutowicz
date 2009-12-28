/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elek
 */
public class Java2DSimpleRenderer extends AbstractJava2DRenderer {

    public Java2DSimpleRenderer(Graphics2D g) {
        setGraphics(g);
    }

    @Override
    public void initSpace(double width, double heght) {
//  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER));
        Map map = new HashMap();
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        getGraphics().setRenderingHints(map);
        getGraphics().setPaint(new Color(0xcc,0xcc,0xcc));
        getGraphics().fillRect(0, 0, round(width), round(heght));
        getGraphics().setPaint(Color.black);


    }

    @Override
    public void release() {
    }
}
