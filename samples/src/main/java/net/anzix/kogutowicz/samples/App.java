package net.anzix.kogutowicz.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.config.ConfigReader;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        List<Sample> samples = new ArrayList();

        //samples.add(new Sample("nk", new File("src/samples/nk/nk.properties"), "Simple png map"));
        samples.add(new Sample("nk", new File("src/samples/remete/remete.properties"), "Simple png map"));

        for (Sample sample : samples) {
            new ConfigReader().start(sample.configFile);
        }

    }

    private static class Sample {

        public Sample() {
        }

        public Sample(File configFile, String description) {
            this.configFile = configFile;
            this.description = description;
        }

        public Sample(String id, File configFile, String description) {
            this.id = id;
            this.configFile = configFile;
            this.description = description;
        }
        protected String id;

        protected File configFile;

        protected String description;

    }
}
