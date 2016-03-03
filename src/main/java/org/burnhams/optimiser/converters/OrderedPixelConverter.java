package org.burnhams.optimiser.converters;

public class OrderedPixelConverter extends BaseImageConverter<Integer> {
    public OrderedPixelConverter(int width, int height) {
        super(width, height);
    }

    @Override
    protected void updatePixels(int[][] pixels, int i, Integer entry) {
        int y = i / width;
        int x = i % width;
        pixels[x][y] = entry;
    }
}
