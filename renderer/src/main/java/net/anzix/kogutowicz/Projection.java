/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

/**
 * Simple projection interface to convert between WGS-84 and any other projection.
 * @author elek
 */
public abstract class Projection {

    public double[] getProjected(double lon, double lat) {
        return getXY(lon, lat);
    }

    public abstract double[] getXY(double lon, double lat);

    public abstract double[] getLanLon(double x, double y);
}

