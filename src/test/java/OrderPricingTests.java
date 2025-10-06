import catalog.Product;
import catalog.SimpleProduct;
import common.Money;
import decorator.ExtraShot;
import decorator.SizeLarge;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderPricingTests {
    @Test
    void line_item_multiplies_decorated_unit_price() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product largeShot = new SizeLarge(new ExtraShot(base));
        LineItem li = new LineItem(largeShot, 3);
        assertEquals(Money.of(12.00), li.lineTotal());
    }

    @Test
    void order_math_with_decorated_items_and_tax() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product p1 = new ExtraShot(base);
        Product p2 = new SizeLarge(base);
        Order o = new Order(42);
        o.addItem(new LineItem(p1, 1));
        o.addItem(new LineItem(p2, 2));
        assertEquals(Money.of(9.70), o.subtotal());
        assertEquals(Money.of(0.97), o.taxAtPercent(10));
        assertEquals(Money.of(10.67), o.totalWithTax(10));
    }

    @Test
    void line_item_falls_back_to_base_price_when_not_priced() {
        Product nonPriced = new Product() {
            @Override public String id() { return "X"; }
            @Override public String name() { return "Mystery"; }
            @Override public Money basePrice() { return Money.of(5.00); }
        };
        LineItem li = new LineItem(nonPriced, 2);
        assertEquals(Money.of(10.00), li.lineTotal());
    }

    @Test
    void line_item_quantity_must_be_positive() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(base, 0));
    }

    @Test
    void decorated_products_are_priced_in_orders() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new ExtraShot(base));
        Order o = new Order(7);
        o.addItem(new LineItem(decorated, 1));
        assertEquals(Money.of(4.00), o.subtotal());
    }
}
