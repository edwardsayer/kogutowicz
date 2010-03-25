/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.config;

/**
 *
 * @author elek
 */
public class Child implements ChildInterface{
    private Double d;

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    @Override
    public void asd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
