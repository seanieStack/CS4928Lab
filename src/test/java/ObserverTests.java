import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;
import payment.CashPayment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObserverTests {

    @Test
    void observers_notified_on_item_add() {
        var p = new SimpleProduct("A", "A", Money.of(2));
        var o = new Order(1);
        o.addItem(new LineItem(p, 1));
        List<String> events = new ArrayList<>();
        o.register((order, evt) -> events.add(evt));
        o.addItem(new LineItem(p, 1));
        assertTrue(events.contains("itemAdded"));
    }

    @Test
    void singleObserverGets_itemAdded() {
        var product = new SimpleProduct("A", "Espresso", Money.of(2.50));
        var order = new Order(1);

        order.addItem(new LineItem(product, 1));

        List<String> events = new ArrayList<>();
        order.register((o, evt) -> events.add(evt));

        order.addItem(new LineItem(product, 1));

        assertTrue(events.contains("itemAdded"));
    }

    @Test
    void twoObserversGet_ready() {
        var order = new Order(2);

        List<String> eventsA = new ArrayList<>();
        List<String> eventsB = new ArrayList<>();

        order.register((o, evt) -> eventsA.add(evt));
        order.register((o, evt) -> eventsB.add(evt));

        order.markReady();

        assertTrue(eventsA.contains("ready"));
        assertTrue(eventsB.contains("ready"));
    }

    @Test
    void observerSees_paid_afterPayment() {
        var product = new SimpleProduct("B", "Americano", Money.of(3.00));
        var order   = new Order(3);
        order.addItem(new LineItem(product, 1));

        List<String> events = new ArrayList<>();
        order.register((o, evt) -> events.add(evt));

        order.pay(new CashPayment());

        assertTrue(events.contains("paid"));
    }
}
