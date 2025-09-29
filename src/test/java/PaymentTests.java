import catalog.SimpleProduct;
import common.Money;
import domain.LineItem;
import domain.Order;
import org.junit.jupiter.api.Test;
import payment.CardPayment;
import payment.PaymentStrategy;
import payment.WalletPayment;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTests {

    private static Order makeOrder() {
        var espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        var cookie   = new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50));
        var order = new Order(1001);
        order.addItem(new LineItem(espresso, 2));
        order.addItem(new LineItem(cookie, 1));
        return order;
    }

    @Test
    void cardConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new CardPayment(null));
        assertThrows(IllegalArgumentException.class, () -> new CardPayment(""));
        assertThrows(IllegalArgumentException.class, () -> new CardPayment("   "));
    }

    @Test
    void walletConstructorValidation() {
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(null));
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(""));
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment("   "));
    }

    @Test
    void delegatesToStrategy_withSameOrderInstance() {
        var order = makeOrder();
        final Order[] received = {null};

        PaymentStrategy s = o -> received[0] = o;
        order.pay(s);

        assertSame(order, received[0], "Strategy should receive the exact same Order instance");
    }

    @Test
    void nullStrategy_throws() {
        var order = makeOrder();
        assertThrows(IllegalArgumentException.class, () -> order.pay(null));
    }

}
