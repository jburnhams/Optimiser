package org.burnhams.optimiser.neighbourhood;

import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.solutions.Solution;

public class ShuffleNeighbour<T> extends NeighbourhoodFunction<T> {

    public ShuffleNeighbour(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Solution<T> getNeighbour(Solution<T> candidate) {
        Solution<T> result = (Solution<T>) candidate.clone();
        result.shuffle();
        return result;
    }
}
