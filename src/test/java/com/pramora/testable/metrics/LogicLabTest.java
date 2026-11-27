package com.pramora.testable.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LogicLabTest {

    private final LogicLab lab = new LogicLab();

    @Test
    public void clampAssertsExactBoundaries() {
        assertEquals(0, lab.clamp(-1, 0, 10));
        assertEquals(0, lab.clamp(0, 0, 10));
        assertEquals(10, lab.clamp(10, 0, 10));
        assertEquals(10, lab.clamp(11, 0, 10));
        assertEquals(5, lab.clamp(5, 0, 10));
    }

    @Test
    public void leapYearRules() {
        assertTrue(lab.isLeapYear(2000));
        assertFalse(lab.isLeapYear(1900));
        assertTrue(lab.isLeapYear(2024));
        assertFalse(lab.isLeapYear(2023));
    }

    @Test
    public void gradeBoundaries() {
        assertEquals("A", lab.grade(90));
        assertEquals("B", lab.grade(89));
        assertEquals("B", lab.grade(80));
        assertEquals("C", lab.grade(70));
        assertEquals("F", lab.grade(69));
    }

    @Test
    public void daysInMonthCoversEveryCase() {
        assertEquals(29, lab.daysInMonth(2, 2024));
        assertEquals(28, lab.daysInMonth(2, 2023));
        assertEquals(30, lab.daysInMonth(4, 2024));
        assertEquals(31, lab.daysInMonth(1, 2024));
        assertEquals(-1, lab.daysInMonth(13, 2024));
        assertEquals(-1, lab.daysInMonth(0, 2024));
    }

    @Test
    public void decemberIsTheLastMonth() {
        assertEquals(31, lab.daysInMonth(12, 2024));
    }
}
