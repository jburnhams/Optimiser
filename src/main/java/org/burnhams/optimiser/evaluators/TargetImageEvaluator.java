package org.burnhams.optimiser.evaluators;

import org.burnhams.imaging.SimpleImage;
import org.burnhams.optimiser.Evaluator;
import org.burnhams.utils.ColourUtils;

public class TargetImageEvaluator implements Evaluator<SimpleImage> {

    private final ColourUtils.ComparisonMethod comparisonMethod = ColourUtils.ComparisonMethod.RGB_DISTANCE;

    private final SimpleImage targetImage;

    public TargetImageEvaluator(SimpleImage targetImage) {
        this.targetImage = targetImage;
    }


    @Override
    public double evaluate(SimpleImage solutionOutput) {
        double total = 0;
        for (int x = 0; x < targetImage.getWidth(); x++) {
            for (int y = 0; y < targetImage.getHeight(); y++) {
                int target = targetImage.getPixel(x, y);
                int input = solutionOutput.getPixel(x, y);
                total += ColourUtils.getDiff(comparisonMethod, target, input);
            }
        }
        return total / (targetImage.getWidth() * targetImage.getHeight());
    }
}


