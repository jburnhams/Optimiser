package org.burnhams.optimiser.solutions;

public class SolutionResult<T, U> {

    private final Solution<T> solution;
    private final U solutionOutput;
    private final double cost;

    public SolutionResult(Solution<T> solution, U solutionOutput, double cost) {
        this.solution = solution;
        this.solutionOutput = solutionOutput;
        this.cost = cost;
    }

    public Solution<T> getSolution() {
        return solution;
    }

    public U getSolutionOutput() {
        return solutionOutput;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "SolutionResult{" +
                "cost=" + cost +
                '}';
    }
}
