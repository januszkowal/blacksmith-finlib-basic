package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDebitCredit<T extends IDebitCredit<T>> {
  BigDecimal getDr();
  BigDecimal getCr();

  @JsonIgnore
  default boolean isZero() {
    return getDr().equals(BigDecimal.ZERO) && getCr().equals(BigDecimal.ZERO);
  }

  T add(IDebitCredit augend);
  T add(BigDecimal augendDr, BigDecimal augendCr);

  T subtract(IDebitCredit subtrahend);
  T subtract(BigDecimal subtrahendDr, BigDecimal subtrahendCr);

  T swap();
  T negate();

  T clone();
}
