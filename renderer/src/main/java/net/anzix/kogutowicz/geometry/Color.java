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
        this(color.length(), convertFromString(color));
    }

    public static String getColorFromString(String name) {
        if (colors == null) {
            colors = new HashMap();
            initColorMap();
        }
        return colors.get(name);
    }

    public Color(long color) {
        this(6, color);
    }

    private Color(int size, long color) {
        if (size < 9) {
            this.red = (int) ((color & 0xFF0000) >> 16);
            this.green = (int) ((color & 0x00FF00) >> 8);
            this.blue = (int) (color & 0x0000FF);
        } else {
            this.red = (int) ((color & 0xFF000000) >> 24);
            this.green = (int) ((color & 0x00FF0000) >> 16);
            this.blue = (int) ((color & 0x0000FF00) >> 8);
            this.alpha = (int) (color & 0x000000FF);
        }

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

    private static long convertFromString(String color) {
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
        return Long.parseLong(c, 16);
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

    @Override
    public String toString() {
        return "0x" + hex(red) + hex(green) + hex(blue) + (alpha == 255 ? "" : hex(alpha));
    }

    private String hex(int red) {
        String hex = Integer.toHexString(red);
        if (hex.length() == 1) {
            return "0" + hex;
        } else {
            return hex;
        }
    }
}
