package net.anzix.kogutowicz.renderer;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

import javax.imageio.ImageIO;

import net.anzix.kogutowicz.Size;

import org.kohsuke.MetaInfServices;

import org.slf4j.LoggerFactory;

/**
 * Render gemotery elements to Java2D context.
 *
 * @author elek
 */
@MetaInfServices(Renderer.class)
public class PngRenderer extends AbstractJava2DRenderer implements FileOutputRenderer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private File outputFile;

    private BufferedImage bi;

    @Override
    public void initSpace(Size size) {
        bi = new BufferedImage(round(size.getWidth()), round(size.getHeight()), BufferedImage.TYPE_INT_ARGB);

        setGraphics(bi.createGraphics());
        //  ig2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER));
        Map map = new HashMap();
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        getGraphics().setRenderingHints(map);
        getGraphics().setPaint(Color.WHITE);
        getGraphics().fillRect(0, 0, round(size.getWidth()), round(size.getHeight()));
        getGraphics().setPaint(Color.black);



    }

    @Override
    public void release() {
        try {
            if (outputFile.getParentFile() != null && !outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            ImageIO.write(bi, "PNG", outputFile);
            bi = null;
            setGraphics(null);
            logger.info("Ouptut is writed to " + outputFile);
        } catch (IOException ex) {
            logger.error("Error on write output file " + outputFile, ex);
        }
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        if (outputFile != null && outputFile.getParentFile() != null && !outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        this.outputFile = outputFile;
    }
}
