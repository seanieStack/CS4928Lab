package domain;

import common.Money;
import observer.OrderObserver;
import observer.OrderPublisher;
import payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;

public final class Order implements OrderPublisher {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();

    public Order(long id) {
        this.id = id;
    }

    public void addItem(LineItem li) {
        if (li.quantity() <= 0) throw new IllegalArgumentException();
        items.add(li);
        notifyObservers("itemAdded");
    }

    public Money subtotal() {
        return items.stream()
                .map(LineItem::lineTotal)
                .reduce(Money.zero(), Money::add);
    }

    public Money taxAtPercent(int percent) {
        return subtotal().percent(percent);
    }

    public Money totalWithTax(int percent) {
        return subtotal().add(taxAtPercent(percent));
    }

    public long getId() { return id; }
    public List<LineItem> getItems() { return items; }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("strategy required");
        }
        strategy.pay(this);
        notifyObservers("paid");
    }

    public void markReady(){
        notifyObservers("ready");
    }

    private final List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void register(OrderObserver o) {
        if(o == null) throw new IllegalArgumentException("ObserverRequired");

        if(!observers.contains(o)) observers.add(o);
    }

    @Override
    public void unregister(OrderObserver o) {
        if(o == null) throw new IllegalArgumentException("ObserverRequired");

        observers.remove(o);
    }

    private void notifyObservers(String eventType) {
        for (OrderObserver x : observers){
            x.updated(this, eventType);
        }
    }

    @Override
    public void notifyObservers(Order order, String eventType) {
        for (OrderObserver x : observers){
            x.updated(this, eventType);
        }
    }
}
