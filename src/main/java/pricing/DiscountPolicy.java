package pricing;

import common.Money;

public interface DiscountPolicy {
    Money discountOf(Money subtotal);
}
