/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.Filter;
import net.anzix.kogutowicz.style.filter.OrFilter;

/**
 * OR operator as a parser element.
 * @author elek
 */
public class OrOperator implements Operator{

    @Override
    public boolean is(String token) {
        return "OR".equalsIgnoreCase(token);
    }

    @Override
    public Filter createFilter(Object... parameters) {
        Filter[] filters = new Filter[2];
        filters[0]=(Filter) parameters[0];
        filters[1]=(Filter) parameters[1];
        return new OrFilter(filters);
    }

    @Override
    public double getPrecendece() {
        return 2;
    }

}
