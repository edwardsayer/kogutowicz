package net.anzix.kogutowicz;

import com.jhlabs.map.proj.ProjectionFactory;

/**
 * Standard Mercator projection used by Open Street Map.
 * @author elek
 */
public class Mercator extends Projection {

    com.jhlabs.map.proj.Projection pr = ProjectionFactory.fromPROJ4Specification(
            new String[]{
                "+proj=merc",
                "+a=6378137",
                "+b=6378137",
                "+lat_ts=0.0",
                "+lon_0=0.0",
                "+x_0=0.0",
                "+y_0=0",
                "+k=1.0",
                "+units=m",
                "+nadgrids=@null",
                "+no_defs"
            });

    @Override
    public double[] getXY(double lon, double lat) {
        return new double[]{mercX(lon), mercY(lat)};
//        Point2D.Double source = new Point2D.Double(lon, lat);
//        Point2D.Double dest = new Point2D.Double();
//        pr.inverseTransform(source, dest);
//        return new double[]{dest.getX(), dest.getY()};
    }

    @Override
    public double[] getLanLon(double x, double y) {
        return new double[]{mercLon(x), mercLat(y)};

    }
    final private static double R_MAJOR = 6378137.0;

    final private static double R_MINOR = 6356752.3142;

    double temp = R_MINOR / R_MAJOR;

    double es = 1.0 - (temp * temp);

    double eccent = Math.sqrt(es);

    double com = 0.5 * eccent;

    private double mercX(double lon) {
        return R_MAJOR * Math.toRadians(lon);
    }

    private double mercLon(double x) {
        return Math.toDegrees(x) / R_MAJOR;
    }

    private double mercLat(double y) {
        double ts = Math.exp(-y / R_MAJOR);
        double phi = Math.PI / 2 - 2 * Math.atan(ts);
        double dphi = 1.0;
        int i = 0;
        while ((Math.abs(dphi) > 0.000000001) && (i < 15)) {
            double con = eccent * Math.sin(phi);
            dphi = Math.PI / 2 - 2 * Math.atan(ts * Math.pow((1.0 - con) / (1.0 + con), com)) - phi;
            phi += dphi;
            i++;
        }
        return Math.toDegrees(phi);
    }

    private double mercY(double lat) {
        if (lat > 89.5) {
            lat = 89.5;
        }
        if (lat < -89.5) {
            lat = -89.5;
        }
        double phi = Math.toRadians(lat);
        double sinphi = Math.sin(phi);
        double con = eccent * sinphi;
        con = Math.pow(((1.0 - con) / (1.0 + con)), com);
        double ts = Math.tan(0.5 * ((Math.PI * 0.5) - phi)) / con;
        return 0 - R_MAJOR * Math.log(ts);
    }
}
