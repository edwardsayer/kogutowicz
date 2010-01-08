/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

/**
 * Convert string (ususally come from a property) to an object which can be injected to a field.
 *
 * @author elek
 */
public interface FieldConverter {

    public Object convert(String setting);
}
