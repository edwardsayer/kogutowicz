/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.geometry;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elek
 */
public class Color {

    private int red;
    private int green;
    private int blue;
    private int alpha = 255;
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    private static Map<String, String> colors;

    public Color(String color) {
        this(convertFromString(color));
    }

    public static String getColorFromString(String name) {
        if (colors == null) {
            colors = new HashMap();
            initColorMap();
        }
        return colors.get(name);
    }

    public Color(int color) {
        this.red = (color & 0xFF0000) >> 16;
        this.green = (color & 0x00FF00) >> 8;
        this.blue = color & 0x0000FF;
    }

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    private static int convertFromString(String color) {
        String c;
        if (color.startsWith("#")) {
            c = color.substring(1);
        } else if (color.startsWith("0x")) {
            c = color.substring(2);
        } else if (color.matches("\\w+")) {
            c = getColorFromString(color);
            if (c == null) {
                System.out.println("Color is not supported: " + color);
                c = "0";
            } else {
                return convertFromString(c);
            }
        } else {
            c = color;
        }
        return Integer.parseInt(c, 16);
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    private static void initColorMap() {
        colors.put("white", "#FFFFFF");
        colors.put("black", "#000000");
    }
}
