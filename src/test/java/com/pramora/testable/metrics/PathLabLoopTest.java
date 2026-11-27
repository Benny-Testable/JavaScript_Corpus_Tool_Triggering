package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PathLabLoopTest {

    @Test
    public void theLoopStopsBeforeItsLimit() {
        assertEquals(9, new PathLab().loopPaths(9));
    }
}
