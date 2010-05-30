package net.anzix.kogutowicz.style;

/**
 * Utility ti csv Style parsing.
 * @author elek
 */
public class StringUtil {

    public static int parseString(String str) {
        if (str.startsWith("0x")) {
            str = str.substring(2);
            return Integer.parseInt(str, 16);
        } else {
            return Integer.parseInt(str);
        }

    }
}
