package observer;

import domain.Order;

public class DeliveryDesk implements OrderObserver{
    @Override
    public void updated(Order order, String eventType) {
        switch (eventType){
            case "ready" ->
                System.out.println("[Delivery] Order #" + order.getId() + " is ready for delivery");
        }
    }
}
