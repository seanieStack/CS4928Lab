package pricing;

import common.Money;

public interface TaxPolicy {
    Money taxOn(Money amount);
}
