package org.blacksmith.finlib.basic.accounting;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.blacksmith.finlib.basic.currency.Currency;
import org.junit.jupiter.api.Test;

public class CurrencyTest {
  @Test
  public void currencyCacheTest() {
    assertSame(Currency.of("EUR"), Currency.EUR, "Cached EUR");
    assertSame(Currency.of("USD"), Currency.USD, "Cached USD");
    assertSame(Currency.of("QAZ"), Currency.of("QAZ"), "Cached QAZ");
  }
}
