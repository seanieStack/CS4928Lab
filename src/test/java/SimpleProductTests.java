import catalog.Product;
import catalog.SimpleProduct;
import common.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleProductTests {

    @Test
    void constructor_setsFields() {
        Product p = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        assertEquals("P-ESP", p.id());
        assertEquals("Espresso", p.name());
        assertEquals(Money.of(2.50), p.basePrice());
    }

    @Test
    void constructor_requiresBasePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleProduct("X", "X", null));
    }

    @Test
    void constructor_rejectsNegativeBasePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new SimpleProduct("X", "X", Money.of(-1.00)));
    }

}
