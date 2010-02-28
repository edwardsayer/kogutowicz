/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.it;

import java.io.File;
import net.anzix.kogutowicz.config.ConfigReader;
import org.junit.Test;

/**
 *
 * @author elek
 */
public class ImageTest {
 
    @Test
    public void test() throws Exception {
//        new ConfigReader(new File("src/it/remete-svg.properties")).start();
        new ConfigReader(new File("src/it/remete-pdf.properties")).start();
//        new ConfigReader(new File("src/it/nk.properties")).start();
//        new ConfigReader(new File("src/it/mapnik.properties")).start();
//        new ConfigReader(new File("/home/elek/projects/kogutowicz.hg/test/hungary.properties")).start();
    }
}
