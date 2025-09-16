import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LineItemTests {

    @Test
    void lineTotal_isBasePriceTimesQuantity() {
        var product = new SimpleProduct("A", "Americano", Money.of(3.00));
        var li = new LineItem(product, 3);
        assertEquals(Money.of(9.00), li.lineTotal());
    }

    @Test
    void constructor_requiresProduct() {
        assertThrows(IllegalArgumentException.class, () -> new LineItem(null, 1));
    }

    @Test
    void constructor_rejectsNonPositiveQuantity() {
        var product = new SimpleProduct("A", "Americano", Money.of(3.00));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, 0));
        assertThrows(IllegalArgumentException.class, () -> new LineItem(product, -1));
    }

    @Test
    void getters_returnValues() {
        var product = new SimpleProduct("B", "Brownie", Money.of(2.25));
        var li = new LineItem(product, 2);
        assertEquals(product, li.product());
        assertEquals(2, li.quantity());
    }

}
