/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.it;

import java.io.File;
import net.anzix.kogutowicz.config.ConfigReader;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author elek
 */
public class ImageTest {

    @Test
@Ignore
    public void test() throws Exception {
        File baseDir = new File("../samples/target/classes");
        new ConfigReader(new File(baseDir, "hungary-atlas.properties")).start();
//        new ConfigReader(new File("/home/elek/projects/kogutowicz.hg/test/hungary.properties")).start();
    }
}
