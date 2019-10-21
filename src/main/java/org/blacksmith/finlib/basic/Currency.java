package org.blacksmith.finlib.basic;

import org.blacksmith.commons.arg.Validate;
import com.fasterxml.jackson.annotation.JsonValue;

public class Currency {

  public static final Currency EUR = Currency.of("EUR");
  public static final Currency USD = Currency.of("USD");
  public static final Currency GBP = Currency.of("GBP");
  public static final Currency CHF = Currency.of("CHF");
  public static final Currency PLN = Currency.of("PLN");
  public static final Currency JPY = Currency.of("JPY");

  @JsonValue
  private final String isoCode;

  public Currency(final String isoCode) {
    Validate.checkStringLength(isoCode,3, "Currency iso code must have length 3");
    this.isoCode = isoCode;
  }

  public static Currency of(String isoCode) {
    return new Currency(isoCode);
  }

  public String getIsoCode() {
    return this.isoCode;
  }
}
