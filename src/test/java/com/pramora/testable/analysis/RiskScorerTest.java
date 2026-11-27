package com.pramora.testable.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import org.junit.Test;

public class RiskScorerTest {

    private final RiskScorer scorer = new RiskScorer();

    @Test
    public void nullOrderScoresZero() {
        assertEquals(0, scorer.score(null));
    }

    @Test
    public void newCustomerLargeStandardOrderScoresHigh() {
        Order o = new Order("O-1", new Customer("C-1", "New", LoyaltyTier.STANDARD, 0));
        o.addLine(new OrderLine("SKU-1", 1, 60000L));
        assertEquals(70, scorer.score(o));
        assertTrue(scorer.requiresReview(o));
    }

    @Test
    public void establishedCustomerSmallOrderScoresLow() {
        Order o = new Order("O-2", new Customer("C-2", "Old", LoyaltyTier.GOLD, 40));
        o.addLine(new OrderLine("SKU-1", 1, 1000L));
        assertEquals(0, scorer.score(o));
        assertFalse(scorer.requiresReview(o));
    }

    @Test
    public void hugeQuantityAddsRisk() {
        Order o = new Order("O-3", new Customer("C-3", "Old", LoyaltyTier.GOLD, 40));
        o.addLine(new OrderLine("SKU-1", 60, 100L));
        assertEquals(10, scorer.score(o));
    }

    @Test
    public void scoreIsCappedAtOneHundred() {
        Order o = new Order("O-4", new Customer("C-4", "New", LoyaltyTier.STANDARD, 0));
        for (int i = 0; i < 6; i++) {
            o.addLine(new OrderLine("SKU-" + i, 60, 20000L));
        }
        assertEquals(100, scorer.score(o));
    }

    private int scoreFor(int orderCount, int quantity, long unitCents) {
        Order o = new Order("O-5", new Customer("C-5", "Edge", LoyaltyTier.GOLD, orderCount));
        o.addLine(new OrderLine("SKU-1", quantity, unitCents));
        return scorer.score(o);
    }

    @Test
    public void newCustomerValueRiskStartsAboveTheThreshold() {
        assertEquals(30, scoreFor(1, 1, 50_000L));
        assertEquals(55, scoreFor(1, 1, 50_001L));
    }

    @Test
    public void establishedCustomerValueRiskStartsAboveTheThreshold() {
        assertEquals(0, scoreFor(2, 1, 200_000L));
        assertEquals(20, scoreFor(2, 1, 200_001L));
    }

    @Test
    public void quantityRiskStartsAboveFifty() {
        assertEquals(0, scoreFor(40, 50, 100L));
        assertEquals(10, scoreFor(40, 51, 100L));
    }

    @Test
    public void freeItemsAddRisk() {
        assertEquals(5, scoreFor(40, 1, 0L));
    }

    @Test
    public void reviewStartsAtFifty() {
        Order o = new Order("O-6", new Customer("C-6", "Old", LoyaltyTier.GOLD, 40));
        for (int i = 0; i < 3; i++) {
            o.addLine(new OrderLine("SKU-" + i, 51, 2_000L));
        }
        assertEquals(50, scorer.score(o));
        assertTrue(scorer.requiresReview(o));
    }
}
