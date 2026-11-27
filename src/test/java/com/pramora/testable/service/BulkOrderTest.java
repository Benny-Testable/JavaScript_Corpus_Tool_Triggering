package com.pramora.testable.service;

import static org.junit.Assert.assertTrue;

import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import org.junit.Test;

public class BulkOrderTest {

    @Test
    public void tenUnitsMakeABulkOrder() {
        Order order = new Order("O-20", new Customer("C-20", "Test", LoyaltyTier.SILVER, 5));
        order.addLine(new OrderLine("SKU-20", 10, 100L));
        assertTrue(new PricingService(new DiscountPolicy()).isBulkOrder(order));
    }
}
