package net.anzix.kogutowicz.style.parser;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to parse a layer tag in a mapnik xml.
 *
 * @author elek
 */
public class MapnikLayer {

    private Logger logger = LoggerFactory.getLogger(MapnikLayer.class);

    private String sourceType;

    private String table;

    private String whereClause;

    private String source;

    public MapnikLayer(Element e) {
        Element datasource = e.getChild("Datasource");
        if (datasource != null) {
            sourceType = getParameterByAttr(datasource, "type");
            table = getParameterByAttr(datasource, "table");
            parse();
        } else {
            logger.warn("no Datasource tag in DOM tree");
        }
    }

    private String getParameterByAttr(Element elements, String attrName) {
        for (Element e : (Collection<Element>) elements.getChildren("Parameter")) {
            if (e.getAttribute("name") != null && e.getAttributeValue("name").equals(attrName)) {
                return e.getTextTrim();
            }
        }
        return null;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSource() {
        return source;
    }

    public String getWhereClause() {
        return whereClause;
    }

    private void parse() {
        if (table != null) {
            Pattern p = Pattern.compile("select.* %PREFIX%_(\\w+).*");
            Matcher m = p.matcher(table);
            if (m.find()) {
                source = m.group(1);
            }
        }
    }
}
