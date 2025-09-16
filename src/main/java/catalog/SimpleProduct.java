package catalog;

import common.Money;

public final class SimpleProduct implements Product {
    private final String id;
    private final String name;
    private final Money basePrice;

    public SimpleProduct(String id, String name, Money basePrice) {
        if(basePrice == null) throw new IllegalArgumentException("basePrice required");
        if(basePrice.compareTo(Money.zero()) < 0) throw new IllegalArgumentException("basePrice needs to be greater than zero");

        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String id() { return id; }

    @Override
    public String name() { return name; }

    @Override
    public Money basePrice() { return basePrice; }
}
