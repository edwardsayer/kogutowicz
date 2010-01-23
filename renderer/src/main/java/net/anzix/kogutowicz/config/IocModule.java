/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

import com.google.inject.Binder;
import com.google.inject.Module;
import net.anzix.kogutowicz.processor.RenderContext;

/**
 *
 * @author elek
 */
public class IocModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(RenderContext.class).asEagerSingleton();
    }
}
