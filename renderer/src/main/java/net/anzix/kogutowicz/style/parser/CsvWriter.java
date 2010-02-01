package net.anzix.kogutowicz.style.parser;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import net.anzix.kogutowicz.geometry.Color;
import net.anzix.kogutowicz.style.Cartographer;
import net.anzix.kogutowicz.style.Figure;
import net.anzix.kogutowicz.style.Layer;
import net.anzix.kogutowicz.style.LineFigure;
import net.anzix.kogutowicz.style.LineStyle;
import net.anzix.kogutowicz.style.PolygonFigure;
import net.anzix.kogutowicz.style.PolygonStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writer cartographer out to csv format.
 *
 * @author elek
 */
public class CsvWriter {

    private Map<Class, String> figureClassToStyle = new HashMap();

    private Logger loggger = LoggerFactory.getLogger(CsvWriter.class);

    public CsvWriter() {
        figureClassToStyle.put(LineFigure.class, "line");
        figureClassToStyle.put(PolygonFigure.class, "polygon");

    }

    /**
     * Write out a cartographer in CSV representation.
     * @param writer
     * @param c
     * @throws IOException
     */
    public void write(Writer writer, Cartographer c) throws IOException {
        writer.write("layer;zindex;type;startZoom;stopZoom;filter;color;width\n");;
        for (Layer layer : c.getLayers()) {
            boolean first = true;
            for (Figure f : layer.getFigures()) {
                if (first) {
                    writer.write(layer.getName());
                }
                writer.write(";");
                writer.write(";");
                String type = figureClassToStyle.get(f.getClass());
                if (type == null) {
                    loggger.error("Unknown figure type " + f.getClass());
                    continue;
                }
                writer.write(type + ";");
                writer.write((f.getStartZoom() > 0 ? f.getStartZoom() : "")+ ";");
                writer.write(f.getEndZoom() + ";");
                writer.write(f.getFilter() + ";");
                if (f instanceof PolygonFigure) {
                    String color = ((PolygonFigure) f).getStyle().getStyle(PolygonStyle.COLOR, Color.class).toString();
                    writer.write(color + ";");
                    String width = ((PolygonFigure) f).getStyle().getStyle(PolygonStyle.WIDTH, Float.class).toString();
                    writer.write(width + ";");
                } else if (f instanceof LineFigure) {
                    String color = ((LineFigure) f).getStyle().getStyle(LineStyle.COLOR, Color.class).toString();
                    writer.write(color + ";");
                    String width = ((LineFigure) f).getStyle().getStyle(LineStyle.WIDTH, Float.class).toString();
                    writer.write(width + ";");
                }
                writer.write("\n");
                first = false;
            }
            writer.flush();
        }

    }
}
