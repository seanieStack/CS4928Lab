import smells.OrderManagerGod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountPolicyCharacterizationTests {
    @Test void loyal5_discount_applied() {
        String receipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);
        assertTrue(receipt.contains("Discount: -0.39"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test void coupon1_fixed_discount_applied() {
        String receipt = OrderManagerGod.process("ESP+SHOT", 1, "CASH", "COUPON1", false);
        assertTrue(receipt.contains("Discount: -1.00"));
        assertTrue(receipt.contains("Total: 2.53"));
    }

    @Test void unknown_discount_code_no_discount() {
        String receipt = OrderManagerGod.process("ESP", 1, null, "INVALID", false);
        assertFalse(receipt.contains("Discount:"));
        assertTrue(receipt.contains("Subtotal: 2.50"));
        assertTrue(receipt.contains("Tax (10%): 0.25"));
        assertTrue(receipt.contains("Total: 2.75"));
    }

    @Test void none_discount_code_no_discount() {
        String receipt = OrderManagerGod.process("ESP", 1, null, "NONE", false);
        assertFalse(receipt.contains("Discount:"));
        assertTrue(receipt.contains("Total: 2.75"));
    }
}
