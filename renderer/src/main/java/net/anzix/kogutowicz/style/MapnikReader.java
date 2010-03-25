/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author elek
 */
@Deprecated
public class MapnikReader {

    public static void main(String[] args) {
        new MapnikReader().start();
    }

    public void start() {
        try {
            Document d = new SAXBuilder().build(new File("src/test/osm-template.xml"));
            Element rootElement = d.getRootElement();
            for (Element element : (List<Element>) rootElement.getChildren("Layer")) {
                String layerName = element.getAttribute("name").getValue();
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
                        List<String> result = new ArrayList();

                        result.add(layerName + "__" + styleName);
                        result.add("way");
                        result.add(maxScale == null ? "" : "" + convertScaleToZoomLevel(maxScale));
                        result.add(minScale == null ? "" : "" + convertScaleToZoomLevel(minScale));
                        result.add(filter);

                        Set<StyleItem> stparams = new HashSet();

                        loadParams(stparams, rule);

                        for (StyleItem stylep : stparams) {
                            if (prevLayer == null || !result.get(0).equals(prevLayer)) {
                                System.out.print(result.get(0) + ",");
                                System.out.print(result.get(1) + ",");
                            } else {
                                System.out.print(",,");
                            }
                            System.out.print(",");
                            System.out.print(stylep.getStyle() + ",");
                            for (int i = 2; i < result.size(); i++) {
                                System.out.print(result.get(i) + ",");
                            }

                            System.out.print(stylep.getAttrs().get("color") + ",");
                            System.out.println("");
                            prevLayer = result.get(0);
                        }


                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
