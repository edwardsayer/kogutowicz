package net.anzix.kogutowicz.style;

/**
 *  Helper class to store figure with layer.
 * @author elek
 */
public class SelectedFigure {

    private Layer layer;

    private Figure figure;

    public SelectedFigure(Layer layer, Figure figure) {
        this.layer = layer;
        this.figure = figure;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }
}
