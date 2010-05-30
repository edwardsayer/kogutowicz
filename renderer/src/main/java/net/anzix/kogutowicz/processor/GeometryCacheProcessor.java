package net.anzix.kogutowicz.processor;

/**
 * Function work on a calculated geometry cache.
 *
 * @author elek
 */
public interface GeometryCacheProcessor {

    public void process(GeometryCache cache, RenderContext context);
}
