package decorator;

import catalog.Priced;
import catalog.Product;
import common.Money;

public final class ExtraShot extends ProductDecorator implements Priced {

    private static final Money SURCHARGE = Money.of(0.8);

    public ExtraShot(Product product) {
        super(product);
    }

    @Override
    public String name() {
        return base.name() + " + Extra Shot";
    }

    public Money price(){
        return (base instanceof Priced p ? p.price() : basePrice()).add(SURCHARGE);
    }
}
