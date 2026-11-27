package com.pramora.testable.service;

import static org.junit.Assert.assertEquals;

import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import org.junit.Test;

public class DiscountPolicyTest {

    private final DiscountPolicy policy = new DiscountPolicy();

    private long discount(LoyaltyTier tier, int orderCount) {
        Order order = new Order("O-1", new Customer("C-1", "Test", tier, orderCount));
        order.addLine(new OrderLine("SKU-1", 1, 10_000L));
        return policy.discountCents(order);
    }

    @Test
    public void noOrderMeansNoDiscount() {
        assertEquals(0L, policy.discountCents(null));
    }

    @Test
    public void theLoyaltyBonusStartsAtTwentyFiveOrders() {
        assertEquals(700L, discount(LoyaltyTier.GOLD, 24));
        assertEquals(900L, discount(LoyaltyTier.GOLD, 25));
    }

    @Test
    public void standardCustomersEarnOnlyTheLoyaltyBonus() {
        assertEquals(0L, discount(LoyaltyTier.STANDARD, 2));
        assertEquals(0L, discount(LoyaltyTier.STANDARD, 3));
        assertEquals(200L, discount(LoyaltyTier.STANDARD, 25));
    }

    @Test
    public void tierRateAppliesToNewCustomers() {
        assertEquals(300L, discount(LoyaltyTier.SILVER, 0));
        assertEquals(1200L, discount(LoyaltyTier.PLATINUM, 0));
    }
}
