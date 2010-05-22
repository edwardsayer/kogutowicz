package net.anzix.kogutowicz;

/**
 * Zoom object to define map details.
 * 
 * @author elek
 */
public class Zoom {

    /**
     * Drawing ratio.
     */
    private double ratio = 1;

    /**
     * OSM detail level.
     */
    private int level;

    /**
     * mapping scale.
     *
     * 10000 means 1:10 000.
     */
    private int scale;

    /**
     * Dpi to connect the scale and level value.
     */
    private int dpi = 300;

    public Zoom() {
    }

    public Zoom(int level) {
        this.level = level;
    }

    public static Zoom zoom(int zoom) {
        return new Zoom(zoom);
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zoom other = (Zoom) obj;
        if (this.level != other.level) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.level;
        return hash;
    }

    @Override
    public String toString() {
        return "zoom: " + level;
    }
}
