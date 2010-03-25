/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser.kosmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author elek
 */
public class KosmosStyleElement {

    private Map<String, String> parameters = new HashMap();
    private KosmosStyleType type;

    public KosmosStyleElement() {
    }

    public static List<KosmosStyleElement> parse(String rawStyle) {
        List<KosmosStyleElement> styles = new ArrayList();
        String pt1 = "(\\w+)\\s*\\(([^\\)]*)\\)";
        String pt2 = "([^,\\s=]+)=([^,]+)";
        Pattern p1 = Pattern.compile(pt1);
        Pattern p2 = Pattern.compile(pt2);
        Matcher m1 = p1.matcher(rawStyle);
        while (m1.find()) {
            KosmosStyleElement e = new KosmosStyleElement();            
            e.setType(KosmosStyleType.valueOf(m1.group(1).toUpperCase()));
            Matcher m2 = p2.matcher(m1.group(2));
            while (m2.find()) {
                for (int i = 0; i <= m2.groupCount(); i++) {
                    e.addParameter(m2.group(1), m2.group(2));

                }
            }
            styles.add(e);
        }
        return styles;



    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public KosmosStyleType getType() {
        return type;
    }

    public void setType(KosmosStyleType type) {
        this.type = type;
    }
}
