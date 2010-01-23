/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

import net.anzix.kogutowicz.app.MapApplication;

/**
 *
 * @author elek
 */
public class SampleApp implements MapApplication {

    private Parent parent;

    private SampleApp thiz;

    @Override
    public void run() {
        thiz = this;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public SampleApp getThiz() {
        return thiz;
    }

    public void setThiz(SampleApp thiz) {
        this.thiz = thiz;
    }

    
}
