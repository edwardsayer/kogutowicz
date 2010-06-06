/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

import com.google.inject.Binder;
import com.google.inject.Module;
import net.anzix.kogutowicz.decorator.MapRender;
import net.anzix.kogutowicz.processor.GeometryCache;
import net.anzix.kogutowicz.processor.QuadraticProcessor;
import net.anzix.kogutowicz.processor.QuadraticTileProcessor;
import net.anzix.kogutowicz.processor.RenderContext;

/**
 * Guice injection definition.
 * 
 * @author elek
 */
public class IocModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(RenderContext.class).asEagerSingleton();
        binder.bind(QuadraticProcessor.class);
        binder.bind(QuadraticTileProcessor.class);
        binder.bind(MapRender.class);
        binder.bind(GeometryCache.class);
    }
}
