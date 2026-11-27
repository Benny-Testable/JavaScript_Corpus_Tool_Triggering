package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LintLabLogTest {

    @Test
    public void loggedReturnsEveryMessageOnItsOwnLine() {
        LintLab lab = new LintLab();
        lab.log("first");
        lab.log("second");
        assertEquals("first\nsecond\n", lab.logged());
    }
}
