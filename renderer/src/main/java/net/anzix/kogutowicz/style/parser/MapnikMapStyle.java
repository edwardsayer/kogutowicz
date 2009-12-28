/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.MapStyle;
import net.anzix.kogutowicz.style.PolygonFigure;
import net.anzix.kogutowicz.style.TrueFilter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Import mapnik style xml and convert to internal style definition.
 *
 * @author elek
 */
public class MapnikMapStyle implements MapStyle {

    /**
     * mapnik sylte xml file.
     */
    private File source;
    private FilterParser fp;

    public MapnikMapStyle() {
        fp = new FilterParser();
    }

    @Override
    public Cartographer applyStyle(Cartographer simpleMap) {
        try {
            Document d = new SAXBuilder().build(source);
            Element rootElement = d.getRootElement();

            //iterate over style
            for (Element element : (List<Element>) rootElement.getChildren("Layer")) {
                String layerName = element.getAttribute("name").getValue();

                //iterate over styles
                for (Element style : (List<Element>) element.getChildren("StyleName")) {
                    String styleName = style.getValue();
                    Element st = findStyleElement(style.getValue(), rootElement);
                    if (st == null) {
                        System.err.println("Style " + style.getValue() + " is not found.");
                        continue;
                    }
                    String prevLayer = null;
                    for (Element rule : (List<Element>) st.getChildren("Rule")) {
                        Long maxScale = getChildIntValue(rule, "MaxScaleDenominator");
                        Long minScale = getChildIntValue(rule, "MinScaleDenominator");
                        String filter = getChildValue(rule, "Filter");
                        filter = filter.replaceAll("''", "#EMPTY#").replaceAll("[\\]\\[']", "").replaceAll("=", " = ").replaceAll("\\s{2,}", " ").trim();

                        String name = layerName + "__" + styleName;
                        Layer l = simpleMap.getLayer(layerName);
                        if (l == null) {
                            l = new Layer(name);
                            simpleMap.addLayer(name, l);
                        }


                        Set<StyleItem> stparams = new HashSet();

                        loadParams(stparams, rule);

                        for (StyleItem stylep : stparams) {
                            PolygonFigure figure = new PolygonFigure();
                            if (maxScale != null) {
                                figure.startZoom(convertScaleToZoomLevel(maxScale));
                            }
                            if (minScale != null) {
                                figure.startZoom(convertScaleToZoomLevel(minScale));
                            }
                            if (stylep.getAttrs().get("color") != null) {
                                //set color
                                figure.setColor(stylep.getAttrs().get("color").toString());
                            }
                            try {
                                figure.setFilter(fp.parse(filter));
                                l.addFigure(figure);
                            } catch (IllegalArgumentException exc) {
                                System.out.println("error in filter" + filter);
                                exc.printStackTrace();
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
    }

    private String getCssParam(Element poly, String string) {
        for (Element e : (List<Element>) poly.getChildren("CssParameter")) {
            if (e.getAttribute("name") != null && e.getAttributeValue("name").equals("fill")) {
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

        String c = color.trim();
        if (c.startsWith("#")) {
            if (c.length() == 4) {
                return "0x" + color.substring(1) + color.substring(1);
            } else {
                return "0x" + color.substring(1);
            }
        } else {
            Color col = colors.get(color.toLowerCase());
            if (col == null) {
                throw new IllegalArgumentException("unknown color " + color);
            }
            return "0x" + getHex(col.getRed()) + getHex(col.getGreen()) + getHex(col.getBlue());
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
