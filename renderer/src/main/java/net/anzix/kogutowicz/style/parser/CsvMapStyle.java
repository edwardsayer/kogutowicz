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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
 * Mapstyle defined by a csv file.
 * 
 * @author elek
 */
@MetaInfServices
public class CsvMapStyle implements MapStyle {

    private static final Logger LOGGER = Logger.getLogger(CsvMapStyle.class.getName());

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
        if (source == null) {
            throw new IllegalStateException("Source file is not set");
        }
        
        if (!source.exists()) {
            throw new IllegalStateException("Source file does not exist: " + source.getAbsolutePath());
        }

        CsvReader reader = null;
        try {
            reader = new CsvReader(new FileReader(source));
            reader.readHeaders();
            Layer currentLayer = null;
            while (reader.readRecord()) {
                //beginning of a file
                if (!isEmpty(reader.get("layer"))) {
                    currentLayer = new Layer();
                    currentLayer.setName(reader.get("layer"));
                    simpleMap.addLayer(reader.get("layer"), currentLayer);
                }
                
                if (currentLayer == null) {
                    throw new IllegalStateException("No layer defined before style elements");
                }
                
                String type = reader.get("type");
                if (isEmpty(type)) {
                    // Skip rows without type
                    continue;
                }
                
                Class<? extends Figure> figureClazz = figures.get(type);
                if (figureClazz == null) {
                    throw new IllegalArgumentException("Unknown style element type: " + type);
                }
                
                try {
                    Figure figure = figureClazz.newInstance();

                    // Load parameters for the figure
                    List<String> params = new ArrayList();
                    for (int i = 6; i < reader.getColumnCount(); i++) {
                        String colValue = reader.get(i);
                        if (!isEmpty(colValue)) {
                            params.add(colValue);
                        }
                    }

                    figure.init(params.toArray(new String[0]));

                    // Apply generic parameter loading
                    applyGenericParameter(figure, "zindex", reader, Integer.class);
                    applyGenericParameter(figure, "startZoom", reader, Integer.class);
                    applyGenericParameter(figure, "stopZoom", reader, "endZoom", Integer.class);

                    // Handle filter separately as it requires parsing
                    String filter = reader.get("filter");
                    if (!isEmpty(filter)) {
                        try {
                            figure.setFilter(fp.parse(filter));
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Error parsing filter: " + filter, e);
                        }
                        currentLayer.addFigure(figure);
                    } else {
                        // Handle combined figures
                        if (currentLayer.getFigures().isEmpty()) {
                            throw new IllegalStateException("Cannot add a combined figure when no previous figure exists");
                        }
                        
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
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("Error creating figure of type: " + type, e);
                }
            }
        } catch (FileNotFoundException e) {
            String errorMsg = "CSV file not found: " + source.getAbsolutePath();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new IllegalStateException(errorMsg, e);
        } catch (IOException e) {
            String errorMsg = "Error reading CSV file: " + source.getAbsolutePath();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new IllegalStateException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Error processing CSV style file: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new IllegalStateException(errorMsg, e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return simpleMap;
    }

    /**
     * Generic method to apply a parameter to a figure from the CSV reader.
     * 
     * @param figure The figure to apply the parameter to
     * @param csvColumn The CSV column name to read from
     * @param reader The CSV reader
     * @param paramType The type of the parameter
     */
    private <T> void applyGenericParameter(Figure figure, String csvColumn, CsvReader reader, Class<T> paramType) {
        applyGenericParameter(figure, csvColumn, reader, csvColumn, paramType);
    }
    
    /**
     * Generic method to apply a parameter to a figure from the CSV reader with different setter name.
     * 
     * @param figure The figure to apply the parameter to
     * @param csvColumn The CSV column name to read from
     * @param reader The CSV reader
     * @param setterName The name of the setter method (without "set" prefix)
     * @param paramType The type of the parameter
     */
    private <T> void applyGenericParameter(Figure figure, String csvColumn, CsvReader reader, String setterName, Class<T> paramType) {
        try {
            String value = reader.get(csvColumn);
            if (!isEmpty(value)) {
                if (paramType == Integer.class) {
                    try {
                        int intValue = Integer.parseInt(value);
                        String methodName = "set" + setterName.substring(0, 1).toUpperCase() + setterName.substring(1);
                        figure.getClass().getMethod(methodName, int.class).invoke(figure, intValue);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid integer value for " + csvColumn + ": " + value, e);
                    }
                } else if (paramType == Float.class) {
                    try {
                        float floatValue = Float.parseFloat(value);
                        String methodName = "set" + setterName.substring(0, 1).toUpperCase() + setterName.substring(1);
                        figure.getClass().getMethod(methodName, float.class).invoke(figure, floatValue);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid float value for " + csvColumn + ": " + value, e);
                    }
                } else if (paramType == String.class) {
                    String methodName = "set" + setterName.substring(0, 1).toUpperCase() + setterName.substring(1);
                    figure.getClass().getMethod(methodName, String.class).invoke(figure, value);
                }
            }
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException)) {
                throw new IllegalStateException("Error setting parameter " + csvColumn + " on figure", e);
            } else {
                throw (IllegalArgumentException) e;
            }
        }
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}