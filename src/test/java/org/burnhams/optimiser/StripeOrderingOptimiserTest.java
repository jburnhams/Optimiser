package org.burnhams.optimiser;

import org.burnhams.BaseImageTest;
import org.burnhams.imaging.SimpleImage;
import org.burnhams.optimiser.algorithms.SimulatedAnnealing;
import org.burnhams.optimiser.converters.OrderedStripeConverter;
import org.burnhams.optimiser.evaluators.TargetImageEvaluator;
import org.burnhams.optimiser.neighbourhood.RandomSwapNeighbour;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionResult;
import org.burnhams.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StripeOrderingOptimiserTest extends BaseImageTest {

    private OrderedStripeConverter orderedStripeConverter;
    private TargetImageEvaluator evaluator;
    private Configuration configuration = new TestConfiguration();

    @Before
    public void setUp() throws Exception {
        evaluator = new TargetImageEvaluator(testImage);
        orderedStripeConverter = new OrderedStripeConverter(testImage);
    }

    @Test
    public void shouldOrderStripesToMatchSourceWithSa() throws IOException {
        SimulatedAnnealing<Integer, SimpleImage> optimiser = new SimulatedAnnealing<>(evaluator, orderedStripeConverter, configuration, new RandomSwapNeighbour<>(configuration));
        List<Integer> stripes = new ArrayList<>(WIDTH + HEIGHT);
        for (int i = 0; i < WIDTH + HEIGHT; i++) {
            stripes.add(i);
        }
        Solution<Integer> candidate = new Solution<>(stripes);
        candidate.shuffle();
        SolutionResult<Integer, SimpleImage> result = optimiser.optimise(candidate);
        ImageUtils.saveImage(result.getSolutionOutput().getBufferedImage(), TEST_DIR + "stripesa", "png");
    }


}
