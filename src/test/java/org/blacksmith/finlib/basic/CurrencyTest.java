package org.blacksmith.finlib.basic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CurrencyTest {
  @Test
  public void currencyCacheTest() {
    assertTrue(Currency.of("EUR") == Currency.EUR, "Cached EUR");
    assertTrue(Currency.of("USD") == Currency.USD, "Cached USD");
    assertTrue(Currency.of("QAZ") == Currency.of("QAZ"), "Cached QAZ");
  }
}
