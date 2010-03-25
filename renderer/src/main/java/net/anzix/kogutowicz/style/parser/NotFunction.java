/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.Filter;
import net.anzix.kogutowicz.style.NotFilter;

/**
 *
 * @author elek
 */
public class NotFunction implements Function{

    @Override
    public boolean is(String token) {
        return token.equalsIgnoreCase("not");
    }

    @Override
    public Filter createFilter(Object... parameters) {
        return new NotFilter((Filter)parameters[0]);
    }

}
