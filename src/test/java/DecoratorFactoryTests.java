import catalog.Priced;
import catalog.Product;
import catalog.SimpleProduct;
import common.Money;
import decorator.ExtraShot;
import decorator.OatMilk;
import decorator.SizeLarge;
import decorator.Syrup;
import domain.LineItem;
import domain.Order;
import factory.ProductFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecoratorFactoryTests {
    @Test
    void decorator_single_addon() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);
        assertEquals("Espresso + Extra Shot", withShot.name());
        assertEquals(Money.of(3.30), ((Priced) withShot).price());
    }

    @Test
    void decorator_stacks() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));
        assertEquals("Espresso + Extra Shot + Oat Milk + Size Large", decorated.name());
        assertEquals(Money.of(2.50 + 0.80 + 0.50 + 0.70), ((Priced) decorated).price());
    }

    @Test
    void factory_parses_recipe() {
        ProductFactory f = new ProductFactory();
        Product p = f.create("ESP+SHOT+OAT");
        assertTrue(p.name().contains("Espresso") && p.name().contains("Oat Milk"));
    }

    @Test
    void order_uses_decorated_price_simple() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product withShot = new ExtraShot(espresso);
        Order o = new Order(1);
        o.addItem(new LineItem(withShot, 2));
        assertEquals(Money.of(6.60), o.subtotal());
    }

    @Test
    void decoration_order_independence_for_price() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));

        Product a = new SizeLarge(new OatMilk(new ExtraShot(base)));
        Product b = new OatMilk(new SizeLarge(new ExtraShot(base)));

        Money pa = ((Priced) a).price();
        Money pb = ((Priced) b).price();

        assertEquals(Money.of(2.50 + 0.80 + 0.50 + 0.70), pa);
        assertEquals(pa, pb);
        assertNotEquals(a.name(), b.name());
    }

    @Test
    void simple_product_is_priced() {
        Product latte = new SimpleProduct("P-LAT", "Latte", Money.of(3.20));
        assertInstanceOf(Priced.class, latte);
        assertEquals(Money.of(3.20), ((Priced) latte).price());
    }

    @Test
    void each_decorator_name_and_surcharge() {
        Product base = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));

        Product shot = new ExtraShot(base);
        assertEquals("Espresso + Extra Shot", shot.name());
        assertEquals(Money.of(3.30), ((Priced) shot).price());

        Product oat = new OatMilk(base);
        assertEquals("Espresso + Oat Milk", oat.name());
        assertEquals(Money.of(3.00), ((Priced) oat).price());

        Product syp = new Syrup(base);
        assertEquals("Espresso + Syrup", syp.name());
        assertEquals(Money.of(2.90), ((Priced) syp).price());

        Product large = new SizeLarge(base);
        assertEquals("Espresso + Size Large", large.name());
        assertEquals(Money.of(3.20), ((Priced) large).price());
    }

    @Test
    void factory_handles_whitespace_and_case() {
        ProductFactory f = new ProductFactory();
        Product a = f.create("  esp + shot + oat  ");
        Product b = f.create("ESP+SHOT+OAT");
        assertEquals(((Priced) b).price(), ((Priced) a).price());
        assertEquals(b.name(), a.name());
    }

    @Test
    void factory_throws_on_unknown_token() {
        ProductFactory f = new ProductFactory();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> f.create("ESP+UNICORN"));
        assertTrue(ex.getMessage().toUpperCase().contains("UNKNOWN"));
    }
}
