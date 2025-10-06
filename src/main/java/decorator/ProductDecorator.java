package decorator;

import catalog.Product;
import common.Money;

public abstract class ProductDecorator implements Product {
    protected final Product base;

    public ProductDecorator(Product base) {
        if (base == null) throw new IllegalArgumentException("base product required");
        this.base = base;
    }

    @Override
    public String id(){
        return base.id();
    }

    @Override
    public Money basePrice(){
        return base.basePrice();
    }
}
