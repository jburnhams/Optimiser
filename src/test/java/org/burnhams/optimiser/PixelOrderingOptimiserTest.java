package org.burnhams.optimiser;

import org.burnhams.BaseImageTest;
import org.burnhams.imaging.SimpleImage;
import org.burnhams.optimiser.algorithms.HillClimber;
import org.burnhams.optimiser.algorithms.SimulatedAnnealing;
import org.burnhams.optimiser.converters.OrderedPixelConverter;
import org.burnhams.optimiser.evaluators.TargetImageEvaluator;
import org.burnhams.optimiser.neighbourhood.RandomSwapNeighbour;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;
import org.burnhams.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PixelOrderingOptimiserTest extends BaseImageTest {

    private final OrderedPixelConverter orderedPixelConverter = new OrderedPixelConverter(WIDTH, HEIGHT);
    private TargetImageEvaluator evaluator;
    private Configuration configuration = new TestConfiguration();

    @Before
    public void setUp() throws Exception {
        evaluator = new TargetImageEvaluator(testImage);
    }

    @Test
    public void shouldReorderPixelsToMatchSourceWithSa() throws IOException {
        SimulatedAnnealing<Integer, SimpleImage> optimiser = new SimulatedAnnealing<>(evaluator, orderedPixelConverter, configuration, new RandomSwapNeighbour<>(configuration));
        Solution<Integer> candidate = new Solution<>(testImage.getPixels());
        candidate.shuffle();
        SolutionResult<Integer, SimpleImage> result = optimiser.optimise(candidate);
        ImageUtils.saveImage(result.getSolutionOutput().getBufferedImage(), TEST_DIR + "resultsa", "png");
    }

    @Test
    public void shouldReorderPixelsToMatchSourceWithHillClimb() throws IOException {
        HillClimber<Integer, SimpleImage> optimiser = new HillClimber<>(evaluator, orderedPixelConverter, configuration, new RandomSwapNeighbour<>(configuration));
        Solution<Integer> candidate = new Solution<>(testImage.getPixels());
        candidate.shuffle();
        SolutionResult<Integer, SimpleImage> result = optimiser.optimise(candidate);
        ImageUtils.saveImage(result.getSolutionOutput().getBufferedImage(), TEST_DIR + "resulthc", "png");
    }


}
