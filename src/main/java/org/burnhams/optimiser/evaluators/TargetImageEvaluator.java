package org.burnhams.optimiser.evaluators;

import org.burnhams.utils.ColourUtils;

public class TargetImageEvaluator {

    private final int[][] targetImage;
    private final int width, height;
    private final ColourUtils.ComparisonMethod comparisonMethod = ColourUtils.ComparisonMethod.RGB_DISTANCE;

    public TargetImageEvaluator(int[][] targetImage) {
        this.targetImage = targetImage;
        this.width = targetImage.length;
        this.height = targetImage[0].length;
    }


    public double getDiff() {
        int tiles = 0;
        double total = 0;
       /* for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TileType t = getTile(x, y);
                if (t != null) {
                    total += t.getColour().getDiff(sourceImage[x][y]);
                    tiles++;
                }
            }
        }         */
        return tiles == 0 ? 0 : (total / tiles);
    }


}


