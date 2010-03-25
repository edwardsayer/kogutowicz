/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz;

/**
 *
 * @author elek
 */
public class EqualProjection extends Projection {

    @Override
    public double[] getXY(double lon, double lat) {
        return new double[]{lon, lat};
    }

    @Override
    public double[] getLanLon(double x, double y) {
        return new double[]{x, y};
    }
}
