package ca.ulaval.glo2004.visualigue.utils;

import java.awt.Color;

public class ColorUtils {

    public static javafx.scene.paint.Color AWTColorToFXColor(Color awtColor) {
        int red = awtColor.getRed();
        int green = awtColor.getGreen();
        int blue = awtColor.getBlue();
        int alpha = awtColor.getAlpha();
        double opacity = alpha / 255.0;
        return javafx.scene.paint.Color.rgb(red, green, blue, opacity);
    }

    public static Color FXColorToAWTColor(javafx.scene.paint.Color fxColor) {
        double red = fxColor.getRed();
        double green = fxColor.getGreen();
        double blue = fxColor.getBlue();
        double opacity = fxColor.getOpacity();
        double alpha = opacity * 255.0;
        return new Color((int) red, (int) green, (int) blue, (int) opacity);
    }

    public static String ToHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

}
