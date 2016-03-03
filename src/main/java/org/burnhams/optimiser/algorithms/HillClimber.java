package org.burnhams.optimiser.algorithms;

import org.apache.log4j.Logger;
import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.Evaluator;
import org.burnhams.optimiser.converters.SolutionConverter;
import org.burnhams.optimiser.neighbourhood.NeighbourhoodFunction;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.burnhams.utils.StringUtils.twoSf;

public class HillClimber<T, U> extends Optimiser<T, U> {

    private static Logger logger = Logger.getLogger(HillClimber.class);

    private final int choices;

    private final int maxNonImprovingMoves;

    private final int threads;

    private ExecutorService executorService;

    public HillClimber(Evaluator<U> evaluator, SolutionConverter<T, U> solutionConverter, Configuration configuration, int choices, int maxNonImprovingMoves, NeighbourhoodFunction<T> neighbourhoodFunctions) {
        super(configuration, evaluator, solutionConverter, neighbourhoodFunctions);
        this.choices = choices;
        this.maxNonImprovingMoves = maxNonImprovingMoves;
        threads = configuration.getThreads();
    }

    public HillClimber(Evaluator<U> evaluator, SolutionConverter<T, U> solutionConverter, Configuration configuration, NeighbourhoodFunction<T> neighbourhoodFunctions) {
        this(evaluator, solutionConverter, configuration, configuration.getHillClimbChoices(), configuration.getMaxNonImprovingMoves(), neighbourhoodFunctions);
    }

    public SolutionResult<T, U> optimise(Solution<T> candidate) {
        executorService = Executors.newFixedThreadPool(threads);
        int run = 0;
        SolutionResult<T, U> result = evaluate(candidate);
        double cost = result.getCost();
        boolean improved = false;
        int nonImprovedMoves = 0;
        while (improved || nonImprovedMoves < maxNonImprovingMoves) {
            nonImprovedMoves++;
            improved = false;
            SolutionResult<T, U> newBest = threads > 1 ? findBestMultiThreaded(run, result.getSolution(), cost) : findBestSingleThreaded(run, result.getSolution(), cost);
            if (newBest != null) {
                double newCost = newBest.getCost();
                if (newCost < cost) {
                    improved = true;
                    cost = newCost;
                    nonImprovedMoves = 0;
                    result = newBest;
                }
            }
            run++;
        }
        executorService.shutdown();
        return result;
    }


    private SolutionResult<T, U> findBestMultiThreaded(int run, final Solution<T> candidate, double currentCost) {
        List<Future<SolutionResult<T, U>>> futures = new ArrayList<>(choices);
        Callable<SolutionResult<T, U>> callable = () -> evaluate(getNeighbour(candidate));
        for (int i = 0; i < choices; i++) {
            futures.add(executorService.submit(callable));
        }
        SolutionResult<T, U> best = getBestFromFutures(futures);
        double bestCost = best.getCost();
        logger.info("Run: " + run + ", neighbour cost: " + twoSf(bestCost) + ", current: " + twoSf(currentCost) + ", Solution: " + candidate);
        return bestCost <= currentCost ? best : null;
    }

    private SolutionResult<T, U> findBestSingleThreaded(int run, Solution<T> candidate, double currentCost) {
        SolutionResult<T, U> best = null;
        double bestCost = Double.MAX_VALUE;
        for (int i = 0; i < choices; i++) {
            Solution<T> neighbour = getNeighbour(candidate);
            SolutionResult<T, U> newResult = evaluate(neighbour);
            double newCost = newResult.getCost();
            if (newCost < bestCost) {
                bestCost = newCost;
                best = newResult;
            }
        }
        logger.info("Run: " + run + ", neighbour cost: " + twoSf(bestCost) + ", current: " + twoSf(currentCost) + ", Solution: " + candidate);
        return bestCost <= currentCost ? best : null;
    }

}
