package net.anzix.kogutowicz.style;

/**
 * Base map style definition interface.
 *
 * @author elek
 */
public interface MapStyle {

    public Cartographer applyStyle(Cartographer simpleMap);
}
