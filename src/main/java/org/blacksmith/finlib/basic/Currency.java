package org.blacksmith.finlib.basic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.blacksmith.commons.arg.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Currency {

  private static final Logger LOGGER = LoggerFactory.getLogger(Currency.class);

  public static final Map<String,Currency> CURRENCIES = new ConcurrentHashMap<>();

  public static final Currency EUR = Currency.of("EUR");
  public static final Currency USD = Currency.of("USD");
  public static final Currency GBP = Currency.of("GBP");
  public static final Currency CHF = Currency.of("CHF");
  public static final Currency PLN = Currency.of("PLN");
  public static final Currency JPY = Currency.of("JPY");

  @JsonValue
  private final String isoCode;


  private Currency(final String isoCode) {
    Validate.checkStringLength(isoCode,3, "Currency iso code must have length 3");
    this.isoCode = isoCode;
  }

  @JsonCreator
  public static Currency of(String isoCode) {
    return CURRENCIES.computeIfAbsent(isoCode,Currency::new);
  }

  public String getIsoCode() {
    return this.isoCode;
  }
}
