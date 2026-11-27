package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PathLabTest {

    private final PathLab lab = new PathLab();

    @Test
    public void processMixedValuesTakesEveryBranch() {
        // -5 -> -5, 0 -> skipped, 7 -> 7, 500 -> 100  => 102
        assertEquals(102, lab.process(new int[] {-5, 0, 7, 500}));
    }

    @Test
    public void processClampsNegativeTotalToZero() {
        assertEquals(0, lab.process(new int[] {-50, -3}));
    }

    @Test
    public void processEmptyArrayIsZero() {
        assertEquals(0, lab.process(new int[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void processNullThrows() {
        lab.process(null);
    }

    @Test
    public void safeDivideHandlesZeroDivisor() {
        assertEquals(5, lab.safeDivide(10, 2));
        assertEquals(Integer.MAX_VALUE, lab.safeDivide(10, 0));
    }

    @Test
    public void nestedCoversAllFourPaths() {
        assertEquals(0, lab.nested(0, 1, 1));
        assertEquals(1, lab.nested(1, 0, 1));
        assertEquals(2, lab.nested(1, 1, 0));
        assertEquals(3, lab.nested(1, 1, 1));
    }

    @Test
    public void narrowWindowFlagsOnlyEleven() {
        assertEquals(7, lab.narrowWindow(7));
        assertEquals(-1, lab.narrowWindow(11));
        assertEquals(12, lab.narrowWindow(12));
    }

    @Test
    public void loopPathsSumsMultiplesOfThree() {
        assertEquals(0, lab.loopPaths(0));
        assertEquals(18, lab.loopPaths(10));
    }

    @Test
    public void narrowWindowExcludesItsLowerEdge() {
        assertEquals(10, lab.narrowWindow(10));
    }
}
