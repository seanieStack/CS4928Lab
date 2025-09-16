import catalog.Catalog;
import catalog.InMemoryCatalog;
import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Week2IntegrationTest {
    @Test
    void integration_order_values_match_demo() {
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));
        catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50)));

        Order order = new Order(1001);
        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        order.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));

        int taxPct = 10;

        assertEquals(2, order.getItems().size(), "Items count");
        assertEquals(Money.of(8.50), order.subtotal(), "Subtotal");
        assertEquals(Money.of(0.85), order.taxAtPercent(taxPct), "Tax 10%");
        assertEquals(Money.of(9.35), order.totalWithTax(taxPct), "Total with tax");

        assertTrue(order.getId() > 0, "Order id should be positive");
    }
}
