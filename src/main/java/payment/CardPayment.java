package payment;

import domain.Order;

public class CardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CardPayment(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()){
            throw new IllegalArgumentException("card number required");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(Order order) {
        System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card ****" + cardNumber.substring(cardNumber.length() - 4));
    }
}
