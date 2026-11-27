package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CognitiveLabTest {

    private final CognitiveLab lab = new CognitiveLab();

    @Test
    public void scoreMatrixWalksNestedBranches() {
        int[][] grid = {{5, 6, -1}, {7, 8, 0}};
        // row 0: 5 > 2 even/even -> +5 ; 6 > 2 even/odd -> -1 ; -1 < 0 -> continue
        // row 1: 7 > 2, odd row, j == 0 -> nothing ; 8 > 2, odd row, previous 7 > 2 -> +2 ; 0 -> nothing
        assertEquals(6, lab.scoreMatrix(grid, 2));
    }

    @Test
    public void findFirstPairReturnsEncodedIndices() {
        assertEquals(102, lab.findFirstPair(new int[] {1, 5, 3, 4}, 8));
        assertEquals(-1, lab.findFirstPair(new int[] {1, 2, 3}, 100));
        assertEquals(-1, lab.findFirstPair(new int[] {50, 60, 1}, 10));
    }

    @Test
    public void collatzStepsCountsRecursiveCalls() {
        assertEquals(0, lab.collatzSteps(1));
        assertEquals(1, lab.collatzSteps(2));
        assertEquals(6, lab.collatzSteps(10));
    }

    @Test
    public void scoreMatrixIgnoresACellEqualToTheThreshold() {
        assertEquals(0, lab.scoreMatrix(new int[][] {{2}}, 2));
    }

    @Test
    public void scoreMatrixHandlesTheFirstColumnOfAnOddRow() {
        assertEquals(5, lab.scoreMatrix(new int[][] {{5}, {5}}, 2));
    }

    @Test
    public void scoreMatrixAlternatesByRowAndColumn() {
        int[][] grid = {{5, 5, 5}, {5, 5, 5}, {5, 5, 5}};
        assertEquals(22, lab.scoreMatrix(grid, 2));
    }
}
