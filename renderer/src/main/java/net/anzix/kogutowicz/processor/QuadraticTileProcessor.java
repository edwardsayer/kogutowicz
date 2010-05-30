/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.processor;

import java.io.File;
import net.anzix.kogutowicz.Size;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.FileOutputRenderer;
import net.anzix.kogutowicz.renderer.Java2DFileRenderer;
import net.anzix.kogutowicz.renderer.Renderer;

/**
 *
 * @author elek
 */
public class QuadraticTileProcessor extends QuadraticProcessor {

    private File outputDir;

    @Override
    protected void afterTileRender(TileCoord coord) {
        getRenderer().release();
    }

    @Override
    protected void beforeTileRender(TileCoord coord) {
        Box b = context.getDivision().getBox(coord);
        setRenderer(new Java2DFileRenderer());
        getRenderer().initSpace(new Size(256, 256));
        getRenderer().setTransformation(new BaseTransformation(getDivision().getBox(coord).getCoordBox(), 256, 256));
        getRenderer().setClip(b.getCoordBox());

        String fileName = context.getZoom().getLevel() + "/" + coord.getX() + "/" + coord.getY() + ".png";
        System.out.println(fileName);
        ((FileOutputRenderer) getRenderer()).setOutputFile(new File(outputDir, fileName));

    }

    @Override
    protected void initRenderer(Renderer renderer, Size size) {
        //NOOP
    }

  

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    
}
