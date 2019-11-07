package org.blacksmith.finlib.basic;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public class FxRate {
  private final Currency ccy1;
  private final Currency ccy2;
  private final Rate rate;

  public FxRate(Currency ccy1, Currency ccy2, Rate rate) {
    Validate.notNull(ccy1,"Currency 1 must be not null");
    Validate.notNull(ccy2,"Currency 2 must be not null");
    Validate.notNull(rate,"Rate must be not null");
    this.ccy1 = ccy1;
    this.ccy2 = ccy2;
    this.rate = rate;
  }

  public static  FxRate of (Currency ccy1, Currency ccy2, Rate rate) {
    return new FxRate(ccy1,ccy2,rate);
  }

  public static  FxRate of (String ccy1, String ccy2, BigDecimal rate) {
    return new FxRate(Currency.of(ccy1),Currency.of(ccy2),Rate.of(rate));
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

}
