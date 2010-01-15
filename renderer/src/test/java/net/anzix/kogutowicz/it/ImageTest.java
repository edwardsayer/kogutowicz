/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.it;

import java.io.File;
import org.junit.Test;
import net.anzix.kogutowicz.config.ConfigReader;
/**
 *
 * @author elek
 */
public class ImageTest {

    @Test
    public void test() throws Exception{
//                new ConfigReader(new File("src/it/remete-svg.properties")).start();
        new ConfigReader(new File("src/it/remete.properties")).start();
//        new ConfigReader(new File("src/it/nk.properties")).start();

    }
}
