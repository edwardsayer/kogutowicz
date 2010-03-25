/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.datasource.Datasource;

/**
 * A map layer.
 *
 * Includes the concrete styles of elements.
 *
 * @author elek
 */
public class Layer {

    private static int globalWeight = 1;

    public static final Layer ZERO = new Layer(0);

    public static final Layer TOP = new Layer(100000);

    private int weight;

    private String name;    

    private List<Figure> figures = new ArrayList();

    private Datasource source;

    public Layer(String name) {
        this.name = name;
    }

    public Layer(int w) {
        this.weight = w;
    }

    public Layer() {
        this.weight = globalWeight++;
    }

    public <T extends Figure> T addFigure(T figure) {
        figures.add(figure);
        return figure;

    }

    @Override
    public String toString() {
        return "Layer " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    } 

    public Datasource getSource() {
        return source;
    }

    public void setSource(Datasource source) {
        this.source = source;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Layer other = (Layer) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.weight;
        return hash;
    }
}
