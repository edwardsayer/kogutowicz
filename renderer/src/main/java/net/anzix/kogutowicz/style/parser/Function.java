/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.Filter;

/**
 * Represents any function like parser element.
 *
 * @author elek
 */
public interface Function {

    public boolean is(String token);

    public Filter createFilter(Object... parameters);
}
