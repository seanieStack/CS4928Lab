package decorator;

import catalog.Priced;
import catalog.Product;
import common.Money;

public final class OatMilk extends ProductDecorator implements Priced {

    private static final Money SURCHARGE = Money.of(0.5);

    public OatMilk(Product product) {
        super(product);
    }

    @Override
    public String name() {
        return base.name() + " + Oat Milk";
    }

    public Money price(){
        return (base instanceof Priced p ? p.price() : basePrice()).add(SURCHARGE);
    }
}