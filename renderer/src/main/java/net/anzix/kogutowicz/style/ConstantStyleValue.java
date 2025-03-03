/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import com.google.inject.Inject;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.config.DpiConfig;

/**
 * Style value independent from zoom value.
 * 
 * @author elek
 */
public class ConstantStyleValue<T> implements StyleValue<T> {

    private T value;
    
    /**
     * Flag to indicate if values are in pt (true) or px (false)
     * Only applicable for Float values
     */
    private boolean isPt = false;
    
    @Inject
    private DpiConfig dpiConfig;

    public ConstantStyleValue(T value) {
        this(value, false);
    }
    
    public ConstantStyleValue(T value, boolean isPt) {
        this.value = value;
        this.isPt = isPt;
    }

    @Override
    public T getValue(Zoom zoom) {
        if (value instanceof Float && isPt && dpiConfig != null) {
            Float ptValue = (Float) value;
            return (T) Float.valueOf(dpiConfig.ptToPx(ptValue));
        }
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
    /**
     * Check if values are in pt
     * Only applicable for Float values
     * 
     * @return true if values are in pt, false if in px
     */
    public boolean isPt() {
        return isPt;
    }
    
    /**
     * Set if values are in pt
     * Only applicable for Float values
     * 
     * @param isPt true if values are in pt, false if in px
     */
    public void setPt(boolean isPt) {
        this.isPt = isPt;
    }
}