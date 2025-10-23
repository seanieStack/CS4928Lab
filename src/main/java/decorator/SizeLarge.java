package decorator;

import catalog.Priced;
import catalog.Product;
import common.Money;

public final class SizeLarge extends ProductDecorator implements Priced {

    private static final Money SURCHARGE = Money.of(0.7);

    public SizeLarge(Product product) {
        super(product);
    }

    @Override
    public String name() {
        return base.name() + " (Large)";
    }

    public Money price(){
        return (base instanceof Priced p ? p.price() : basePrice()).add(SURCHARGE);
    }
}