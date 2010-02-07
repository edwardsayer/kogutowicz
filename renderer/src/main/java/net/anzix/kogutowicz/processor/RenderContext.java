package net.anzix.kogutowicz.processor;

import java.io.File;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.element.Node;
import net.anzix.kogutowicz.style.Cartographer;

/**
 *Context values for the rendering.
 *
 * @author elek
 */
public class RenderContext {

    private TileDivision division;

    private Node topLeft;

    private Node bottomRight;

    private Projection projection;

    private File basedir;

    private Size size = new Size();

    private Zoom zoom = Zoom.zoom(14);

    private Cartographer cartographer;

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

    public Node getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Node bottomRight) {
        this.bottomRight = bottomRight;
    }

    public TileDivision getDivision() {
        return division;
    }

    public void setDivision(TileDivision division) {
        this.division = division;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Node getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Node topLeft) {
        this.topLeft = topLeft;
    }

    public Zoom getZoom() {
        return zoom;
    }

    public void setZoom(Zoom zoom) {
        this.zoom = zoom;
    }

    public Box getBoundary() {
        return new Box(topLeft, bottomRight);
    }

    public TileCoord getTileStart() {
        return division.getTileCoord(topLeft);
    }

    public TileCoord getTileEnd() {
        return division.getTileCoord(bottomRight);
    }

    public Cartographer getCartographer() {
        return cartographer;
    }

    public void setCartographer(Cartographer cartographer) {
        this.cartographer = cartographer;
    }
}
