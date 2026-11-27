package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CognitiveLabPairTest {

    @Test
    public void searchContinuesWhenASumEqualsTheCutOff() {
        assertEquals(203, new CognitiveLab().findFirstPair(new int[] {5, 3, 1, 1}, 2));
    }
}
