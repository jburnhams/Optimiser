package org.burnhams.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ColourUtilsTest {

    @Test
    public void shouldReturnSingleEntryAsIs() {
        int red = ColourUtils.getIntFromRGB(255, 0, 0);
        int average = ColourUtils.average(Collections.singletonList(red));
        assertThat(average).isEqualTo(red);
    }

    @Test
    public void shouldCalculateAverageRed() {
        List<Integer> reds = Arrays.asList(
                ColourUtils.getIntFromRGB(255, 0, 0),
                ColourUtils.getIntFromRGB(200, 10, 10),
                ColourUtils.getIntFromRGB(205, 5, 5),
                ColourUtils.getIntFromRGB(250, 50, 50),
                ColourUtils.getIntFromRGB(255, 10, 0),
                ColourUtils.getIntFromRGB(255, 0, 10),
                ColourUtils.getIntFromRGB(215, 0, 0)
        );
        int average = ColourUtils.average(reds);
        int[] rgbFromInt = ColourUtils.getRGBFromInt(average);
        assertThat(rgbFromInt).containsExactly(235, 14, 15);
    }

}