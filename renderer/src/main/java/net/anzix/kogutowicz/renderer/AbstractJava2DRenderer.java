
package net.anzix.kogutowicz.renderer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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

        }
    }

    public int round(double d) {
        return (int) Math.round(d);
    }

    private void drawLine(Line le) {
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
        graphics.setStroke(new BasicStroke(polygon.getStyle().getStyle(PolygonStyle.WIDTH, Float.class), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.setPaint(convertColor(polygon.getStyle().getStyle(PolygonStyle.COLOR, net.anzix.kogutowicz.geometry.Color.class)));

        int alpha = polygon.getStyle().getStyle(PolygonStyle.COLOR, net.anzix.kogutowicz.geometry.Color.class).getAlpha();
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

    private void drawIcon(Icon i) {
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
            cx = round(i.transform(getCurrentTransformation()).getX() - sumw / 2);
            cy = round(i.transform(getCurrentTransformation()).getY() - maxh / 2);
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
