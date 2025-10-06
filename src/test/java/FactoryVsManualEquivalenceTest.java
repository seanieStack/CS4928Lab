import catalog.Priced;
import catalog.Product;
import catalog.SimpleProduct;
import common.Money;
import decorator.ExtraShot;
import decorator.OatMilk;
import decorator.SizeLarge;
import domain.LineItem;
import domain.Order;
import factory.ProductFactory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryVsManualEquivalenceTest {
    @Test
    public void factory_and_manual_build_same_drink() {
        Product viaFactory = new ProductFactory().create("ESP+SHOT+OAT+L");
        Product viaManual = new SizeLarge(new OatMilk(new ExtraShot(
                new SimpleProduct("P-ESP","Espresso", Money.of(2.50)))));

        assertEquals(viaManual.name(), viaFactory.name());
        assertEquals(((Priced) viaManual).price(), ((Priced) viaFactory).price());

        Order o1 = new Order(1);
        o1.addItem(new LineItem(viaFactory, 1));
        Order o2 = new Order(2);
        o2.addItem(new LineItem(viaManual, 1));

        assertEquals(o2.subtotal(), o1.subtotal());
        assertEquals(o2.totalWithTax(10), o1.totalWithTax(10));
    }
}
