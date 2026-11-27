package com.pramora.testable.app;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class MainTest {

    @Test
    public void summaryUsesTheGivenReference() {
        String text = Main.summary("O-77");
        assertTrue(text.contains("order      : O-77"));
        assertTrue(text.contains("lines      : 2"));
    }

    @Test
    public void summaryFallsBackToTheDemoReference() {
        assertTrue(Main.summary(null).contains("order      : O-DEMO"));
        assertTrue(Main.summary("").contains("order      : O-DEMO"));
    }

    @Test
    public void mainPrintsTheSummaryForTheArgument() {
        assertTrue(printedBy("O-88").contains("order      : O-88"));
    }

    @Test
    public void mainWithoutArgumentsPrintsTheDemoOrder() {
        assertTrue(printedBy().contains("order      : O-DEMO"));
    }

    private static String printedBy(String... args) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true));
        try {
            Main.main(args);
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }
}
