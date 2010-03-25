package net.anzix.kogutowicz.renderer;

import java.io.File;

/**
 * Renderer family which render the geometries to a phisycal file.
 * 
 * @author elek
 */
public interface FileOutputRenderer {

    /**
     * Setting the output file destination.
     * @param outputFile
     */
    public void setOutputFile(File outputFile);
}
