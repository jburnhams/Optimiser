package org.burnhams.optimiser.algorithms;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {

    private Acceptance acceptance = new Acceptance();

    @Test
    public void shouldAlwaysAcceptImprovement() {
        boolean accepted = acceptance.isAccepted(100, 0, 10, 20, 9);
        assertThat(accepted).isTrue();
    }


}