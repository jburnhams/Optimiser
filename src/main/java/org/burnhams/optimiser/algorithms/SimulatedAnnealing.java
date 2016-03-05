package org.burnhams.optimiser.algorithms;

import org.apache.log4j.Logger;
import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.Evaluator;
import org.burnhams.optimiser.converters.SolutionConverter;
import org.burnhams.optimiser.neighbourhood.NeighbourhoodFunction;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;

import static org.burnhams.utils.StringUtils.twoSf;

public class SimulatedAnnealing<T, U> extends Optimiser<T, U> {

    private static Logger logger = Logger.getLogger(SimulatedAnnealing.class);
    private final Acceptance acceptance = new Acceptance();

    public SimulatedAnnealing(Evaluator<U> evaluator, SolutionConverter<T, U> solutionConverter, Configuration configuration, NeighbourhoodFunction<T> neighbourhoodFunction) {
        super(configuration, evaluator, solutionConverter, neighbourhoodFunction);
    }

    @Override
    public SolutionResult<T, U> optimise(Solution<T> candidate) {
        double startingTemperature = configuration.getStartTemperature();

        long maxIterations = configuration.getMaxIterations();
        double temperatureMultiple = Math.pow(configuration.getEndTemperature() / startingTemperature, 1d / maxIterations);

        double temperature = startingTemperature;
        Solution<T> current = candidate;
        SolutionResult<T, U> currentResult = evaluate(current);
        SolutionResult<T, U> best = currentResult;
        double currentCost = currentResult.getCost();
        double maxCost = currentCost;
        double bestCost = currentCost;

        long logEvery = Math.max(1, maxIterations / 1000);

        int nonAcceptedMoves = 0;

        for (long i = 0; i < maxIterations || (configuration.isAllowExtraTimeForImprovement() && nonAcceptedMoves <= configuration.getMaxNonImprovingMoves()); i++) {
            Solution<T> neighbour = getNeighbour(current);
            SolutionResult<T, U> neighbourResult = evaluate(neighbour);
            double neighbourCost = neighbourResult.getCost();
            if (neighbourCost > maxCost) {
                maxCost = neighbourCost;
            }
            boolean accepted = acceptance.isAccepted(startingTemperature, temperature, currentCost, maxCost, neighbourCost);
            if (i % logEvery == 0 || i == configuration.getMaxIterations() - 1) {
                logger.info("Run: " + i + ", Temp: " + twoSf(temperature) + ", Current: " + twoSf(currentCost) + ", Neighbour: " + twoSf(neighbourCost) + ", Accepted: " + accepted + ", Best: " + twoSf(bestCost) + ", " + best);
            }
            nonAcceptedMoves++;
            if (accepted) {
                if (neighbourCost < bestCost) {
                    bestCost = neighbourCost;
                    best = neighbourResult;
                }
                currentCost = neighbourCost;
                current = neighbour;
                nonAcceptedMoves = 0;
            }
            temperature *= temperatureMultiple;
        }
        return best;
    }
}
