package payment;

import domain.Order;

public class CashPayment implements PaymentStrategy{
    @Override
    public void pay(Order order) {
        System.out.println("[Cash] Customer Paid " + order.totalWithTax(10) + " EUR");
    }
}
