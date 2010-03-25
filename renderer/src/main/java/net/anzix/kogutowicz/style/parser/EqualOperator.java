/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.Filter;
import net.anzix.kogutowicz.style.TagFilter;

/**
 *
 * @author elek
 */
public class EqualOperator implements Operator {

    @Override
    public boolean is(String token) {
        return "=".equals(token);
    }

    @Override
    public Filter createFilter(Object... parameters) {
        if (parameters.length != 2) {
            throw new IllegalArgumentException("Wrong parameter number: " + parameters.length + " instead of 2");
        }
        return new TagFilter((String) parameters[0], (String) parameters[1]);
    }

    @Override
    public double getPrecendece() {
        return 1;
    }
}
