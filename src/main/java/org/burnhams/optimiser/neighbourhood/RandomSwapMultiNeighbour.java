package org.burnhams.optimiser.neighbourhood;

import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.solutions.Solution;

public class RandomSwapMultiNeighbour<T> extends NeighbourhoodFunction<T> {

    public RandomSwapMultiNeighbour(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Solution<T> getNeighbour(Solution<T> candidate) {
        Solution<T> result = candidate.clone();
        int size = candidate.swappableSize();

        boolean swapped = false;
        while (!swapped) {
            int numberToSwap = (int) Math.round(Math.abs(random.nextGaussian()) * 2 + 0.5);

            int from = random.nextInt(size + 1 - numberToSwap);
            int to = random.nextInt(size + 1 - numberToSwap);
            for (int i = 0; i < numberToSwap; i++) {
                if (result.swap(from + i, to + i)) {
                    swapped = true;
                }
            }
        }
        return result;
    }
}
