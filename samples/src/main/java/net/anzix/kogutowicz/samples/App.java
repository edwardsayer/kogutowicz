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
        File baseDir = new File("target/classes");
        for (File file : baseDir.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        })) {
            new ConfigReader(file).start();
        }

    }
}
