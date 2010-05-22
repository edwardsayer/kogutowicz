package net.anzix.kogutowicz;

/**
 * Trivial implementation of for projection.
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
