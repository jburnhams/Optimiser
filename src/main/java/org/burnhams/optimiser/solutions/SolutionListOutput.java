package org.burnhams.optimiser.solutions;

import java.util.List;

public class SolutionListOutput<T> implements SolutionConverter<T, List<T>> {
    @Override
    public List<T> convert(Solution<T> solution) {
        return solution;
    }
}
