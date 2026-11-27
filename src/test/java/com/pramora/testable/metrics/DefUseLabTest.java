package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefUseLabTest {

    private final DefUseLab lab = new DefUseLab();

    @Test
    public void definitionsCoversBothBranches() {
        // a=5,b=1: x=6,y=2 -> x>y -> x=4 -> z=6
        assertEquals(6, lab.definitions(5, 1));
        // a=0,b=5: x=1,y=10 -> y=9 -> z=10
        assertEquals(10, lab.definitions(0, 5));
    }

    @Test
    public void multipleDefsUsesLastDefinition() {
        assertEquals(4, lab.multipleDefs(3));
        assertEquals(14, lab.multipleDefs(6));
    }

    @Test
    public void crossFunctionUsesHelperResults() {
        // helper(2)=5 ; helper(5)=14 ; 5+14=19
        assertEquals(19, lab.crossFunction(2));
    }

    @Test
    public void unusedDefinitionStillReturnsLive() {
        assertEquals(8, lab.unusedDefinition(7));
    }

    @Test
    public void loopUseSumsPositiveValues() {
        assertEquals(9, lab.loopUse(new int[] {4, -1, 5, 0}));
    }

    @Test
    public void multipleDefsDoublesOnlyAboveFive() {
        assertEquals(6, lab.multipleDefs(5));
    }
}
