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

    public void test() throws Exception {
        File baseDir = new File("../samples/target/classes");
        new ConfigReader(new File(baseDir, "remete-svg.properties")).start();
//        new ConfigReader(new File("/home/elek/projects/kogutowicz.hg/test/hungary.properties")).start();
    }
}
