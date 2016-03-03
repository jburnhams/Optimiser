package org.burnhams.optimiser.evaluators;

import org.burnhams.optimiser.Evaluator;

import java.util.List;

public class TargetStringEvaluator implements Evaluator<List<Character>> {

    private final String targetString;

    public TargetStringEvaluator(String targetString) {
        this.targetString = targetString;
    }

    @Override
    public double evaluate(List<Character> t) {
        double cost = 0;
        for (int i = 0; i < targetString.length(); i++) {
            if (!t.get(i).equals(targetString.charAt(i))) {
                cost++;
            }
        }
        return cost;
    }
}
