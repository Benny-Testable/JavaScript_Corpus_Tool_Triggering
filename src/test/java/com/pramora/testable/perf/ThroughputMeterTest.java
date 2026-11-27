package com.pramora.testable.perf;

import static org.junit.Assert.assertEquals;

import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import java.util.List;
import org.junit.Test;

public class ThroughputMeterTest {

    private static Order order(String id, int quantity, long cents) {
        Order order = new Order(id, new Customer("C-1", "Demo", LoyaltyTier.GOLD, 1));
        order.addLine(new OrderLine("SKU-1", quantity, cents));
        return order;
    }

    @Test
    public void batchCentsAddsEveryOrder() {
        assertEquals(700L, ThroughputMeter.batchCents(List.of(order("O-1", 2, 100L), order("O-2", 1, 500L))));
        assertEquals(0L, ThroughputMeter.batchCents(List.of()));
    }

    @Test
    public void ordersPerSecondUsesTheElapsedTime() {
        assertEquals(50L, ThroughputMeter.ordersPerSecond(100, 2_000_000_000L));
        assertEquals(1_000_000_000L, ThroughputMeter.ordersPerSecond(1, 1L));
    }

    @Test
    public void ordersPerSecondIsZeroWithoutElapsedTime() {
        assertEquals(0L, ThroughputMeter.ordersPerSecond(100, 0L));
        assertEquals(0L, ThroughputMeter.ordersPerSecond(100, -1L));
    }
}
