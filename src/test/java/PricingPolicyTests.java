import common.Money;
import pricing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PricingPolicyTests {
    @Test void loyalty_discount_5_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.39), d.discountOf(Money.of(7.80)));
    }

    @Test void no_discount_returns_zero() {
        DiscountPolicy d = new NoDiscount();
        assertEquals(Money.of(0.00), d.discountOf(Money.of(10.00)));
    }

    @Test void fixed_coupon_discount() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(1.00));
        assertEquals(Money.of(1.00), d.discountOf(Money.of(5.00)));
    }

    @Test void fixed_coupon_capped_at_subtotal() {
        DiscountPolicy d = new FixedCouponDiscount(Money.of(10.00));
        assertEquals(Money.of(5.00), d.discountOf(Money.of(5.00)));
    }

    @Test void fixed_rate_tax_10_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(0.74), t.taxOn(Money.of(7.41)));
    }

    @Test void fixed_rate_tax_5_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(5);
        assertEquals(Money.of(0.50), t.taxOn(Money.of(10.00)));
    }

    @Test void pricing_pipeline() {
        var pricing = new PricingService(new LoyaltyPercentDiscount(5), new FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(7.80));
        assertEquals(Money.of(0.39), pr.discount());
        assertEquals(Money.of(7.41), Money.of(pr.subtotal().asBigDecimal().subtract(pr.discount().asBigDecimal())));
        assertEquals(Money.of(0.74), pr.tax());
        assertEquals(Money.of(8.15), pr.total());
    }

    @Test void pricing_with_no_discount() {
        var pricing = new PricingService(new NoDiscount(), new FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(10.00));
        assertEquals(Money.of(0.00), pr.discount());
        assertEquals(Money.of(1.00), pr.tax());
        assertEquals(Money.of(11.00), pr.total());
    }

    @Test void pricing_with_fixed_coupon() {
        var pricing = new PricingService(new FixedCouponDiscount(Money.of(1.00)), new FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(3.30));
        assertEquals(Money.of(1.00), pr.discount());
        assertEquals(Money.of(0.23), pr.tax());
        assertEquals(Money.of(2.53), pr.total());
    }
}
