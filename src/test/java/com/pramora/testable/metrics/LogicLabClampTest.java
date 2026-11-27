package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogicLabClampTest {

    @Test
    public void aValueBelowTheRangeReturnsANonZeroMinimum() {
        assertEquals(5, new LogicLab().clamp(1, 5, 10));
    }
}
