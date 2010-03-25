/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Icon with multpile source.
 *
 * @author elek
 */
public class Icon extends Point {

    private List<String> source = new ArrayList();

    public Icon(double x, double y) {
        super(x, y);
    }

    public Icon(double x, double y, String source) {
        super(x, y);
        this.source.add(source);
    }

    public Icon(Point point) {
        super(point);
    }

    public void addSource(String image) {
        source.add(image);
    }

    public List<String> getSource() {
        return source;
    }
}
