/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz;

/**
 * ANy axception on rendering.
 * 
 * @author elek
 */
public class RenderException extends RuntimeException{

    public RenderException(Throwable cause) {
        super(cause);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException() {
    }

}
