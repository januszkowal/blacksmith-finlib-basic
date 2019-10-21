package org.blacksmith.finlib.basic;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AmountTest {
  @Test
  public void amountCreateTest() {
    Amount amount1 = Amount.of(56.789d);
    Assertions.assertEquals(BigDecimal.valueOf(56.79d),amount1.getValue());
  }
}
