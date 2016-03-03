package org.burnhams.optimiser.converters;

import org.burnhams.imaging.SimpleImage;
import org.burnhams.utils.ColourUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderedStripeConverter extends BaseImageConverter<Integer> {

    private final SimpleImage targetImage;

    public OrderedStripeConverter(SimpleImage targetImage) {
        super(targetImage.getWidth(), targetImage.getHeight());
        this.targetImage = targetImage;
    }

    @Override
    protected void updatePixels(int[][] pixels, int i, Integer entry) {
        if (entry < width) {
            updateColumn(pixels, entry);
        } else {
            updateRow(pixels, entry - width);
        }
    }

    private void updateRow(int[][] pixels, int y) {
        List<Integer> targets = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            if (pixels[x][y] == 0) {
                targets.add(targetImage.getPixel(x, y));
            }
        }
        int average = ColourUtils.average(targets);
        for (int x = 0; x < width; x++) {
            if (pixels[x][y] == 0) {
                pixels[x][y] = average;
            }
        }
    }


    private void updateColumn(int[][] pixels, int x) {
        List<Integer> targets = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            if (pixels[x][y] == 0) {
                targets.add(targetImage.getPixel(x, y));
            }
        }
        int average = ColourUtils.average(targets);
        for (int y = 0; y < height; y++) {
            if (pixels[x][y] == 0) {
                pixels[x][y] = average;
            }
        }
    }
}
