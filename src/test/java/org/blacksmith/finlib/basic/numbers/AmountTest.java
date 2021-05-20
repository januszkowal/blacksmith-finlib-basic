package org.blacksmith.finlib.basic.numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.blacksmith.finlib.basic.numbers.Amount;
import org.blacksmith.finlib.basic.numbers.Rate;
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
    assertEquals(Amount.of(2.15d), Rate.of(2.15d));
  }

  @Test
  public void xamountTest() {
    conversions(0.01d, 2);
    conversions(0.000001d, 7);
  }

  private void conversions(double d, int scale) {
    System.out.println(BigDecimal.valueOf(d));
    System.out.println(BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_UP));
    System.out.println(BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_UP));
    System.out.println(new BigDecimal(d));
    System.out.println(new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP));
  }
}
