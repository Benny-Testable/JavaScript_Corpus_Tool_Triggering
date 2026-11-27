package com.pramora.testable.app;

import com.pramora.testable.analysis.RiskScorer;
import com.pramora.testable.model.Customer;
import com.pramora.testable.model.LoyaltyTier;
import com.pramora.testable.model.Order;
import com.pramora.testable.model.OrderLine;
import com.pramora.testable.service.DiscountPolicy;
import com.pramora.testable.service.PricingService;
import com.pramora.testable.util.InputSanitizer;

/**
 * Prices one demo order. {@link #summary(String)} returns the summary as text and
 * {@link #main(String[])} prints it, taking the order reference from the command line.
 */
public final class Main {

    private static final int DEMO_ORDER_COUNT = 12;
    private static final int FIRST_LINE_QTY = 2;
    private static final long FIRST_LINE_CENTS = 1999L;
    private static final long SECOND_LINE_CENTS = 4999L;

    private Main() {
    }

    /** Command-line entry point, run as {@code java -jar <artifact> O-1234}. */
    public static void main(String[] args) {
        System.out.print(summary(args.length == 0 ? null : args[0]));
    }

    /** Builds the demo order for {@code reference} and returns its priced summary. */
    public static String summary(String reference) {
        String id = reference == null || reference.isEmpty() ? "O-DEMO" : InputSanitizer.sanitize(reference);
        Order order = new Order(id, new Customer("C-1", "Demo", LoyaltyTier.GOLD, DEMO_ORDER_COUNT));
        order.addLine(new OrderLine("SKU-1", FIRST_LINE_QTY, FIRST_LINE_CENTS));
        order.addLine(new OrderLine("SKU-2", 1, SECOND_LINE_CENTS));

        PricingService pricing = new PricingService(new DiscountPolicy());
        RiskScorer scorer = new RiskScorer();

        StringBuilder out = new StringBuilder();
        out.append("order      : ").append(order.getId()).append('\n');
        out.append("placed     : ").append(order.getPlacedOn()).append('\n');
        out.append("lines      : ").append(order.lineCount()).append('\n');
        out.append("subtotal   : ").append(order.subtotalCents()).append('\n');
        out.append("largest    : ").append(order.largestLine().map(OrderLine::getSku).orElse("-")).append('\n');
        out.append("total      : ").append(pricing.priceCents(order)).append('\n');
        out.append("risk score : ").append(scorer.score(order)).append('\n');
        out.append("review     : ").append(scorer.requiresReview(order)).append('\n');
        return out.toString();
    }
}
