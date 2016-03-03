package org.burnhams.imaging;

import org.burnhams.utils.ColourUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SimpleImage {

    private final int[][] pixels;
    private final int width, height;


    public SimpleImage(SimpleImage other) {
        width = other.getWidth();
        height = other.getHeight();
        this.pixels = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = other.getPixel(x, y);
            }
        }

    }

    public SimpleImage(int[][] targetImage) {
        this.pixels = targetImage;
        this.width = targetImage.length;
        this.height = targetImage[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixel(int x, int y) {
        return pixels[x][y];
    }

    public void setPixel(int x, int y, int rgb) {
        pixels[x][y] = rgb;
    }

    public int[] getRGB(int x, int y) {
        return ColourUtils.getRGBFromInt(pixels[x][y]);
    }

    public List<Integer> getPixels() {
        List<Integer> results = new ArrayList<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                results.add(pixels[x][y]);
            }
        }
        return results;
    }

    public BufferedImage getBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, pixels[x][y]);
            }
        }
        return image;
    }
}
