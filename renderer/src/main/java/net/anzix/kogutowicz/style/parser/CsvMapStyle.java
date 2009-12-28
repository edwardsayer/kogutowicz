/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.*;
import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.anzix.kogutowicz.element.Way;

/**
 *
 * @author elek
 */
public class CsvMapStyle implements MapStyle {

    private File source;

    private FilterParser fp;

    private Map<String, Class<? extends Figure>> figures = new HashMap();

    public CsvMapStyle() {
        figures.put("polygon", PolygonFigure.class);
        figures.put("line", LineFigure.class);
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
                if (!isEmpty(reader.get("layer"))) {
                    currentLayer = new Layer();
                    currentLayer.setName(reader.get("layer"));
                    simpleMap.addLayer(reader.get("layer"), currentLayer);
                }
                Class<? extends Figure> figureClazz = figures.get(reader.get("type"));
                Figure figure = figureClazz.newInstance();

                List<String> params = new ArrayList();
                for (int i = 7; i < reader.getColumnCount(); i++) {
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
