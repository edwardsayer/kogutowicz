/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.AndFilter;
import net.anzix.kogutowicz.style.*;
import com.csvreader.CsvReader;
import com.google.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.processor.RenderContext;
import org.kohsuke.MetaInfServices;

/**
 *
 * @author elek
 */
@MetaInfServices
public class CsvMapStyle implements MapStyle {

    @NotNull
    @Inject
    private RenderContext ctx;

    @NotNull
    private File source;

    private FilterParser fp;

    private Map<String, Class<? extends Figure>> figures = new HashMap();

    public CsvMapStyle() {
        figures.put("polygon", PolygonFigure.class);
        figures.put("line", LineFigure.class);
        figures.put("icon", PngLabelFigure.class);
        figures.put("label", LabelFigure.class);
        fp = new FilterParser();
    }

    public boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    @Override
    public Cartographer applyStyle(Cartographer simpleMap) {

        CsvReader reader = null;
        try {
            reader = new CsvReader(new FileReader(source));
            reader.readHeaders();
            Layer currentLayer = null;
            while (reader.readRecord()) {
                //begining of a file
                if (!isEmpty(reader.get("layer"))) {
                    currentLayer = new Layer();
                    currentLayer.setName(reader.get("layer"));
                    simpleMap.addLayer(reader.get("layer"), currentLayer);
                }
                String type = reader.get("type");
                Class<? extends Figure> figureClazz = figures.get(type);
                if (figureClazz == null) {
                    throw new IllegalArgumentException("Unknown style element " + type);
                }
                Figure figure = figureClazz.newInstance();

                List<String> params = new ArrayList();
                for (int i = 6; i < reader.getColumnCount(); i++) {
                    String colValue = reader.get(i);
                    if (!isEmpty(colValue)) {
                        params.add(colValue);
                    }
                }

                figure.init(params.toArray(new String[0]));

                String zindex = reader.get("zindex");
                if (!isEmpty(zindex)) {
                    figure.setZindex(Integer.parseInt(zindex));
                }

                String zoom = reader.get("startZoom");
                if (!isEmpty(zoom)) {
                    figure.setStartZoom(Integer.parseInt(zoom));
                }

                zoom = reader.get("stopZoom");
                if (!isEmpty(zoom)) {
                    figure.setEndZoom(Integer.parseInt(zoom));
                }

                String filter = reader.get("filter");
                if (!isEmpty(filter)) {
                    figure.setFilter(fp.parse(filter));
                    currentLayer.addFigure(figure);
                } else {
                    int idx = currentLayer.getFigures().size() - 1;
                    Figure prevFigure = currentLayer.getFigures().get(idx);
                    CombinedFigure cf = new CombinedFigure();
                    cf.add(prevFigure);
                    cf.add(figure);
                    cf.setFilter(prevFigure.getFilter());
                    cf.setZindex(prevFigure.getZindex());
                    cf.setStartZoom(prevFigure.getStartZoom());
                    cf.setEndZoom(prevFigure.getEndZoom());
                    prevFigure.setFilter(new AndFilter());
                    currentLayer.getFigures().remove(idx);
                    currentLayer.getFigures().add(idx, cf);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CsvMapStyle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return simpleMap;

    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
