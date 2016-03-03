package org.burnhams.optimiser.evaluators;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TargetStringEvaluatorTest {

    private final TargetStringEvaluator targetStringEvaluator = new TargetStringEvaluator("Hello World");

    private List<Character> stringToList(String input) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            result.add(input.charAt(i));
        }
        return result;
    }

    @Test
    public void shouldCalcCost() {
        double result = targetStringEvaluator.evaluate(stringToList("Hellp World"));
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldHaveLowerCostWhileCloser() {
        double result = targetStringEvaluator.evaluate(stringToList("dollrHleWo "));
        assertThat(result).isEqualTo(9);
        double result2 = targetStringEvaluator.evaluate(stringToList("HollrdleWo "));
        assertThat(result2).isEqualTo(8);
        double result3 = targetStringEvaluator.evaluate(stringToList("HellrdloWo "));
        assertThat(result3).isEqualTo(6);
    }

}