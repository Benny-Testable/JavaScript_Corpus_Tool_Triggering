package com.pramora.testable.analysis;

import static org.junit.Assert.assertEquals;

import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import org.junit.Test;

public class DuplicateProcessorBBoundaryTest {

    private long processed(long unitCents) {
        Order order = new Order("O-12", new Customer("C-12", "Test", LoyaltyTier.SILVER, 5));
        order.addLine(new OrderLine("SKU-12", 1, unitCents));
        return new DuplicateProcessorB().process(order);
    }

    @Test
    public void bulkReliefStartsAboveTheThreshold() {
        assertEquals(100_000L, processed(100_000L));
        assertEquals(114_000L, processed(120_000L));
    }

    @Test
    public void totalsRoundToTheNearestFiveCents() {
        assertEquals(10L, processed(10L));
        assertEquals(5L, processed(6L));
        assertEquals(10L, processed(7L));
        assertEquals(10L, processed(9L));
    }
}
