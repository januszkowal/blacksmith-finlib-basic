package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDebitCredit<T extends IDebitCredit<T>> {
  BigDecimal getDt();
  BigDecimal getCr();

  @JsonIgnore
  default boolean isZero() {
    return getDt().equals(BigDecimal.ZERO) && getCr().equals(BigDecimal.ZERO);
  }

  T add(IDebitCredit augend);
  T add(BigDecimal augendDt, BigDecimal augendCr);

  T subtract(IDebitCredit subtrahend);
  T subtract(BigDecimal subtrahendDt, BigDecimal subtrahendCr);

  T swap();
  T negate();

  T clone();
}
