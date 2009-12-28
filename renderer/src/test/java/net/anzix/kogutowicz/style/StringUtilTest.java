/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elek
 */
public class StringUtilTest {

    
    /**
     * Test of parseString method, of class StringUtil.
     */
    @Test
    public void testParseString() {
       assertEquals(10, StringUtil.parseString("0x0A"));
       assertEquals(10, StringUtil.parseString("10"));
       assertEquals(51966, StringUtil.parseString("0xCAFE"));
    }

}