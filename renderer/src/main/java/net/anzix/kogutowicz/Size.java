/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

/**
 *
 * @author elek
 */
public class Size {

    private int width;

    private int height;

    private int dpi = 300;

    /**
     * point per pixel
     */
    private int point = 1;

    public Size() {
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void multiplyResolution(double m) {
    }

    @Override
    public String toString() {
        return getWidth() + "x" + getHeight();
    }
}
