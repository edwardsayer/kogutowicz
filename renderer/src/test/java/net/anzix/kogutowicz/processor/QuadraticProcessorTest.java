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
import net.anzix.kogutowicz.datasource.DataSource;
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
        ProcessMatrix matrix = mock(ProcessMatrix.class);
        
        

        when(matrix.getTileStart()).thenReturn(new TileCoord(2, 2));
        when(matrix.getTileEnd()).thenReturn(new TileCoord(3, 3));
        when(matrix.getProjecion()).thenReturn(new EqualProjection());
        when(matrix.getBoundary()).thenReturn(new Box(47, 19, 46, 20));
        when(matrix.getDivision()).thenReturn(new SimpleTileDivision(Zoom.zoom(13), new Node(47,19), new Node(46,20)));
        when(matrix.getGeometries()).thenReturn(new GeometryCache(matrix));

        QuadraticProcessor p = new QuadraticProcessor(matrix, c);
        p.setWidth(1000);
        p.setHeight(1000);
        p.setRenderer(new SystemOutputRenderer());

        InMemory m = new InMemory();
        m.add(new TileCoord(3, 3), new Way());
        List<DataSource> ds = new ArrayList();
        ds.add(m);

        when(c.getDataSources()).thenReturn(ds);
        //when

        p.process();
        assertNotNull(p);
        //result

    }

  
}
