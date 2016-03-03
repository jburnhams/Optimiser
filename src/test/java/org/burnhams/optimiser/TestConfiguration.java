package org.burnhams.optimiser;

class TestConfiguration implements Configuration {
    @Override
    public long getMaxIterations() {
        return 10000;
    }

    @Override
    public boolean isAllowExtraTimeForImprovement() {
        return true;
    }

    @Override
    public int getThreads() {
        return 1;
    }

    public double getStartingTemperature() {
        return 100;
    }

    @Override
    public double getEndingTemperature() {
        return 0.001;
    }

    @Override
    public int getHillClimbChoices() {
        return 100;
    }

    @Override
    public int getMaxNonImprovingMoves() {
        return 10;
    }
}
