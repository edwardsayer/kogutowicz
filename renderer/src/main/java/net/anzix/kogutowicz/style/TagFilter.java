/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style;

import java.util.ArrayList;
import java.util.List;
import net.anzix.kogutowicz.Zoom;
import net.anzix.kogutowicz.element.Element;

/**
 *
 * @author elek
 */
public class TagFilter implements Filter {

    public static final String EMPTY = "#EMTPY#";

    private String key;

    private List<String> values = new ArrayList();

    public TagFilter(String key, String... value) {
        this.key = key;
        if (value.length == 1 && value[0].contains(",")) {
            for (String st : value[0].split(",")) {
                this.values.add(st);
            }
        } else {
            for (String v : value) {
                this.values.add(v);
            }
        }
    }

    @Override
    public boolean is(Element element, Zoom zoom) {
        if (values.size() == 1 && values.get(0).equals(EMPTY)) {
            return "".equals(element.tagValue(EMPTY));
        } else if (values.size() == 0) {
            if (element.containsTag(key)) {
                return true;
            }

        } else {
            for (String v : values) {
                if (element.containsTag(key, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getValues() {
        return values;
    }

    public String getKey() {
        return key;
    }
}
