package net.anzix.kogutowicz.geometry;

/**
 * Text object.
 * 
 * @author elek
 */
public class Text extends GeometryElement {

    private CoordPair coords;

    private String text;

    @Override
    public CoordBox getBoundingBox() {
        return new CoordBox(coords, coords);
    }

    public CoordPair getCoords() {
        return coords;
    }

    public void setCoords(CoordPair coords) {
        this.coords = coords;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
}
