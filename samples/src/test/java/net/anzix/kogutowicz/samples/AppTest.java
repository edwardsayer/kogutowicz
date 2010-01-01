/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import net.anzix.kogutowicz.config.ConfigReader;

/**
 *
 * @author elek
 */
public class AppTest extends TestCase {

    /**
     * Test of main method, of class App.
     */
    public void testMain() throws Exception {
        List<Sample> samples = new ArrayList();

        samples.add(new Sample("nk", new File("src/samples/nk/nk.properties"), "Simple png map"));
        samples.add(new Sample("nk", new File("src/samples/remete/remete.properties"), "Simple png map"));

        for (Sample sample : samples) {
            new ConfigReader().start(sample.configFile);
        }
    }
}
