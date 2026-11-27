package com.pramora.testable.perf;

import com.pramora.testable.model.Order;
import java.util.List;

/** Throughput figures for a batch of priced orders. */
public final class ThroughputMeter {

    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NO_TIME = 0L;

    private ThroughputMeter() {
    }

    /** Sum of the subtotals of every order in the batch, in cents. */
    public static long batchCents(List<Order> orders) {
        long total = 0L;
        for (Order order : orders) {
            total += order.subtotalCents();
        }
        return total;
    }

    /** Whole orders processed per second; zero when no time has elapsed. */
    public static long ordersPerSecond(int orders, long elapsedNanos) {
        if (elapsedNanos <= NO_TIME) {
            return NO_TIME;
        }
        return orders * NANOS_PER_SECOND / elapsedNanos;
    }
}
