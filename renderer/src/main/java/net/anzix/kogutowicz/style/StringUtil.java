/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.anzix.kogutowicz.style;

/**
 *
 * @author elek
 */
public class StringUtil {
    public static int parseString(String str){
        if (str.startsWith("0x")){
            str = str.substring(2);
            return Integer.parseInt(str, 16);
        } else {
            return Integer.parseInt(str);
        }

    }
}
