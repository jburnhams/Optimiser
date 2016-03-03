package org.burnhams.utils;

import java.awt.*;

public class ColourUtils {


    private static double getDiff(int r, int g, int b, float[] hsb) {
        float[] other = Color.RGBtoHSB(r, g, b, null);
        return Math.sqrt(Math.pow(other[0] - hsb[0], 2)
                + Math.pow(other[1] - hsb[1], 2)
                + Math.pow(other[2] - hsb[2], 2));
    }

    private static double getDiff(int r1, int r2, int g1, int g2, int b1, int b2) {
        return Math.sqrt(Math.pow(r2 - r1, 2)
                + Math.pow(g2 - g1, 2)
                + Math.pow(b2 - b1, 2));
    }

    private static double getWeightedDiff(int r1, int r2, int g1, int g2, int b1, int b2) {
        return Math.sqrt(Math.pow((double) (r2 - r1) * 0.30, 2)
                + Math.pow((double) (g2 - g1) * 0.59, 2)
                + Math.pow((double) (b2 - b1) * 0.11, 2));
    }

    private static double getColourDistance(int r1, int r2, int g1, int g2, int b1, int b2) {
        long rmean = ((long) r1 + (long) r2) / 2;
        long r = (long) r1 - (long) r2;
        long g = (long) g1 - (long) g2;
        long b = (long) b1 - (long) b2;
        return Math.sqrt((((512 + rmean) * r * r) >> 8) + 4 * g * g + (((767 - rmean) * b * b) >> 8));
    }

    public static int[] getRGBFromInt(int input) {
        int r2 = (input >> 16) & 0xFF;
        int g2 = (input >> 8) & 0xFF;
        int b2 = input & 0xFF;
        return new int[]{r2, g2, b2};
    }

    public static double getDiff(ComparisonMethod method, int r1, int r2, int g1, int g2, int b1, int b2) {
        switch (method) {
            case RGB:
                return getDiff(r1, r2, g1, g2, b1, b2);
            case RGB_DISTANCE:
                return getColourDistance(r1, r2, g1, g2, b1, b2);
            case RGB_WEIGHTED:
                return getWeightedDiff(r1, r2, g1, g2, b1, b2);
            case HSB:
                return getDiff(r2, g2, b2, Color.RGBtoHSB(r1, g1, b1, null));
        }
        return -1;
    }

    public static double getDiff(ComparisonMethod comparisonMethod, int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;
        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;
        return getDiff(comparisonMethod, r1, r2, g1, g2, b1, b2);
    }

    public enum ComparisonMethod {
        RGB,
        RGB_WEIGHTED,
        RGB_DISTANCE,
        HSB;
    }


}
