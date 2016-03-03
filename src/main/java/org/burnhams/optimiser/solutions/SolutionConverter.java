package org.burnhams.optimiser.solutions;

public interface SolutionConverter<T, U> {

    U convert(Solution<T> solution);
}
