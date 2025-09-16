import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTests {

    @Test
    void order_totals() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));
        assertEquals(Money.of(8.50), o.subtotal());
        assertEquals(Money.of(0.85), o.taxAtPercent(10));
        assertEquals(Money.of(9.35), o.totalWithTax(10));
    }

    @Test
    void add_twoAmounts_producesSum() {
        assertEquals(Money.of(5.00), Money.of(2.00).add(Money.of(3.00)));
    }

    @Test
    void multiply_byQuantity_producesProduct() {
        assertEquals(Money.of(7.50), Money.of(2.50).multiply(3));
    }

    @Test
    void of_roundsToTwoDecimals_halfUp() {
        assertEquals(Money.of(2.35), Money.of(2.345));
    }

    @Test
    void of_rejectsNegativeAmounts() {
        assertThrows(IllegalArgumentException.class, () -> Money.of(-0.01));
    }
}
