package net.anzix.kogutowicz;

import java.io.File;


import net.anzix.kogutowicz.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: java -jar kogutowicz.jar config.properties");
        } else {
            logger.info("Start rendering");
            File propFile = new File(args[0]);
            new ConfigReader(propFile).start();
        }

    }
}
