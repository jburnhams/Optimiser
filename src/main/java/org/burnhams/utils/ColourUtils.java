package org.burnhams.utils;

import java.awt.*;
import java.util.List;

public class ColourUtils {

    private static final double[] CIEXYZ_D65 = new double[]{0.9505, 1.0, 1.0890};


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

    public static int getIntFromRGB(int r, int g, int b) {
        return ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff);
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

    public static double[] RGBtoXYZ(int red, int green, int blue) {
        // normalize red, green, blue values
        double rLinear = (double) red / 255.0;
        double gLinear = (double) green / 255.0;
        double bLinear = (double) blue / 255.0;

        // convert to a sRGB form
        double r = (rLinear > 0.04045) ? Math.pow((rLinear + 0.055) / (
                1 + 0.055), 2.2) : (rLinear / 12.92);
        double g = (gLinear > 0.04045) ? Math.pow((gLinear + 0.055) / (
                1 + 0.055), 2.2) : (gLinear / 12.92);
        double b = (bLinear > 0.04045) ? Math.pow((bLinear + 0.055) / (
                1 + 0.055), 2.2) : (bLinear / 12.92);

        // converts
        return new double[]{
                (r * 0.4124 + g * 0.3576 + b * 0.1805),
                (r * 0.2126 + g * 0.7152 + b * 0.0722),
                (r * 0.0193 + g * 0.1192 + b * 0.9505)
        };
    }

    private static double Fxyz(double t) {
        return ((t > 0.008856) ? Math.pow(t, (1.0 / 3.0)) : (7.787 * t + 16.0 / 116.0));
    }

    /// <summary>
/// Converts CIEXYZ to CIELab.
/// </summary>
    public static double[] XYZtoLab(double x, double y, double z) {
        return new double[]{
                116.0 * Fxyz(y / CIEXYZ_D65[1]) - 16,
                500.0 * (Fxyz(x / CIEXYZ_D65[0]) - Fxyz(y / CIEXYZ_D65[1])),
                200.0 * (Fxyz(y / CIEXYZ_D65[1]) - Fxyz(z / CIEXYZ_D65[2]))
        };
    }

    public static double[] LabtoXYZ(double l, double a, double b) {
        double delta = 6.0 / 29.0;

        double fy = (l + 16) / 116.0;
        double fx = fy + (a / 500.0);
        double fz = fy - (b / 200.0);

        return new double[]{
                (fx > delta) ? CIEXYZ_D65[0] * (fx * fx * fx) : (fx - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ_D65[0],
                (fy > delta) ? CIEXYZ_D65[1] * (fy * fy * fy) : (fy - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ_D65[1],
                (fz > delta) ? CIEXYZ_D65[2] * (fz * fz * fz) : (fz - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ_D65[2]
        };
    }

    public static int XYZtoRGB(double x, double y, double z) {
        double[] Clinear = new double[3];
        Clinear[0] = x * 3.2410 - y * 1.5374 - z * 0.4986; // red
        Clinear[1] = -x * 0.9692 + y * 1.8760 - z * 0.0416; // green
        Clinear[2] = x * 0.0556 - y * 0.2040 + z * 1.0570; // blue

        for (int i = 0; i < 3; i++) {
            Clinear[i] = (Clinear[i] <= 0.0031308) ? 12.92 * Clinear[i] : (
                    1 + 0.055) * Math.pow(Clinear[i], (1.0 / 2.4)) - 0.055;
        }

        return getIntFromRGB((int) Math.round(Clinear[0] * 255.0), (int) Math.round(Clinear[1] * 255.0), (int) Math.round(Clinear[2] * 255.0));
    }

    public static int average(List<Integer> pixels) {
        if (pixels == null || pixels.isEmpty()) {
            return 0;
        } else if (pixels.size() == 1) {
            return pixels.get(0);
        }
        double totalL = 0, totalA = 0, totalB = 0;
        for (Integer pixel : pixels) {
            int[] rgb = getRGBFromInt(pixel);
            double[] xyz = RGBtoXYZ(rgb[0], rgb[1], rgb[2]);
            double[] lab = XYZtoLab(xyz[0], xyz[1], xyz[2]);
            totalL += lab[0];
            totalA += lab[1];
            totalB += lab[2];
        }
        int size = pixels.size();
        double[] xyz = LabtoXYZ(totalL / size, totalA / size, totalB / size);
        return XYZtoRGB(xyz[0], xyz[1], xyz[2]);
    }

    public enum ComparisonMethod {
        RGB,
        RGB_WEIGHTED,
        RGB_DISTANCE,
        HSB;
    }


}
