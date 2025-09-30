package observer;

import domain.Order;

public class CustomerNotifier implements OrderObserver{

    @Override
    public void updated(Order order, String eventType) {
        System.out.println("[Customer] Dear customer, your Order #" + order.getId() + " has been updated: " + eventType + ".");
    }
}
