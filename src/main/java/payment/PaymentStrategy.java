package payment;

import domain.Order;

public interface PaymentStrategy {
    void pay(Order order);
}
