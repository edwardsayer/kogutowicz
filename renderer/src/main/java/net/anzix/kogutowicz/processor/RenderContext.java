package net.anzix.kogutowicz.processor;

import java.io.File;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.Zoom;

/**
 *Context values for the rendering.
 *
 * @author elek
 */
public class RenderContext {

    private Projection projection;

    private File basedir;

    private Size size;

    private Zoom zoom;

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
