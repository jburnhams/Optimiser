package org.burnhams.optimiser;

class TestConfiguration implements Configuration {
    @Override
    public long getMaxIterations() {
        return 100;
    }

    @Override
    public boolean isAllowExtraTimeForImprovement() {
        return true;
    }

    @Override
    public int getThreads() {
        return 4;
    }

    public double getStartTemperature() {
        return 100;
    }

    @Override
    public double getEndTemperature() {
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
