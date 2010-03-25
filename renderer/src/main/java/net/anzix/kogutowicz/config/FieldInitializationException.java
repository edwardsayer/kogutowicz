/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.config;

/**
 * Config problem.
 *
 * @author elek
 */
public class FieldInitializationException extends RuntimeException{

    public FieldInitializationException(Throwable cause) {
        super(cause);
    }

    public FieldInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldInitializationException(String message) {
        super(message);
    }

    public FieldInitializationException() {
    }

}
