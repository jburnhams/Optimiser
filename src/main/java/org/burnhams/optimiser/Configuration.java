package org.burnhams.optimiser;

public interface Configuration {

    int getThreads();

    double getStartTemperature();

    double getEndTemperature();

    long getMaxIterations();

    boolean isAllowExtraTimeForImprovement();

    int getHillClimbChoices();

    int getMaxNonImprovingMoves();
}
