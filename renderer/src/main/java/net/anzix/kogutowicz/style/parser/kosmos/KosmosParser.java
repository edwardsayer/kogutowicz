/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser.kosmos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.anzix.kogutowicz.element.Way;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Figure;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.MapStyle;
import net.anzix.kogutowicz.style.PolygonFigure;
import net.anzix.kogutowicz.style.parser.FilterParser;
import org.cyberneko.html.parsers.DOMParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.filter.Filter;
import org.jdom.input.DOMBuilder;
import org.xml.sax.InputSource;

/**
 * Initial version of a parser to load kosmos like style definition pages.
 *
 * @TODO
 * @author elek
 */
public class KosmosParser implements MapStyle {

    Namespace ns = Namespace.getNamespace("http://www.w3.org/1999/xhtml");

    private File source;

    private FilterParser fp = new FilterParser();

    public KosmosParser() {
    }

    public KosmosParser(File source) {
        this.source = source;
    }

    @Override
    public Cartographer applyStyle(Cartographer simpleMap) {
        try {
            if (source == null) {
                throw new IllegalArgumentException("Source parameter must be set on " + getClass().getCanonicalName());
            }
            if (!source.exists()) {
                throw new FileNotFoundException("Style file not found" + source.getAbsolutePath());
            }
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(new FileReader(source)));
            Document d = new DOMBuilder().build(parser.getDocument());

            Element body = d.getRootElement().getChild("BODY", ns);
            Iterator it = body.getDescendants(new Filter() {

                @Override
                public boolean matches(Object obj) {
                    return obj instanceof Element && ((Element) obj).getName().equals("TABLE");

                }
            });

            //iterate over tables
            int layerNo = 0;
            while (it.hasNext()) {
                Layer currentLayer = null;

                Element table = (Element) it.next();
                Element tr1 = table.getChild("TR", ns);
                if (tr1.getChild("TH", ns) != null) {

                    //positions

                    Selector sel = new Selector();
                    List<Element> ths = tr1.getChildren("TH", ns);
                    int i = 0;
                    for (Element e : ths) {
                        String name = e.getText().trim();
                        sel.add(name, i);
                        i++;
                    }
                    if (sel.valid()) {
                        if (currentLayer == null) {

                            currentLayer = new Layer();
                            simpleMap.addLayer("l" + ++layerNo, currentLayer);
                        }
                        for (Element tr : (List<Element>) table.getChildren("TR", ns)) {
                            List tds = tr.getChildren("TD", ns);
                            if (tds.size() < 1) {
                                continue;
                            }
                            KosmosStyle style = new KosmosStyle();

                            style.template = sel.get(tr, "Template");
                            style.targets = sel.get(tr, "Targets");
                            style.name = sel.get(tr, "Rule Name");
                            style.selector = sel.get(tr, "Selector");
                            style.options = sel.get(tr, "Options");
                            style.comment = sel.get(tr, "Comment");
                            System.out.println("parsing " + style.name);
                            try {
                                for (Figure f : createFigures(style)) {
                                    currentLayer.addFigure(f);
                                }
                            } catch (Exception ex) {
                                System.out.println("error in parsing rule: " + style.name);
                                ex.printStackTrace();
                            }

                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return simpleMap;
    }

    private List<Figure> createFigures(KosmosStyle style) {
        List<Figure> figures = new ArrayList();
        if (style.name.startsWith(".") || style.selector.length() == 0) {
            return figures;
        }
        List<KosmosStyleElement> elements = KosmosStyleElement.parse(style.template);
        for (KosmosStyleElement element : elements) {
            Figure f = null;
            if (element.getType() == KosmosStyleType.POLYGON) {
                f = new PolygonFigure();
                PolygonFigure pf = (PolygonFigure) f;
                if (element.getParameter("Color") != null) {
                    pf.setColor(element.getParameter("Color"));
                }

            } else if (element.getType() == KosmosStyleType.POLYLINE) {
                f = new LineFigure();
                LineFigure lf = (LineFigure) f;
                if (element.getParameter("Color") != null) {
                    lf.setColor(element.getParameter("Color"));
                }


            }
            if (f != null) {
                if (element.getParameter("MinZoom") != null) {
                    f.setStartZoom(Integer.parseInt(element.getParameter("MinZoom")));
                }
                if (element.getParameter("MaxZoom") != null) {
                    f.setEndZoom(Integer.parseInt(element.getParameter("MaxZoom")));
                }
                f.setFilter(fp.parse(style.selector));
                figures.add(f);
            }

        }
        return figures;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    private class Selector {

        Map<String, Integer> values = new HashMap();

        void add(String name, Integer position) {
            values.put(name, position);
        }

        private boolean valid() {
            return values.keySet().contains("Template");
        }

        private String get(Element e, String name) {
            int idx = values.get(name);
            List elements = e.getChildren("TD", ns);
            if (idx >= elements.size()) {
                return "";
            } else {
                return ((Element) elements.get(idx)).getValue().trim();
            }
        }
    }
}
