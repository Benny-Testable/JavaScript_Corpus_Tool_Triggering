package com.pramora.testable.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PricingSettingsTest {

    @Test
    public void missingOrBlankValuesAreNotPresent() {
        assertFalse(PricingSettings.isPresent(null));
        assertFalse(PricingSettings.isPresent("   "));
    }

    @Test
    public void textIsPresent() {
        assertTrue(PricingSettings.isPresent("configured"));
    }

    @Test
    public void tokenFlagFollowsTheEnvironment() {
        String value = System.getenv("PRICING_API_TOKEN");
        assertEquals(value != null && !value.isBlank(), PricingSettings.tokenConfigured());
    }
}
