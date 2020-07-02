package org.blacksmith.finlib.basic;

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

  private static final Map<String,Currency> CURRENCIES = new ConcurrentHashMap<>();

  public static final Currency EUR = Currency.of("EUR");
  public static final Currency USD = Currency.of("USD");
  public static final Currency GBP = Currency.of("GBP");
  public static final Currency CHF = Currency.of("CHF");
  public static final Currency PLN = Currency.of("PLN");
  public static final Currency JPY = Currency.of("JPY");

  @JsonValue
  private final String isoCode;


  private Currency(final String isoCode) {
    ArgChecker.checkStringLength(isoCode,3, "Currency iso code must have length 3");
    this.isoCode = isoCode;
  }

  @JsonCreator
  public static Currency of(String isoCode) {
    return CURRENCIES.computeIfAbsent(isoCode,Currency::new);
  }

  public String getIsoCode() {
    return this.isoCode;
  }
  
  @Override
  public String toString() {
    return "(isoCode="+this.isoCode+")";
  }

  @Override
  public int compareTo(Currency o) {
    return this.getIsoCode().compareTo(o.getIsoCode());
  }
}
