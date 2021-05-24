package org.blacksmith.finlib.basic.currency;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.blacksmith.commons.arg.ArgChecker;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class CurrencyPair {

  static final Pattern REGEX_PATTERN = Pattern.compile("([A-Z0-9]{3})/([A-Z0-9]{3})");

  private final Currency base;
  private final Currency counter;

  public CurrencyPair(Currency base, Currency counter) {
    ArgChecker.notNull(base, "base");
    ArgChecker.notNull(counter, "counter");
    this.base = base;
    this.counter = counter;
  }

  public static CurrencyPair of(String base, String counter) {
    return new CurrencyPair(Currency.of(base), Currency.of(counter));
  }

  public static CurrencyPair of(String pair) {
    ArgChecker.notNull(pair, "pairStr");
    Matcher matcher = REGEX_PATTERN.matcher(pair.toUpperCase(Locale.ENGLISH));
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid currency pair: " + pair);
    }
    Currency base = Currency.of(matcher.group(1));
    Currency counter = Currency.of(matcher.group(2));
    return new CurrencyPair(base, counter);
  }
}
