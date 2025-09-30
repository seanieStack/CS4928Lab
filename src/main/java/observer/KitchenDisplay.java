package observer;

import domain.Order;

public class KitchenDisplay implements OrderObserver{
    @Override
    public void updated(Order order, String eventType) {
        switch (eventType) {
            case "itemAdded" ->
                System.out.println("[Kitchen] Order #" + order.getId() + ": item added");
            case "paid" ->
                System.out.println("[Kitchen] Order #" + order.getId() + ": payment received");
        }
    }
}
