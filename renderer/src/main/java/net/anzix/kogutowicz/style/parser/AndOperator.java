/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.AndFilter;
import net.anzix.kogutowicz.style.filter.Filter;

/**
 * AND definition element.
 *
 * @author elek
 */
public class AndOperator implements Operator {

    @Override
    public boolean is(String token) {
        return "AND".equalsIgnoreCase(token);
    }

    @Override
    public Filter createFilter(Object... parameters) {
        Filter[] filters = new Filter[2];
        filters[0] = (Filter) parameters[0];
        filters[1] = (Filter) parameters[1];
        return new AndFilter(filters);
    }

    @Override
    public double getPrecendece() {
        return 2;
    }
}
