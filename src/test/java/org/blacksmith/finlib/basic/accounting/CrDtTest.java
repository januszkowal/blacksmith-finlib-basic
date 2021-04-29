package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrDtTest {
  @Test
  public void testOfAmount1() {
    CreditDebit cd1 = CreditDebit.ofAmount(BigDecimal.ONE);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CreditDebit cd2 = CreditDebit.ofAmount(BigDecimal.ONE.negate());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getCr());
    Assertions.assertEquals(BigDecimal.ONE,cd2.getDt());
  }
  @Test
  public void testOfAmount2() {
    CreditDebit cd1 = CreditDebit.ofAmount(BigDecimal.ONE, BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CreditDebit cd2 = CreditDebit.ofAmount(BigDecimal.ONE.negate(),BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE.negate(),cd2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getDt());
  }
  @Test
  public void testOfAmountWithAllign() {
    CreditDebit cd1 = CreditDebit.ofAmountWithAlign(BigDecimal.ONE,BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CreditDebit cd2 = CreditDebit.ofAmountWithAlign(BigDecimal.ONE.negate(),BookingSide.D);
    Assertions.assertEquals(BigDecimal.ONE,cd2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getDt());
  }
}
