/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.josm;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import net.anzix.kogutowicz.EqualProjection;
import net.anzix.kogutowicz.SimpleTileDivision;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Box;
import net.anzix.kogutowicz.geometry.CoordPair;
import net.anzix.kogutowicz.processor.ImageRenderListener;
import net.anzix.kogutowicz.processor.ProcessMatrix;
import net.anzix.kogutowicz.processor.Processor;
import net.anzix.kogutowicz.renderer.BaseTransformation;
import net.anzix.kogutowicz.renderer.Java2DSimpleRenderer;
import net.anzix.kogutowicz.renderer.SystemOutputRenderer;
import net.anzix.kogutowicz.renderer.Transformation;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.MapStyle;
import net.anzix.kogutowicz.style.TuhuStyleFactory;
import net.anzix.kogutowicz.style.parser.CsvMapStyle;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;

/**
 *
 * @author elek
 */
public class RenderLayer extends Layer {

    private OsmDataLayer dataLayer;

    private static Icon ICON = new ImageIcon(Toolkit.getDefaultToolkit().createImage(KogutowiczPlugin.class.getResource("/images/editgpx_layer.png")));

    public RenderLayer(String arg0) {
        super(arg0);


    }

    @Override
    public void paint(Graphics graphics, final MapView mv) {
        Rectangle r = mv.getBounds();
        System.out.println(r.getBounds());

        if (dataLayer == null) {
            dataLayer = findDataLayer(mv);
        }
        Rectangle bounds = mv.getBounds();
        LatLon ll1 = mv.getLatLon((int) bounds.getX(), (int) bounds.getY());

        LatLon ll2 = mv.getLatLon((int) (bounds.getX() + bounds.getWidth()), (int) (bounds.getY() + bounds.getHeight()));
        System.out.println(ll1);
        System.out.println(ll2);

        Box box = new Box(new net.anzix.kogutowicz.element.Node(ll2.getX(), ll2.getY()),
                new net.anzix.kogutowicz.element.Node(ll1.getX(), ll1.getY()));
        SimpleTileDivision div = new SimpleTileDivision(new Zoom(14), box.getTopLeft(), box.getBottomRight());

        ProcessMatrix matrix = new ProcessMatrix(box, div, 1, 1);
        matrix.setProjecion(new EqualProjection());
        MapStyle mapStyle = new TuhuStyleFactory();
        Cartographer c = mapStyle.applyStyle(new Cartographer(new JosmDataSource(dataLayer.data)));
//        int wp = (int) Math.round(Math.abs(east - west) / (360 / Math.pow(2, zoom)) * 256);
//        int hp = (int) Math.round(Math.abs(north - south) / (121 * 2 / Math.pow(2, zoom)) * 256);
        ImageRenderListener l = new ImageRenderListener((int) bounds.getWidth(), (int) bounds.getHeight()) {

            @Override
            public void init(ProcessMatrix matrix) {
                super.init(matrix);
                setTransformation(new Transformation() {

                    @Override
                    public CoordPair transform(CoordPair pair) {
                        Point p = mv.getPoint(new LatLon(pair.getY(), pair.getX()));
                        return new CoordPair(p.x, p.y);
                    }
                });

            }
        };
        l.setRenderer(new Java2DSimpleRenderer((Graphics2D) graphics));
        //l.setRenderer(new SystemOutputRenderer());
        matrix.addListener(l);
        Processor processor = new Processor(matrix, c);
        processor.process();


        graphics.drawRect(10, 20, 100, 200);
    }

    @Override
    public Icon getIcon() {
        return ICON;
    }

    @Override
    public String getToolTipText() {
        return "Kogutowitz rendered map layer";
    }

    @Override
    public void mergeFrom(Layer arg0) {
    }

    @Override
    public boolean isMergable(Layer arg0) {
        return false;
    }

    @Override
    public void visitBoundingBox(BoundingXYVisitor arg0) {
    }

    @Override
    public Object getInfoComponent() {
        return null;
    }

    @Override
    public Component[] getMenuEntries() {
        return new Component[]{
                    new JMenuItem(new RefreshAction(this)),
                    new JSeparator()};

    }

    private OsmDataLayer findDataLayer(MapView mv) {
        for (Layer layer : mv.getAllLayers()) {
            if (layer instanceof OsmDataLayer) {
                return (OsmDataLayer) layer;
            }
        }
        return null;
    }
}
