package net.anzix.kogutowicz.config;

import com.google.inject.Singleton;

/**
 * Configuration for DPI (dots per inch) settings.
 * Used to convert between point (pt) and pixel (px) units.
 * 
 * @author SOTA-agent
 */
@Singleton
public class DpiConfig {
    
    /**
     * Default DPI value (72 DPI is standard for many systems)
     */
    private static final float DEFAULT_DPI = 72.0f;
    
    /**
     * Current DPI value
     */
    private float dpi = DEFAULT_DPI;
    
    /**
     * Get the current DPI value
     * 
     * @return the current DPI value
     */
    public float getDpi() {
        return dpi;
    }
    
    /**
     * Set the DPI value
     * 
     * @param dpi the new DPI value
     */
    public void setDpi(float dpi) {
        this.dpi = dpi;
    }
    
    /**
     * Convert points (pt) to pixels (px) based on the current DPI
     * 
     * @param pt value in points
     * @return value in pixels
     */
    public float ptToPx(float pt) {
        return pt * (dpi / 72.0f);
    }
    
    /**
     * Convert pixels (px) to points (pt) based on the current DPI
     * 
     * @param px value in pixels
     * @return value in points
     */
    public float pxToPt(float px) {
        return px * (72.0f / dpi);
    }
}