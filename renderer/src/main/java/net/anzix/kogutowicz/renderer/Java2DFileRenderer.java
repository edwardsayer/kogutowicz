/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Render gemotery elements to Java2D context.
 *
 * @author elek
 */
public class Java2DFileRenderer extends AbstractJava2DRenderer implements FileOutputRenderer {

    private File outputFile;

    private BufferedImage bi;

    @Override
    public void initSpace(double width, double heght) {

        // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
        // into integer pixels
        bi = new BufferedImage(round(width), round(heght), BufferedImage.TYPE_INT_ARGB);

        setGraphics(bi.createGraphics());
        //  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER));
        Map map = new HashMap();
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        getGraphics().setRenderingHints(map);
        getGraphics().setPaint(Color.WHITE);
        getGraphics().fillRect(0, 0, round(width), round(heght));
        getGraphics().setPaint(Color.black);



    }

    @Override
    public void release() {
        try {
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            ImageIO.write(bi, "PNG", outputFile);
            bi = null;
            setGraphics(null);
        } catch (IOException ex) {
            Logger.getLogger(Java2DFileRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        if (outputFile != null && !outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        this.outputFile = outputFile;
    }
}
