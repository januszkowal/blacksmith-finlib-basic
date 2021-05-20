package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;
import org.blacksmith.finlib.basic.currency.Currency;
import org.blacksmith.finlib.basic.numbers.Amount;

import lombok.Getter;

@Getter
public class CurrencyAmount implements Comparable<CurrencyAmount> {

  private static final Comparator<CurrencyAmount> CURRENCY_AMOUNT_COMPARATOR =
      Comparator.comparing(CurrencyAmount::getCurrency)
          .thenComparing(CurrencyAmount::getAmount);
  private final Amount amount;
  private final Currency currency;

  public CurrencyAmount(Amount amount, Currency currency) {
    ArgChecker.notNull(amount, "Amount must be not null");
    ArgChecker.notNull(currency, "Currency must be not null");
    this.amount = amount;
    this.currency = currency;
  }

  public static CurrencyAmount of(Amount amount, Currency currency) {
    ArgChecker.notNull(amount, "Amount must be not null");
    ArgChecker.notNull(currency, "Currency must be not null");
    return new CurrencyAmount(amount, currency);
  }

  public static CurrencyAmount of(BigDecimal amount, Currency currency) {
    ArgChecker.notNull(amount, "Amount must be not null");
    ArgChecker.notNull(currency, "Currency must be not null");
    return new CurrencyAmount(Amount.of(amount), currency);
  }

  public static CurrencyAmount of(double amount, Currency currency) {
    ArgChecker.notNull(currency, "Currency must be not null");
    return new CurrencyAmount(Amount.of(amount), currency);
  }

  public static CurrencyAmount of(long amount, Currency currency) {
    ArgChecker.notNull(currency, "Currency must be not null");
    return new CurrencyAmount(Amount.of(amount), currency);
  }

  public CurrencyAmount add(BigDecimal augend) {
    ArgChecker.notNull(augend, "Augend amount must be not null");
    return new CurrencyAmount(this.amount.add(augend), this.currency);
  }

  public CurrencyAmount add(Amount augend) {
    ArgChecker.notNull(augend, "Augend amount must be not null");
    return add(augend.getValue());
  }

  public CurrencyAmount add(CurrencyAmount augend) {
    ArgChecker.notNull(augend, "Augend must be not null");
    ArgChecker.isTrue(this.currency.equals(augend.currency), () -> "Unable to subtract amounts in different currencies");
    return add(augend.amount.getValue());
  }

  public CurrencyAmount subtract(BigDecimal subtrahend) {
    ArgChecker.notNull(subtrahend, "Subtrahend amount must be not null");
    return new CurrencyAmount(this.amount.subtract(subtrahend), this.currency);
  }

  public CurrencyAmount subtract(Amount subtrahend) {
    ArgChecker.notNull(subtrahend, "Subtrahend amount must be not null");
    return subtract(subtrahend.getValue());
  }

  public CurrencyAmount multiply(BigDecimal multiplicand) {
    ArgChecker.notNull(multiplicand, "Multiplicand amount must be not null");
    return new CurrencyAmount(this.amount.multiply(multiplicand), this.currency);
  }

  public CurrencyAmount multiply(double multiplicand) {
    return multiply(BigDecimal.valueOf(multiplicand));
  }

  public CurrencyAmount divide(BigDecimal divisor) {
    ArgChecker.isTrue(divisor.compareTo(BigDecimal.ZERO) != 0, "multiplicand amount must be not zero");
    return new CurrencyAmount(this.amount.divide(divisor), this.currency);
  }

  public CurrencyAmount divide(double divisor) {
    ArgChecker.isTrue(divisor == 0.0d, "multiplicand amount must be not zero");
    return divide(BigDecimal.valueOf(divisor));
  }

  public CurrencyAmount negate() {
    return new CurrencyAmount(this.amount.negate(), this.currency);
  }

  public CurrencyAmount abs() {
    return new CurrencyAmount(this.amount.abs(), this.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, amount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final CurrencyAmount ca = (CurrencyAmount) o;
    return Objects.equals(currency, ca.currency) &&
        Objects.equals(amount, ca.amount);
  }

  @Override
  public int compareTo(CurrencyAmount o) {
    return CURRENCY_AMOUNT_COMPARATOR.compare(this, o);
  }
}
