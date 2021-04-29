package org.blacksmith.finlib.basic.numbers;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class AmountTest {
  @Test
  public void test1() {
    Amount amount = new Amount(BigDecimal.TEN);
  }
}
