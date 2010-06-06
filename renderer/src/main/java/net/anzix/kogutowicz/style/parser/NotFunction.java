/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.Filter;
import net.anzix.kogutowicz.style.filter.NotFilter;

/**
 * NOT funciont as a praser element.
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
