package catalog;

import common.Money;

public interface Product {
    String id();
    String name();
    Money basePrice();
}
