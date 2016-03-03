package org.burnhams.optimiser;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.burnhams.optimiser.algorithms.HillClimber;
import org.burnhams.optimiser.algorithms.Optimiser;
import org.burnhams.optimiser.algorithms.SimulatedAnnealing;
import org.burnhams.optimiser.evaluators.TargetStringEvaluator;
import org.burnhams.optimiser.neighbourhood.RandomSwapNeighbour;
import org.burnhams.optimiser.solutions.Solution;
import org.burnhams.optimiser.solutions.SolutionListOutput;
import org.burnhams.optimiser.solutions.SolutionResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StringOptimiserTest {

    public static final String HELLO_WORLD = "Hello World";
    private static Logger logger = Logger.getLogger(StringOptimiserTest.class);
    private final SolutionListOutput<Character> solutionListOutput = new SolutionListOutput<>();
    private final TargetStringEvaluator targetStringEvaluator = new TargetStringEvaluator(HELLO_WORLD);
    private Configuration configuration = new Configuration() {
        @Override
        public long getMaxIterations() {
            return 10000;
        }

        @Override
        public int getThreads() {
            return 1;
        }

        public double getStartingTemperature() {
            return 100;
        }

        @Override
        public int getHillClimbChoices() {
            return 100;
        }

        @Override
        public int getHillClimbMaxNonImprovingMoves() {
            return 5;
        }
    };
    private final RandomSwapNeighbour<Character> neighbour = new RandomSwapNeighbour<>(configuration);

    public static List<Character> stringToList(String input) {
        List<Character> chars = new ArrayList<>(input.length());
        for (int i = 0; i < input.length(); i++) {
            chars.add(input.charAt(i));
        }
        return chars;
    }

    @Test
    public void shouldRearrangeHelloWorldUsingHillClimber() {
        HillClimber<Character, List<Character>> hillClimber = new HillClimber<>(targetStringEvaluator, solutionListOutput, configuration, neighbour);
        shouldRearrangeHelloWorld(hillClimber);
    }

    @Test

    public void shouldRearrangeHelloWorldUsingSimulatedAnnealing() {
        SimulatedAnnealing<Character, List<Character>> sa = new SimulatedAnnealing<>(targetStringEvaluator, solutionListOutput, configuration, neighbour);
        shouldRearrangeHelloWorld(sa);
    }

    public void shouldRearrangeHelloWorld(Optimiser<Character, List<Character>> optimiser) {
        String target = HELLO_WORLD;
        List<Character> chars = stringToList(target);
        List<Character> correct = new ArrayList<>(chars);
        Collections.shuffle(chars);
        SolutionResult<Character, List<Character>> solution = optimiser.optimise(new Solution<>(chars));
        assertThat(solution.getSolutionOutput()).isEqualTo(correct);
    }

    @Test
    public void shouldFailToRearrangeExactly() {
        String target = RandomStringUtils.randomNumeric(10);
        String actual = RandomStringUtils.randomNumeric(10);
        List<Character> correct = stringToList(target);
        logger.info("Target: " + correct);
        List<Character> chars = stringToList(actual);
        HillClimber<Character, List<Character>> hillClimber = new HillClimber<>(new TargetStringEvaluator(target), solutionListOutput, configuration, neighbour);
        SolutionResult<Character, List<Character>> solution = hillClimber.optimise(new Solution<>(chars));
        assertThat(solution.getSolutionOutput()).isNotEqualTo(correct);
    }


}
