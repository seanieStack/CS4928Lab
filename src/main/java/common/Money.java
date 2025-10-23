package common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;

    public static Money of(double value) {
        if (value < 0) throw new IllegalArgumentException("value cannot be negative");
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money of(BigDecimal value) {
        if (value == null) throw new IllegalArgumentException("value required");
        if (value.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("value cannot be negative");
        return new Money(value);
    }

    public static Money zero() {
        return of(0);
    }

    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal asBigDecimal() {
        return amount;
    }

    public Money add(Money other) {
        if (other == null) throw new IllegalArgumentException("other required");

        return new Money(amount.add(other.amount));
    }

    public Money multiply(int qty) {
        if (qty < 0) throw new IllegalArgumentException("qty must be positive");
        return new Money(amount.multiply(BigDecimal.valueOf(qty)));
    }

    public Money percent(int percent) {
        if (percent < 0) throw new IllegalArgumentException("percent must be positive");
        BigDecimal rate = BigDecimal.valueOf(percent).movePointLeft(2);
        return new Money(this.amount.multiply(rate));
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }

}
