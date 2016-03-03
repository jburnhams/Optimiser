package org.burnhams.optimiser.converters;

import org.burnhams.imaging.SimpleImage;
import org.burnhams.optimiser.solutions.Solution;

public abstract class BaseImageConverter<T> implements SolutionConverter<T, SimpleImage> {

    protected final int width;
    protected final int height;

    public BaseImageConverter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public SimpleImage convert(Solution<T> solution) {
        int[][] pixels = new int[width][height];

        for (int i = 0; i < solution.size(); i++) {
            T entry = solution.get(i);
            updatePixels(pixels, i, entry);
        }

        return new SimpleImage(pixels);
    }

    protected abstract void updatePixels(int[][] pixels, int i, T entry);
}
