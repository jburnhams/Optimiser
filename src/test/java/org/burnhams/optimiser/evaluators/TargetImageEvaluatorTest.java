package org.burnhams.optimiser.evaluators;

import org.burnhams.BaseImageTest;
import org.burnhams.imaging.SimpleImage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class TargetImageEvaluatorTest extends BaseImageTest {
    private TargetImageEvaluator evaluator;

    @Before
    public void setUp() throws Exception {
        evaluator = new TargetImageEvaluator(testImage);
    }

    @Test
    public void shouldBeZeroCostForEqualImages() throws IOException {
        SimpleImage other = getSimpleImage();
        double cost = evaluator.evaluate(other);
        assertThat(cost).isEqualTo(0);
    }

    @Test
    public void shouldHaveCostForDifferentImages() throws IOException {
        SimpleImage other = getSimpleImage();
        other.setPixel(10, 10, 0);
        double cost = evaluator.evaluate(other);
        assertThat(cost).isGreaterThan(0);
    }

}