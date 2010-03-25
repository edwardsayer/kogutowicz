/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.EqualProjection;
import net.anzix.kogutowicz.SimpleTileDivision;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.datasource.Datasource;
import net.anzix.kogutowicz.datasource.InMemory;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.renderer.SystemOutputRenderer;
import net.anzix.kogutowicz.style.Cartographer;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class QuadraticProcessorTest {

    public QuadraticProcessorTest() {
    }

    /**
     * Test of process method, of class QuadraticProcessor.
     */
    @Test
    public void testProcess() {
        //given
        Cartographer c = mock(Cartographer.class);


        RenderContext context = new RenderContext();
        context.setZoom(Zoom.zoom(14));
        context.setTopLeft(new Node(47, 19));
        context.setBottomRight(new Node(46, 20));
        context.setDivision(new SimpleTileDivision(Zoom.zoom(13), new Node(47, 19), new Node(46, 20)));
        QuadraticProcessor p = new QuadraticProcessor();
        p.setContext(context);
        context.setCartographer(c);
        p.setWidth(1000);
        p.setHeight(1000);
        p.setRenderer(new SystemOutputRenderer());

        InMemory m = new InMemory();
        m.add(new TileCoord(3, 3), new Way());
        List<Datasource> ds = new ArrayList();
        ds.add(m);

        when(c.getDataSources()).thenReturn(ds);
        //when

        p.process();
        assertNotNull(p);
        //result

    }
}
