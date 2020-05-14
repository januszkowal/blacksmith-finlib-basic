package org.blacksmith.finlib.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrencyTest {
  @Test
  public void currencyCacheTest() {
    assertEquals(true,Currency.of("EUR")==Currency.EUR,"Cached EUR");
    assertEquals(true,Currency.of("USD")==Currency.USD,"Cached USD");
    assertEquals(true,Currency.of("QAZ")==Currency.of("QAZ"),"Cached QAZ");
  }
}
