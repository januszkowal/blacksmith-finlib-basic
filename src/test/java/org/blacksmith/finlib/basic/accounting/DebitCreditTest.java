package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCreditTest {
  @Test
  public void testOfAmount1() {
    DebitCredit a1 = DebitCredit.ofValue(BigDecimal.ONE);
    Assertions.assertEquals(BigDecimal.ONE, a1.getDr());
    Assertions.assertEquals(BigDecimal.ZERO, a1.getCr());
    DebitCredit a2 = DebitCredit.ofValue(BigDecimal.ONE.negate());
    Assertions.assertEquals(BigDecimal.ONE, a2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO, a2.getDr());
  }

  @Test
  public void testOfAmount2() {
    DebitCredit a1 = DebitCredit.ofValue(BigDecimal.ONE, BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE, a1.getCr());
    Assertions.assertEquals(BigDecimal.ZERO, a1.getDr());
    DebitCredit a2 = DebitCredit.ofValue(BigDecimal.ONE.negate(), BookingSide.C);
    Assertions.assertEquals(BigDecimal.ONE.negate(), a2.getCr());
    Assertions.assertEquals(BigDecimal.ZERO, a2.getDr());
  }

  @Test
  public void testOfAmountWithAllign() {
    DebitCredit cd1 = DebitCredit.ofValueWithAlign(BigDecimal.ONE, BookingSide.C);
    Assertions.assertEquals(BigDecimal.ZERO, cd1.getCr());
    Assertions.assertEquals(BigDecimal.ONE, cd1.getDr());
    DebitCredit a2 = DebitCredit.ofValueWithAlign(BigDecimal.ONE.negate(), BookingSide.D);
    Assertions.assertEquals(BigDecimal.ZERO, a2.getCr());
    Assertions.assertEquals(BigDecimal.ONE, a2.getDr());
  }

  @Test
  public void addTest() {
    IDebitCredit a1 = DebitCredit.of(8.004d, 4.00001d);
    IDebitCredit a2 = DebitCredit.of(-3.1d, 7.15d);
    var result = a1.add(a2);
    assertThat(result).describedAs("Debit").extracting(IDebitCredit::getDr)
        .isEqualTo(BigDecimal.valueOf(4.904d));
    assertThat(result).describedAs("Credit").extracting(IDebitCredit::getCr)
        .isEqualTo(BigDecimal.valueOf(11.15001d));
  }

}
