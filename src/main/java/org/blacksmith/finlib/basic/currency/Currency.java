package org.blacksmith.finlib.basic.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.EqualsAndHashCode;

import org.blacksmith.commons.arg.ArgChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode
public class Currency implements Comparable<Currency> {

  private static final Logger LOGGER = LoggerFactory.getLogger(Currency.class);

  private static final Map<String, Currency> CURRENCIES = new ConcurrentHashMap<>();

  public static final Currency EUR = Currency.of("EUR");
  public static final Currency USD = Currency.of("USD");
  public static final Currency GBP = Currency.of("GBP");
  public static final Currency CHF = Currency.of("CHF");
  public static final Currency PLN = Currency.of("PLN");
  public static final Currency JPY = Currency.of("JPY");

  @JsonValue
  private final String currencyCode;

  private Currency(final String currencyCode) {
    ArgChecker.checkStringLength(currencyCode, 3, "Currency iso code must have length 3");
    this.currencyCode = currencyCode;
  }

  @JsonCreator
  public static Currency of(String currencyCode) {
    return CURRENCIES.computeIfAbsent(currencyCode, Currency::new);
  }

  public String getCurrencyCode() {
    return this.currencyCode;
  }

  @Override
  public String toString() {
    return "(isoCode=" + this.currencyCode + ")";
  }

  @Override
  public int compareTo(Currency o) {
    return this.currencyCode.compareTo(o.currencyCode);
  }
}
