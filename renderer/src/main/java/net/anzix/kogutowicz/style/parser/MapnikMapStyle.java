package net.anzix.kogutowicz.style.parser;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.MapStyle;
import net.anzix.kogutowicz.style.PolygonFigure;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.LoggerFactory;

/**
 * Import mapnik style xml and convert to inte  rnal style definition.
 *
 * @author elek
 */
public class MapnikMapStyle implements MapStyle {

    /**
     * mapnik sylte xml file.
     */
    private File source;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private FilterParser fp;

    public MapnikMapStyle() {
        fp = new FilterParser();
    }

    @Override
    public Cartographer applyStyle(Cartographer simpleMap) {
        try {
            Document d = new SAXBuilder().build(source);
            Element rootElement = d.getRootElement();
            int zindex = 100000;
            int layerw = 0;
            //iterate over style
            for (Element element : (List<Element>) rootElement.getChildren("Layer")) {
                String layerName = element.getAttribute("name").getValue();
                logger.debug("parsing layer: {}", layerName);
                MapnikLayer ml = new MapnikLayer(element);
                if (!ml.getSourceType().equals("postgis")) {
                    continue;
                }
                //iterate over styles
                for (Element style : (List<Element>) element.getChildren("StyleName")) {
                    String styleName = style.getValue();
                    logger.debug("parsing style: {}", styleName);
                    Element st = findStyleElement(style.getValue(), rootElement);
                    if (st == null) {
                        logger.error("Style " + style.getValue() + " is not found.");
                        continue;
                    }

                    //generate a layer
                    String name = layerName + "__" + styleName;
                    Layer l = simpleMap.getLayer(layerName);
                    if (l == null) {
                        l = new Layer(name);
                        l.setWeight(layerw++);
                        simpleMap.addLayer(name, l);
                    }
                    

                    for (Element rule : (List<Element>) st.getChildren("Rule")) {

                        Long maxScale = getChildIntValue(rule, "MaxScaleDenominator");
                        Long minScale = getChildIntValue(rule, "MinScaleDenominator");
                        String filter = getChildValue(rule, "Filter");
                        filter = filter.replaceAll("''", "#EMPTY#").replaceAll("[\\]\\[']", "").replaceAll("=", " = ").replaceAll("\\s{2,}", " ").trim();




                        Set<StyleItem> stparams = new HashSet();

                        loadParams(stparams, rule);

                        for (StyleItem stylep : stparams) {
                            if ("polygon".equals(stylep.getStyle())) {
                                PolygonFigure figure = new PolygonFigure();
                                if (maxScale != null) {
                                    figure.startZoom(convertScaleToZoomLevel(maxScale));
                                }
                                if (minScale != null) {
                                    figure.endZoom(convertScaleToZoomLevel(minScale));
                                }
                                if (stylep.getAttrs().get("color") != null) {
                                    //set color
                                    figure.setColor(stylep.getAttrs().get("color").toString());
                                }
                                try {
                                    if (!filter.trim().isEmpty()) {
                                        figure.setFilter(fp.parse(filter));
                                    }
                                    figure.setZindex(zindex--);
                                    l.addFigure(figure);
                                } catch (Exception exc) {
                                    System.out.println("error in filter" + filter);
                                    exc.printStackTrace();
                                }
                            } else if ("line".equals(stylep.getStyle()) || "roads".equals(stylep.getStyle())) {
                                LineFigure figure = new LineFigure();
                                if (maxScale != null) {
                                    figure.startZoom(convertScaleToZoomLevel(maxScale));
                                }
                                if (minScale != null) {
                                    figure.endZoom(convertScaleToZoomLevel(minScale));
                                }
                                if (stylep.getAttrs().get("stroke") != null) {
                                    //set color
                                    figure.setColor(stylep.getAttrs().get("stroke").toString());
                                }
                                if (stylep.getAttrs().get("stroke-width") != null) {
                                    figure.setStroke(Float.valueOf(stylep.getAttrs().get("stroke-width")));
                                }
                                try {
                                    if (!filter.trim().isEmpty()) {
                                        figure.setFilter(fp.parse(filter));
                                    }
                                    figure.setZindex(zindex--);
                                    l.addFigure(figure);
                                } catch (Exception exc) {
                                    System.out.println("error in filter" + filter);
                                    exc.printStackTrace();
                                }
                            }
                        }


                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleMap;
    }

    private Element findStyleElement(String value, Element rootElement) {
        for (Element style : (List<Element>) rootElement.getChildren("Style")) {
            if (style.getAttributeValue("name").equals(value)) {
                return style;
            }
        }
        return null;
    }

    private String getChildValue(Element rule, String string) {
        Element e = rule.getChild(string);
        if (e == null) {
            return "";
        } else {
            return e.getValue();
        }
    }

    protected int convertScaleToZoomLevel(long scale) {
        double limit = 400000000 / 256 / 0.0028;
        for (int level = 0; level < 19; level++) {
            if (scale > limit) {
                return level - 1;
            }
            limit /= 2;
        }
        return -1;

    }

    private Long getChildIntValue(Element rule, String string) {
        Element e = rule.getChild(string);
        if (e == null) {
            return null;
        } else {
            return Long.valueOf(e.getValue());
        }
    }

    protected void loadParams(Set<StyleItem> stparams, Element rule) {
        List<Element> polys = rule.getChildren("PolygonSymbolizer");
        for (Element poly : polys) {
            StyleItem style = new StyleItem("polygon");
            String fill = getCssParam(poly, "fill");
            if (fill != null) {
                style.addAttr("color", normalizeColor(fill));
            }
            stparams.add(style);
        }
        polys = rule.getChildren("LineSymbolizer");
        for (Element poly : polys) {
            StyleItem style = new StyleItem("line");

            String inp;
            inp = getCssParam(poly, "stroke");
            if (inp != null) {
                style.addAttr("stroke", normalizeColor(inp));
            }
            inp = getCssParam(poly, "stroke-width");
            if (inp != null) {
                style.addAttr("stroke-width", inp);
            }
            stparams.add(style);
        }
    }

    private String getCssParam(Element poly, String string) {
        for (Element e : (List<Element>) poly.getChildren("CssParameter")) {
            if (e.getAttribute("name") != null && e.getAttributeValue("name").equals(string)) {
                return e.getValue();
            }
        }
        return null;
    }

    public static class StyleItem {

        private String style;

        public StyleItem(String style) {
            this.style = style;
        }
        private Map<String, String> attrs = new HashMap();

        public Map<String, String> getAttrs() {
            return attrs;
        }

        public void addAttr(String key, String value) {
            attrs.put(key, value);
        }

        public void setAttrs(Map<String, String> attrs) {
            this.attrs = attrs;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }

    public String normalizeColor(String color) {
        Map<String, Color> colors = new HashMap<String, Color>();

        colors.put("pink", Color.PINK);
        colors.put("green", Color.GREEN);
        colors.put("grey", Color.GRAY);
        colors.put("white", Color.WHITE);
        colors.put("black", Color.black);

        String c = color.trim();
        if (c.startsWith("#")) {
            if (c.length() == 4) {
                return "0x" + color.substring(1) + color.substring(1);
            } else {
                return "0x" + color.substring(1);
            }
        } else {
            try {
                Color col = (Color) java.awt.Color.class.getField(color).get(null);
                return "0x" + getHex(col.getRed()) + getHex(col.getGreen()) + getHex(col.getBlue());
            } catch (Exception ex) {
                logger.warn("Unknown color{}" + color);
                return "0x000000";
            }
        }
    }

    public String getHex(int num) {
        return (Integer.toHexString(0x100 | num).substring(1).toUpperCase());
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
