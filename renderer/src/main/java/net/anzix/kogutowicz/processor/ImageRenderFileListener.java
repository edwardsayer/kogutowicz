/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.io.File;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;

/**
 *
 * @author elek
 */
public class ImageRenderFileListener extends ImageRenderListener {

    private File outputFile;

    public ImageRenderFileListener(File outputFile, int wp, int hp) {
        super(wp, hp);
        this.outputFile = outputFile;
    }

    @Override
    public void init(ProcessMatrix matrix) {
        super.init(matrix);
        ((Java2DFileRenderer) getRenderer()).setOutputFile(outputFile);
    }
}
