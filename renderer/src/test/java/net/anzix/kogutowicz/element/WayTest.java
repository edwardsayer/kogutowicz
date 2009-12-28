/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class WayTest {

    private Way testWay1;

    private Way testWay2;

    private Way testWay3;

    @Before
    public void init() {
        Node n1 = new Node(1, 1);
        Node n2 = new Node(1, 5);
        Node n3 = new Node(4, 1);
        testWay1 = new Way();
        testWay1.addNode(n1);
        testWay1.addNode(n2);
        testWay1.addNode(n3);
        testWay1.addNode(n1);

        Node n4 = new Node(11, 1);
        testWay2 = new Way();
        testWay2.addNode(n1);
        testWay2.addNode(n3);
        testWay2.addNode(n4);

        testWay3 = new Way();
        testWay3.addNode(new Node(1, 1));
        testWay3.addNode(new Node(3, 3));
        testWay3.addNode(new Node(5, 5));
        testWay3.addNode(new Node(11, 11));
    }

    public WayTest() {
    }

    @Test
    public void testGetLength() {
        assertEquals(12, testWay1.getLength(), 0.0001);
        assertEquals(10, testWay2.getLength(), 0.0001);
    }

    @Test
    public void testGetPositionAt() {
        assertEquals(new Node(1, 4), testWay1.getPositionAt(0.25));
        assertEquals(new Node(6, 1), testWay2.getPositionAt(0.5));
        assertEquals(new Node(9, 1), testWay2.getPositionAt(0.8));
        assertEquals(new Node(9, 1), testWay2.getPositionAt(0.8));
      
    }
}
