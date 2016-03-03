package org.burnhams.optimiser.converters;

import org.burnhams.optimiser.solutions.Solution;

public interface SolutionConverter<T, U> {

    U convert(Solution<T> solution);
}
