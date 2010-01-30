/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.geometry.CoordBox;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.kohsuke.MetaInfServices;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * Render map to SVG via Java2D API.
 *
 * @author elek
 */
@MetaInfServices
public class SvgRenderer extends AbstractJava2DRenderer implements FileOutputRenderer {

    @NotNull
    private File outputFile;

    @Override
    public void initSpace(Size size) {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        setGraphics(new SVGGraphics2D(document));
    }

    @Override
    public void release() {
        Writer out = null;
        try {
            if (!outputFile.getParentFile().exists()){
                outputFile.getParentFile().mkdirs();
            }
            boolean useCSS = true;
            out = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
            getSVGGenerator().stream(out, useCSS);
        } catch (Exception ex) {
            Logger.getLogger(SvgRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(SvgRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SVGGraphics2D getSVGGenerator() {
        return (SVGGraphics2D) getGraphics();
    }

    @Override
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
