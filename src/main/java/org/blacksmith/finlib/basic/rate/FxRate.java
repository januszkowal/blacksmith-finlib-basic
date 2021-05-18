package org.blacksmith.finlib.basic.rate;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;
import org.blacksmith.finlib.basic.currency.Currency;
import org.blacksmith.finlib.basic.numbers.Rate;

import lombok.Getter;

@Getter
public class FxRate implements Comparable<FxRate> {
  private final Currency ccy1;
  private final Currency ccy2;
  private final Rate rate;

  public FxRate(Currency ccy1, Currency ccy2, Rate rate) {
    ArgChecker.notNull(ccy1, "Currency 1 must be not null");
    ArgChecker.notNull(ccy2, "Currency 2 must be not null");
    ArgChecker.notNull(rate, "Rate must be not null");
    this.ccy1 = ccy1;
    this.ccy2 = ccy2;
    this.rate = rate;
  }

  public static FxRate of(Currency ccy1, Currency ccy2, Rate rate) {
    return new FxRate(ccy1, ccy2, rate);
  }

  public static FxRate of(String ccy1, String ccy2, BigDecimal rate) {
    return new FxRate(Currency.of(ccy1), Currency.of(ccy2), Rate.of(rate));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final FxRate fxRate = (FxRate) o;
    return Objects.equals(ccy1, fxRate.ccy1) &&
        Objects.equals(ccy2, fxRate.ccy2) &&
        Objects.equals(rate, fxRate.rate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ccy1, ccy2, rate);
  }

  @Override
  public String toString() {
    return "FxRate{" +
        "ccy1=" + ccy1 +
        ", ccy2=" + ccy2 +
        ", rate=" + rate +
        '}';
  }

  private static final Comparator<FxRate> FX_RATE_COMPARATOR =
      Comparator.comparing(FxRate::getCcy1)
          .thenComparing(FxRate::getCcy2)
          .thenComparing(FxRate::getRate);

  @Override
  public int compareTo(FxRate o) {
    return FX_RATE_COMPARATOR.compare(this, o);
  }
}
