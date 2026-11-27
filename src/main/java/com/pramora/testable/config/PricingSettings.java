package com.pramora.testable.config;

/**
 * Settings read from the environment. The token is only checked for presence: its
 * value is never returned, printed, logged or written.
 */
public final class PricingSettings {

    private PricingSettings() {
    }

    /** True when the pricing API token is configured. */
    public static boolean tokenConfigured() {
        return isPresent(System.getenv("PRICING_API_TOKEN"));
    }

    static boolean isPresent(String value) {
        return value != null && !value.isBlank();
    }
}
