import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTests {

    @Test
    void emptyOrder_subtotalIsZero() {
        var o = new Order(1);
        assertEquals(Money.zero(), o.subtotal());
    }

    @Test
    void addItems_updatesItemsCountAndSubtotal() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);

        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));

        assertEquals(2, o.getItems().size());
        assertEquals(Money.of(8.50), o.subtotal());
    }

    @Test
    void taxAtPercent_computesExpectedTax() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));

        assertEquals(Money.of(0.85), o.taxAtPercent(10));
    }

    @Test
    void totalWithTax_returnsSubtotalPlusTax() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));

        assertEquals(Money.of(9.35), o.totalWithTax(10));
    }

}
