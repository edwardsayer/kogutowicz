package net.anzix.kogutowicz;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main CLI entry point.
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
            ClassLoader loader = loadExtensions();
            ConfigReader reader = new ConfigReader(propFile,loader);

            reader.start();

        }

    }

    private static ClassLoader loadExtensions() {
        logger.debug("finding extensions");
        String homeDir = System.getProperty("user.home");
        File home = new File(homeDir);
        File settignsDir = new File(home, ".kogutowicz");
        File pluginsDir = new File(settignsDir, "plugins");
        logger.debug("finding plugins at " + pluginsDir.getAbsolutePath());

        List<URL> urls = new ArrayList();
        if (pluginsDir.exists()) {
            try {
                for (File file : pluginsDir.listFiles(new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".jar");
                    }
                })) {
                    try {
                        urls.add(file.toURI().toURL());
                    } catch (MalformedURLException ex) {
                        logger.error("Mailformed url {}", file.getAbsolutePath(), ex);
                    }
                }
                URLClassLoader loader = URLClassLoader.newInstance(urls.toArray(new URL[0]), App.class.getClassLoader());                
                logger.info("loaded plugins: {}", urls.toArray(new URL[0]));
                return loader;
            } catch (Exception ex) {
                throw new RenderException("Error on loading plugins", ex);
            }
        }
        return App.class.getClassLoader();

    }
}
