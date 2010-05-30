package net.anzix.kogutowicz.style;

import java.util.HashMap;
import java.util.Map;
import net.anzix.kogutowicz.Zoom;

/**
 * Style value depend on zoom value.
 *
 * @author elek
 */
public class ZoomStyleValue implements StyleValue<Float> {

    /**
     * zoom/value pairs.
     */
    private Map<Integer, Float> values = new HashMap();

    public ZoomStyleValue(String pattern) {
        String[] elements = pattern.split(",");
        for (String element : elements) {
            String[] keyVal = element.split(":");
            if (keyVal[0].contains("-")) {
                String[] fromTo = keyVal[0].split("-");
                int from = Integer.parseInt(fromTo[0]);
                int to = Integer.parseInt(fromTo[1]);
                for (int i = from; i <= to; i++) {
                    values.put(i, Float.parseFloat(keyVal[1]));
                }
            } else {
                values.put(Integer.valueOf(keyVal[0]), Float.parseFloat(keyVal[1]));
            }
        }
    }

    @Override
    public Float getValue(Zoom zoom) {
        return values.get(new Integer(zoom.getLevel()));
    }

    protected Map<Integer, Float> getValues() {
        return values;
    }
}
