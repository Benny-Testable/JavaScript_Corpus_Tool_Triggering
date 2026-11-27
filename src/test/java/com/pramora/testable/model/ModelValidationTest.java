package com.pramora.testable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import org.junit.Test;

/** The argument checks of the model classes, each at the value where it starts to refuse. */
public class ModelValidationTest {

    private static Customer customer() {
        return new Customer("C-1", "Demo", LoyaltyTier.GOLD, 0);
    }

    @Test
    public void customerKeepsItsValues() {
        Customer customer = customer();
        assertEquals("C-1", customer.getId());
        assertEquals("Demo", customer.getName());
        assertEquals(0, customer.getOrderCount());
    }

    @Test
    public void customerNeedsAnIdATierAndANonNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "Demo", LoyaltyTier.GOLD, 0));
        assertThrows(IllegalArgumentException.class, () -> new Customer("", "Demo", LoyaltyTier.GOLD, 0));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C-1", "Demo", null, 0));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C-1", "Demo", LoyaltyTier.GOLD, -1));
    }

    @Test
    public void orderLineAcceptsTheSmallestQuantityAndAFreeItem() {
        OrderLine line = new OrderLine("SKU-1", 1, 0L);
        assertEquals(1, line.getQuantity());
        assertEquals(0L, line.getUnitPriceCents());
    }

    @Test
    public void orderLineRefusesBadValues() {
        assertThrows(IllegalArgumentException.class, () -> new OrderLine(null, 1, 100L));
        assertThrows(IllegalArgumentException.class, () -> new OrderLine("", 1, 100L));
        assertThrows(IllegalArgumentException.class, () -> new OrderLine("SKU-1", 0, 100L));
        assertThrows(IllegalArgumentException.class, () -> new OrderLine("SKU-1", 1, -1L));
    }

    @Test
    public void orderNeedsAnIdAndACustomer() {
        assertThrows(IllegalArgumentException.class, () -> new Order(null, customer()));
        assertThrows(IllegalArgumentException.class, () -> new Order("", customer()));
        assertThrows(IllegalArgumentException.class, () -> new Order("O-1", null));
    }

    @Test
    public void orderDateFallsBackWhenMissing() {
        LocalDate chosen = LocalDate.of(2025, 3, 4);
        assertEquals(chosen, new Order("O-1", customer(), chosen).getPlacedOn());
        assertEquals(LocalDate.of(2026, 1, 15), new Order("O-2", customer(), null).getPlacedOn());
    }

    @Test
    public void orderStatusStartsAsDraftAndCanChange() {
        Order order = new Order("O-1", customer());
        assertEquals(OrderStatus.DRAFT, order.getStatus());
        order.setStatus(OrderStatus.SUBMITTED);
        assertEquals(OrderStatus.SUBMITTED, order.getStatus());
    }

    @Test
    public void orderRefusesAMissingStatusOrLine() {
        Order order = new Order("O-1", customer());
        assertThrows(IllegalArgumentException.class, () -> order.setStatus(null));
        assertThrows(IllegalArgumentException.class, () -> order.addLine(null));
        assertEquals(0, order.lineCount());
    }
}
