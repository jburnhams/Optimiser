package org.burnhams.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    private static final Logger logger = Logger.getLogger(ImageUtils.class);

    public static int getAdjustedHeight(int width, int w, int h) {
        return (h * width) / w;
    }

    public static int getAdjustedWidth(int height, int w, int h) {
        return (w * height) / h;
    }

    public static BufferedImage getImage(File f, int w, int h) throws IOException {
        BufferedImage bi = ImageIO.read(f);
        int newHeight = getAdjustedHeight(w, bi.getWidth(), bi.getHeight());
        int newWidth = getAdjustedWidth(h, bi.getWidth(), bi.getHeight());
        if (newHeight < h) {
            w = newWidth;
        } else {
            h = newHeight;
        }

        BufferedImage tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = tmp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(bi, 0, 0, w, h, null);
        g2.dispose();
        return tmp;
    }

    public static int[][] getPixels(String s, int w, int h) throws IOException {
        int[][] pixels = getPixels(getImage(new File(s), w, h));
        int h2 = pixels[0].length;
        int w2 = pixels.length;
        int[][] result = new int[w][h];
        int heightOffset = (h2 - h) / 2;
        int widthOffset = (w2 - w) / 2;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                result[x][y] = pixels[x + widthOffset][y + heightOffset];
            }
        }
        return result;
    }

    public static int[][] getPixels(BufferedImage bi) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        int[][] result = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                result[x][y] = bi.getRGB(x, y);
            }
        }
        return result;
    }

    public static void saveImage(BufferedImage bi, String filename, String fileExtension) throws IOException {
        File f = new File(filename + "." + fileExtension);
        ImageIO.write(bi, fileExtension, f);
        logger.info("Saved: " + f.getAbsolutePath());
    }

    public static BufferedImage combine(File bg, BufferedImage input) throws IOException {
        BufferedImage result = ImageIO.read(bg);
        Graphics2D g2 = result.createGraphics();
        g2.drawImage(input, (result.getWidth() - input.getWidth()) / 2, (result.getHeight() - input.getHeight()) / 2, input.getWidth(), input.getHeight(), null);
        g2.dispose();
        return result;
    }

}
