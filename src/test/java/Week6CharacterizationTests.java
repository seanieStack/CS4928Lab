import smells.OrderManagerGod;
import checkout.CheckoutService;
import factory.ProductFactory;
import pricing.*;
import common.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Week6CharacterizationTests {

    @Test void no_discount_cash_payment() {
        String oldReceipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "NONE", false);
        assertTrue(oldReceipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(oldReceipt.contains("Subtotal: 3.80"));
        assertTrue(oldReceipt.contains("Tax (10%): 0.38"));
        assertTrue(oldReceipt.contains("Total: 4.18"));

        var pricing = new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        String newReceipt = checkout.checkout("ESP+SHOT+OAT", 1);

        // both produce identical output
        assertEquals(oldReceipt, newReceipt);
    }

    @Test void loyalty_discount_card_payment() {
        String oldReceipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);

        assertTrue(oldReceipt.contains("Subtotal: 7.80"));
        assertTrue(oldReceipt.contains("Discount: -0.39"));
        assertTrue(oldReceipt.contains("Tax (10%): 0.74"));
        assertTrue(oldReceipt.contains("Total: 8.15"));

        var pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        String newReceipt = checkout.checkout("LAT+L", 2);

        // both produce identical output
        assertEquals(oldReceipt, newReceipt);
    }

    @Test void coupon_fixed_amount_and_qty_clamp() {
        String oldReceipt = OrderManagerGod.process("ESP+SHOT", 0, "WALLET", "COUPON1", false);
        assertTrue(oldReceipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(oldReceipt.contains("Subtotal: 3.30"));
        assertTrue(oldReceipt.contains("Discount: -1.00"));
        assertTrue(oldReceipt.contains("Tax (10%): 0.23"));
        assertTrue(oldReceipt.contains("Total: 2.53"));

        // refactored implementation
        var pricing = new PricingService(new FixedCouponDiscount(Money.of(1.00)), new FixedRateTaxPolicy(10));
        var printer = new ReceiptPrinter();
        var checkout = new CheckoutService(new ProductFactory(), pricing, printer, 10);
        String newReceipt = checkout.checkout("ESP+SHOT", 0);

        // both produce identical output
        assertEquals(oldReceipt, newReceipt);
    }
}
