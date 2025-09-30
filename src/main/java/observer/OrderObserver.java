package observer;

import domain.Order;

public interface OrderObserver {
    void updated(Order order, String eventType);
}
