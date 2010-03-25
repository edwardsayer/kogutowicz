/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class NodeTest {

    public NodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetDistanceFrom() {
        Node n1 = new Node(1, 1);
        Node n2 = new Node(1, 5);
        Node n3 = new Node(4, 1);
        assertEquals(4, n1.getDistanceFrom(n2), 0.001);
        assertEquals(3, n1.getDistanceFrom(n3), 0.001);
        assertEquals(5, n2.getDistanceFrom(n3), 0.001);

    }
}
