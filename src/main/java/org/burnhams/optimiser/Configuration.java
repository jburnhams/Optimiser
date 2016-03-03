package org.burnhams.optimiser;

public interface Configuration {

    public int getThreads();

    public double getStartingTemperature();

    public double getEndingTemperature();

    public long getMaxIterations();

    public boolean isAllowExtraTimeForImprovement();

    public int getHillClimbChoices();

    public int getMaxNonImprovingMoves();
}
