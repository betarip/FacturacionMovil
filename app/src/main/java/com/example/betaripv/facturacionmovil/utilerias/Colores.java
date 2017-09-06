package com.example.betaripv.facturacionmovil.utilerias;

import android.graphics.Color;

/**
 * Created by ivan on 05/09/2017.
 */

public class Colores {

    public static int hexToIInt(String colorStr) {
        int color = Color.parseColor(colorStr);
        return color;
    }

    public static String darker(int color) {
        int c = Integer.valueOf(String.valueOf(color), 16);
        int cDarker = (c - 0x110000);
        String hexColor = String.format("#%06X", (0xFFFFFF & cDarker));
        return hexColor;
    }

    public static String lighter(int color) {
        int c = Integer.valueOf(String.valueOf(color), 16);
        int cLighter = (c + 0x110000);
        String hexColor = String.format("#%06X", (0xFFFFFF & cLighter));
        return hexColor;
    }

    public static String lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        int argb = Color.argb(Color.alpha(color), red, green, blue);
        return String.format("#%06X", (0xFFFFFF & argb));
    }

    public static String darker(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 + factor) / 255 - factor) * 255);
        int green = (int) ((Color.green(color) * (1 + factor) / 255 - factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 + factor) / 255 - factor) * 255);
        int argb = Color.argb(Color.alpha(color), red, green, blue);
        return String.format("#%06X", (0xFFFFFF & argb));
    }

    public static int manipuleColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255)
        );
    }

    public static String textColor(int color) {
        if (Color.red(color) * 0.299 +
                Color.green(color) * 0.587 +
                Color.blue(color) * 0.144 > 186
                ) return "#000000";
        else
            return "#FFFFFF";
    }
}
