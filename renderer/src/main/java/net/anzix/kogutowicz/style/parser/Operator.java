/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.Filter;

/**
 * Operator like parser elements.
 * @author elek
 */
public interface Operator {

    public boolean is(String token);

    public Filter createFilter(Object... parameters);
    
    public double getPrecendece();

}
