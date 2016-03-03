package org.burnhams.optimiser.neighbourhood;

import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.solutions.Solution;

public class RandomSwapNeighbour<T> extends NeighbourhoodFunction<T> {

    public RandomSwapNeighbour(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Solution<T> getNeighbour(Solution<T> candidate) {
        Solution<T> result = candidate.clone();
        int size = candidate.swappableSize();
        boolean swapped = false;
        while (!swapped) {
            int from = random.nextInt(size);
            int to = random.nextInt(size);
            swapped = result.swap(from, to);
        }
        return result;
    }
}
