package net.anzix.kogutowicz.samples;

import java.io.File;
import java.io.FilenameFilter;
import net.anzix.kogutowicz.config.ConfigReader;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        //to project wide build
        File baseDir = new File("samples/target/classes");
        if (!baseDir.exists()) {
            baseDir = new File("target/classes");
        }

        for (File file : baseDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties") && !name.contains("MANUAL");
            }
        })) {
            new ConfigReader(file).start();
        }

    }
}
