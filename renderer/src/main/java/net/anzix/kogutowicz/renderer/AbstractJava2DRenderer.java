package net.anzix.kogutowicz.renderer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import net.anzix.kogutowicz.geometry.CoordBox;
import net.anzix.kogutowicz.geometry.CoordPair;
import net.anzix.kogutowicz.geometry.GeometryElement;
import net.anzix.kogutowicz.geometry.Icon;
import net.anzix.kogutowicz.geometry.Label;
import net.anzix.kogutowicz.geometry.Line;
import net.anzix.kogutowicz.geometry.Point;
import net.anzix.kogutowicz.geometry.Polygon;
import net.anzix.kogutowicz.style.Layer;

/**
 * Base class to render to the Java g2d object.
 *
 * @author elek
 */
public abstract class AbstractJava2DRenderer extends AbstractRenderer {

    private Graphics2D graphics;

    @Override
    public void setClip(CoordBox clip) {
        if (clip != null) {
            graphics.setColor(Color.BLACK);
            CoordPair cliptl = clip.getTopLeft().transform(getCurrentTransformation());
            CoordPair clipbr = clip.getBottomRight().transform(getCurrentTransformation());
            Rectangle r = new Rectangle(
                    (int) Math.round(cliptl.getX()) - 1,
                    (int) Math.round(cliptl.getY()) - 1,
                    (int) Math.round(Math.abs(cliptl.getX() - clipbr.getX())) + 1,
                    (int) Math.round(Math.abs(cliptl.getY() - clipbr.getY())) + 1);

//            graphics.draw(r);
            graphics.setClip(r);
        }
    }

    @Override
    public void renderGeometry(Layer layer, GeometryElement element) {
        if (element.isHidden()) {
            return;
        }


//        if (style.getPattern() != null) {
//            ig2.setStroke(new BasicStroke(style.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, style.getPattern(), 0f));
//        } else {
//            ig2.setStroke(new BasicStroke(style.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//        }
//        Color c = new Color(style.getColor().getRed(), style.getColor().getGreen(), style.getColor().getBlue(), style.getAlpha());

//        ig2.setPaint(c);
        if (element instanceof Line) {
            drawLine((Line) element);

        } else if (element instanceof Polygon) {
            drawPolygons((Polygon) element);


        } else if (element instanceof Icon) {
            drawIcon((Icon) element);

        } else if (element instanceof Label) {
            drawLabel((Label) element);
        }
    }

    public int round(double d) {
        return (int) Math.round(d);
    }

    private void drawLine(Line le) {
        float[] pattern = le.getPattern();
        if (pattern != null && pattern.length > 0) {
            graphics.setStroke(new BasicStroke(le.getWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0f, pattern, 0));
        } else {
            graphics.setStroke(new BasicStroke(le.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        graphics.setPaint(convertColor(le.getColor()));


        int x[] = new int[le.getPoints().size()];
        int y[] = new int[le.getPoints().size()];

        for (int i = 0; i < le.getPoints().size(); i++) {
            Point p1 = le.getPoints().get(i).transform(getCurrentTransformation());
            x[i] = round(p1.getX());
            y[i] = round(p1.getY());

        }
        graphics.drawPolyline(x, y, le.getPoints().size());
    }

    private Color convertColor(net.anzix.kogutowicz.geometry.Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private void drawPolygons(Polygon polygon) {
        graphics.setStroke(new BasicStroke(polygon.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.setPaint(convertColor(polygon.getColor()));

        int alpha = polygon.getColor().getAlpha();
        if (alpha != 255) {
            float fl = alpha / 255f;
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
        } else {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        java.awt.Polygon p = new java.awt.Polygon();
        for (Point pp : polygon.getOutline().getPoints()) {
            Point pt = pp.transform(getCurrentTransformation());
            p.addPoint(round(pt.getX()), round(pt.getY()));
        }
        graphics.fill(p);
    }

    protected void drawIcon(Icon i) {
        try {
            int noOfPng = i.getSource().size();
            int sumw = 0;
            int maxh = -1;
            int realNo = 0;
            List<BufferedImage> images = new ArrayList<BufferedImage>();
            for (int j = 0; j < noOfPng; j++) {
                String img = i.getSource().get(j);
                if (!img.endsWith(".png")) {
                    img += ".png";
                }
                InputStream is = getClass().getResourceAsStream("/icons/" + img);
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
            Point p = i.transform(getCurrentTransformation());

            int cx = round(p.getX() - sumw / 2);
            int cy = round(p.getY() - maxh / 2);

            for (int j = 0; j < realNo; j++) {
                BufferedImage bimg = images.get(j);
                int w = bimg.getWidth(null);
                int h = bimg.getHeight(null);
                graphics.drawImage(bimg, round(cx), round(cy), null);
                cx += w;
            }
//            cx = round(i.transform(getCurrentTransformation()).getX() - sumw / 2);
//            cy = round(i.transform(getCurrentTransformation()).getY() - maxh / 2);
//            graphics.drawRect(cx, cy, sumw, maxh);



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

    private void drawLabel(Label label) {
        Point p = label.transform(getCurrentTransformation());
        Font font = new Font("Dialog", Font.PLAIN, label.getSize());
        FontMetrics metrics = graphics.getFontMetrics(font);
        int adv = metrics.stringWidth(label.getMessages());
        graphics.drawString(label.getMessages(), ((float) p.getX() - adv / 2), (float) p.getY());

    }
}
