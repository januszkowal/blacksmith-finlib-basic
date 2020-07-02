package org.blacksmith.finlib.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

public class AmountTest {
  @Test
  public void amountCreateTest() {
    Amount amount1 = Amount.of(56.789d);
    assertEquals(BigDecimal.valueOf(56.79d),amount1.getValue());
  }

  @Test
  public void amountDivideTest() {
    Amount amount1 = Amount.of(10.00d);
    assertEquals(Amount.of(3.33d),amount1.divide(3.0d));
    Amount amount2 = Amount.of(20.00d);
    assertEquals(Amount.of(6.67d),amount2.divide(3.0d));
  }

  @Test
  public void amountMultiplyTest() {
    Amount amount1 = Amount.of(10.00d);
    assertEquals(Amount.of(3.33d),amount1.multiply(1/3.0d));
  }

  @Test
  void equalTest() {
    assertEquals(Amount.of(2.15d),Rate.of(2.15d));
  }

  @Test
  public void xamountTest() {
    Rate b = null;
    System.out.println("#####");
    Rate a = new Rate(2.12345d,4);
    System.out.println("#####:" + a);
    b = a.multiply(0.1d);
    System.out.println("#####:b1:" + b);
    b = a.multiply(0.1d,3);
    System.out.println("#####:b2:" + b);
    System.out.println("#####:b2:" + BigDecimal.valueOf(232.3456).setScale(3, RoundingMode.HALF_UP).precision());
  }
}
