/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import net.anzix.kogutowicz.OSMTileDivision;
import net.anzix.kogutowicz.Tile;
import net.anzix.kogutowicz.Zoom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class ImageRenderListenerTest {

    @Test
    public void testGetDefaultWidthAndHeight() {
        int zoom = 1;
        OSMTileDivision div = new OSMTileDivision(new Zoom(14));
        
//        ProcessMatrix matrix = new ProcessMatrix(div.getTopLeftNode(), div.getBottomRight(null), new Zoom(zoom), 1, 1);
//        ImageRenderListener l = new ImageRenderListener();
//        assertEquals(256, l.getDefaultWidth(matrix));
//        assertEquals(256, l.getDefaultHeight(matrix));

    }
}
