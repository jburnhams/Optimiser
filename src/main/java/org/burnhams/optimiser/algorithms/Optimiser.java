package org.burnhams.optimiser.algorithms;

import org.apache.log4j.Logger;
import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.Evaluator;
import org.burnhams.optimiser.converters.SolutionConverter;
import org.burnhams.optimiser.neighbourhood.NeighbourhoodFunction;
import org.burnhams.optimiser.neighbourhood.RandomSwapNeighbour;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import static java.util.Collections.singletonList;

public abstract class Optimiser<T, U> {

    private static Logger logger = Logger.getLogger(Optimiser.class);
    protected final Configuration configuration;
    private final Random random = new Random();
    private final Evaluator<U> evaluator;
    private final SolutionConverter<T, U> solutionConverter;
    private final List<NeighbourhoodFunction<T>> neighbourhoodFunctions;

    protected Optimiser(Configuration configuration, Evaluator<U> evaluator, SolutionConverter<T, U> solutionConverter, NeighbourhoodFunction<T> neighbourhoodFunction) {
        this(configuration, evaluator, solutionConverter, singletonList(neighbourhoodFunction));
    }

    protected Optimiser(Configuration configuration, Evaluator<U> evaluator, SolutionConverter<T, U> solutionConverter, List<NeighbourhoodFunction<T>> neighbourhoodFunctions) {
        this.configuration = configuration;
        this.evaluator = evaluator;
        this.solutionConverter = solutionConverter;
        if (neighbourhoodFunctions == null || neighbourhoodFunctions.isEmpty()) {
            this.neighbourhoodFunctions = singletonList(new RandomSwapNeighbour<>(configuration));
        } else {
            this.neighbourhoodFunctions = neighbourhoodFunctions;
        }
    }

    private static <U> U safeGet(Future<U> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get solution from future", e);
        }
    }

    public static <T, U> SolutionResult<T, U> getBestFromFutures(Collection<Future<SolutionResult<T, U>>> futureList) {
        SolutionResult<T, U> best = null;
        double bestCost = -1;
        for (Future<SolutionResult<T, U>> future : futureList) {
            SolutionResult<T, U> solution = safeGet(future);
            double newCost = solution.getCost();
            if (best == null || newCost < bestCost) {
                bestCost = newCost;
                best = solution;
            }
        }
        return best;
    }

    public abstract SolutionResult<T, U> optimise(Solution<T> candidate);


    protected SolutionResult<T, U> evaluate(Solution<T> solution) {
        U solutionOutput = solutionConverter.convert(solution);
        double cost = evaluator.evaluate(solutionOutput);
        return new SolutionResult<>(solution, solutionOutput, cost);
    }

    protected Solution<T> getNeighbour(Solution<T> candidate) {
        int size = neighbourhoodFunctions.size();
        int neighbourFunction = 0;
        if (size > 1) {
            neighbourFunction = random.nextInt(size);
        }
        Solution<T> neighbour = neighbourhoodFunctions.get(neighbourFunction).getNeighbour(candidate);
        //logger.debug("Neighbour: "+neighbour);
        return neighbour;
    }

}
