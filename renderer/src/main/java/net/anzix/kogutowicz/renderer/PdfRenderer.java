/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import net.anzix.kogutowicz.RenderException;
import net.anzix.kogutowicz.Size;
import org.kohsuke.MetaInfServices;

/**
 * Render map to PDF.
 * 
 * Using Java Graphics2D object.
 *
 * @author elek
 */
@MetaInfServices(Renderer.class)
public class PdfRenderer extends AbstractJava2DRenderer implements FileOutputRenderer {

    private Document document;

    private PdfTemplate tp;

    private File outputFile;

    private PdfContentByte cb;

    private PdfWriter writer;

    @Override
    public void initSpace(Size size) {
        try {
            document = new Document();
            document.setPageSize(new Rectangle(size.getWidth(), size.getHeight()));

            // step 2: creation of the writer
            writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));

            // step 3: we open the document
            document.open();

            // step 4: we grab the ContentByte and do some stuff with it

            // we create a fontMapper and read all the fonts in the font directory
            DefaultFontMapper mapper = new DefaultFontMapper();
            FontFactory.registerDirectories();
//            mapper.insertDirectory("c:\\windows\\fonts");
            // we create a template and a Graphics2D object that corresponds with it
            cb = writer.getDirectContent();
            tp = cb.createTemplate(size.getWidth(), size.getHeight());
            tp.setWidth(size.getWidth());
            tp.setHeight(size.getHeight());
            setGraphics(tp.createGraphics(size.getWidth(), size.getHeight(), mapper));
        } catch (Exception ex) {
            throw new RenderException("Error on initializing pdf writer", ex);
        }

    }

    @Override
    public void release() {
        System.out.println("release");
        getGraphics().dispose();
        tp.sanityCheck();
        cb.addTemplate(tp, 0, 0);
        cb.sanityCheck();
        document.close();
        writer.close();
    }

    @Override
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
}
