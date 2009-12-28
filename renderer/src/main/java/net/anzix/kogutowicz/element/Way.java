/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.element;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Geometry;

/**
 * Element represents an OSM Way object.
 * 
 * @author elek
 */
public class Way extends Element {

    private List<Node> nodes = new ArrayList();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public boolean isIntersect(Box box) {

        for (int i = 0; i < nodes.size() - 1; i++) {
            Node prev = nodes.get(i);
            Node next = nodes.get(i + 1);
            if (Geometry.isLineIntersectingRectangle(
                    prev.getLongitude(), prev.getLatitude(),
                    next.getLongitude(), next.getLatitude(),
                    box.getTopLeft().getLongitude(), box.getTopLeft().getLatitude(),
                    box.getBottomRight().getLongitude(), box.getBottomRight().getLatitude())) {
                return true;
            }
            if (Geometry.isPointInsideRectangle(
                    box.getTopLeft().getLongitude(), box.getBottomRight().getLatitude(),
                    box.getBottomRight().getLongitude(), box.getTopLeft().getLatitude(),
                    nodes.get(i).getLongitude(), nodes.get(i).getLatitude())) {

                return true;
            }
        }
        //it is a polygon
        if (nodes.get(0).equals(nodes.get(nodes.size() - 1))) {
            double px[] = new double[nodes.size()];
            double py[] = new double[nodes.size()];
            for (int i = 0; i < nodes.size(); i++) {
                px[i] = nodes.get(i).getLatitude();
                py[i] = nodes.get(i).getLongitude();
            }
            if (Geometry.isPointInsidePolygon(px, py, box.getTopLeft().getLatitude(), box.getTopLeft().getLongitude())) {
                return true;
            }
            if (Geometry.isPointInsidePolygon(px, py, box.getTopLeft().getLatitude(), box.getBottomRight().getLongitude())) {
                return true;
            }
            if (Geometry.isPointInsidePolygon(px, py, box.getBottomRight().getLatitude(), box.getTopLeft().getLongitude())) {
                return true;
            }
            if (Geometry.isPointInsidePolygon(px, py, box.getBottomRight().getLatitude(), box.getBottomRight().getLongitude())) {
                return true;
            }
        }
        return false;
    }

    public double getLength() {
        double sum = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            sum += nodes.get(i).getDistanceFrom(nodes.get(i + 1));
        }
        return sum;
    }

    public Node getPositionAt(double percent) {
        double length = getLength();
        double scaledLength = length * percent;
        for (int i = 0; i < nodes.size() - 1; i++) {
            double subLength = nodes.get(i).getDistanceFrom(nodes.get(i + 1));
            if (subLength < scaledLength) {
                scaledLength -= subLength;
            } else {
                double vscale = scaledLength / subLength;
                double nLat = nodes.get(i + 1).getLatitude() - nodes.get(i).getLatitude();
                double nLon = nodes.get(i + 1).getLongitude() - nodes.get(i).getLongitude();
                return new Node(nodes.get(i).getLongitude() + nLon * vscale, nodes.get(i).getLatitude() + nLat * vscale);
            }
        }
        return null;
    }

    public Node getHalfPosition() {
        return getPositionAt(0.5);
    }
}
