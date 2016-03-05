package org.burnhams;

import org.burnhams.imaging.SimpleImage;
import org.burnhams.optimiser.Configuration;
import org.burnhams.optimiser.PropertiesConfiguration;
import org.burnhams.optimiser.algorithms.SimulatedAnnealing;
import org.burnhams.optimiser.converters.OrderedStripeConverter;
import org.burnhams.optimiser.evaluators.TargetImageEvaluator;
import org.burnhams.optimiser.neighbourhood.RandomSwapNeighbour;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;
import org.burnhams.utils.ImageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;


    public static void main(String[] args) throws IOException {
        String filename = "DSC05771";
        SimpleImage simpleImage = new SimpleImage(ImageUtils.getPixels("data/" + filename + ".jpg", WIDTH, HEIGHT));
        OrderedStripeConverter orderedStripeConverter;
        TargetImageEvaluator evaluator;
        Configuration configuration = new PropertiesConfiguration();
        evaluator = new TargetImageEvaluator(simpleImage);
        orderedStripeConverter = new OrderedStripeConverter(simpleImage);
        SimulatedAnnealing<Integer, SimpleImage> optimiser = new SimulatedAnnealing<>(evaluator, orderedStripeConverter, configuration, new RandomSwapNeighbour<>(configuration));
        List<Integer> stripes = new ArrayList<>(WIDTH + HEIGHT);
        for (int i = 0; i < WIDTH + HEIGHT; i++) {
            stripes.add(i);
        }
        Solution<Integer> candidate = new Solution<>(stripes);
        candidate.shuffle();
        SolutionResult<Integer, SimpleImage> result = optimiser.optimise(candidate);
        ImageUtils.saveImage(result.getSolutionOutput().getBufferedImage(), "data/" + filename, "png");
    }
}
