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
public class TilesTest {

    @Test
    public void test() throws Exception {
        new ConfigReader().start(new File("src/it/remete-tiles.properties"));

    }
}
