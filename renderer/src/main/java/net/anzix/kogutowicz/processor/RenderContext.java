package net.anzix.kogutowicz.processor;

import java.io.File;
import net.anzix.kogutowicz.Projection;

/**
 *Context values for the rendering.
 *
 * @author elek
 */
public class RenderContext {

    private Projection projection;

    private File basedir;
    

    public File getBasedir() {
        return basedir;
    }

    public void setBasedir(File basedir) {
        this.basedir = basedir;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public File getAbsolutePath(String path) {
        File t = new File(path);
        if (t.isAbsolute()) {
            return t;
        } else {
            return new File(basedir, path);
        }

    }
}
