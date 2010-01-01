/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.CoordPair;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Icon;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.geometry.Polygon;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineStyle;
import net.anzix.kogutowicz.style.PolygonStyle;

/**
 * 
 *
 * @author elek
 */
public abstract class AbstractJava2DRenderer implements Renderer {

    private Graphics2D graphics;

    @Override
    public void renderGeometry(Layer layer, GeometryElement element, Transformation t, CoordBox clip) {

        graphics.setClip(null);
        if (clip != null) {
//            graphics.setColor(Color.BLACK);

            CoordPair cliptl = clip.getTopLeft().transform(t);
            CoordPair clipbr = clip.getBottomRight().transform(t);
            Rectangle r = new Rectangle((int) Math.floor(cliptl.getX()), (int) Math.round(cliptl.getY()),
                    (int) Math.ceil(Math.abs(cliptl.getX() - clipbr.getX())),
                    (int) Math.round(Math.abs(cliptl.getY() - clipbr.getY())));

//            graphics.draw(r);
            graphics.setClip(r);
        }
//        if (style.getPattern() != null) {
//            ig2.setStroke(new BasicStroke(style.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, style.getPattern(), 0f));
//        } else {
//            ig2.setStroke(new BasicStroke(style.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//        }
//        Color c = new Color(style.getColor().getRed(), style.getColor().getGreen(), style.getColor().getBlue(), style.getAlpha());

//        ig2.setPaint(c);
        if (element instanceof Line) {
            drawLine((Line) element, t);

        } else if (element instanceof Polygon) {
            drawPolygons((Polygon) element, t);


        } else if (element instanceof Icon) {
            drawIcon((Icon) element, t);

        }
    }

    public int round(double d) {
        return (int) Math.round(d);
    }

    private void drawLine(Line le, Transformation t) {
        float[] pattern = le.getStyle().getStyle(LineStyle.PATTERN, new float[0].getClass());
        if (pattern != null) {
            graphics.setStroke(new BasicStroke(le.getStyle().getStyle(LineStyle.WIDTH, Float.class), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, pattern, 0));
        } else {
            graphics.setStroke(new BasicStroke(le.getStyle().getStyle(LineStyle.WIDTH, Float.class), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        graphics.setPaint(convertColor(le.getStyle().getStyle(LineStyle.COLOR, net.anzix.kogutowicz.geometry.Color.class)));


        int x[] = new int[le.getPoints().size()];
        int y[] = new int[le.getPoints().size()];

        for (int i = 0; i < le.getPoints().size(); i++) {
            Point p1 = le.getPoints().get(i).transform(t);
            x[i] = round(p1.getX());
            y[i] = round(p1.getY());

        }
        graphics.drawPolyline(x, y, le.getPoints().size());
    }

    private Color convertColor(net.anzix.kogutowicz.geometry.Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private void drawPolygons(Polygon polygon, Transformation t) {
        graphics.setStroke(new BasicStroke(polygon.getStyle().getStyle(PolygonStyle.WIDTH, Float.class), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.setPaint(convertColor(polygon.getStyle().getStyle(PolygonStyle.COLOR, net.anzix.kogutowicz.geometry.Color.class)));

        java.awt.Polygon p = new java.awt.Polygon();
        for (Point pp : polygon.getOutline().getPoints()) {
            Point pt = pp.transform(t);
            p.addPoint(round(pt.getX()), round(pt.getY()));
        }
        graphics.fill(p);
    }

    private void drawIcon(Icon i, Transformation t) {
        try {
            int noOfPng = i.getSource().size();
            int sumw = 0;
            int maxh = -1;
            int realNo = 0;
            List<BufferedImage> images = new ArrayList<BufferedImage>();
            for (int j = 0; j < noOfPng; j++) {
                String img = i.getSource().get(j);
                InputStream is = getClass().getResourceAsStream("/icons/" + img + ".png");
                if (is != null) {
                    BufferedImage bimg = ImageIO.read(is);

                    sumw += bimg.getWidth(null);
                    if (maxh == -1 || maxh < bimg.getHeight()) {
                        maxh = bimg.getHeight();
                    }
                    images.add(bimg);
                    realNo++;
                } else {
                    //System.out.println("no such icon " + img);
                }
            }
            Point p = i.transform(t);

            int cx = round(p.getX() - sumw / 2);
            int cy = round(p.getY() - maxh / 2);

            for (int j = 0; j < realNo; j++) {
                BufferedImage bimg = images.get(j);
                int w = bimg.getWidth(null);
                int h = bimg.getHeight(null);
                graphics.drawImage(bimg, round(cx), round(cy), null);
                cx += w;
            }
            cx = round(i.transform(t).getX() - sumw / 2);
            cy = round(i.transform(t).getY() - maxh / 2);
            graphics.drawRect(cx, cy, sumw, maxh);



        } catch (IOException ex) {
            Logger.getLogger(Java2DFileRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }
}
