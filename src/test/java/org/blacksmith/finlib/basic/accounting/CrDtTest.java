package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;
import org.blacksmith.finlib.basic.accounting.BookingSide;
import org.blacksmith.finlib.basic.accounting.CrDtA;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrDtTest {
  @Test
  public void testOfAmount1() {
    CrDtA cd1 = CrDtA.ofAmount(BigDecimal.ONE);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CrDtA cd2 = CrDtA.ofAmount(BigDecimal.ONE.negate());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getCr());
    Assertions.assertEquals(BigDecimal.ONE,cd2.getDt());
  }
  @Test
  public void testOfAmount2() {
    CrDtA cd1 = CrDtA.ofAmount(BigDecimal.ONE, BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CrDtA cd2 = CrDtA.ofAmount(BigDecimal.ONE.negate(),BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE.negate(),cd2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getDt());
  }
  @Test
  public void testOfAmountWithAllign() {
    CrDtA cd1 = CrDtA.ofAmountWithAlign(BigDecimal.ONE,BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE,cd1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd1.getDt());
    CrDtA cd2 = CrDtA.ofAmountWithAlign(BigDecimal.ONE.negate(),BookingSide.D);
    Assertions.assertEquals(BigDecimal.ONE,cd2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO,cd2.getDt());
  }
}
