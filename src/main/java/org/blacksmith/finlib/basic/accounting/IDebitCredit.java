package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDebitCredit<T extends IDebitCredit<T>> {
  BigDecimal getDr();

  BigDecimal getCr();

  @JsonIgnore
  default BigDecimal getSideValue(BookingSide side) {
    return side == BookingSide.D ? getDr() : getCr();
  }

  @JsonIgnore
  default BigDecimal getDrValue() {
    return getDr().subtract(getCr());
  }

  @JsonIgnore
  default BigDecimal getCrValue() {
    return getCr().subtract(getCr());
  }

  @JsonIgnore
  default BigDecimal getValue() {
    return getDrValue();
  }

  @JsonIgnore
  default BigDecimal getValue(BookingSide side) {
    return side == BookingSide.D ? getDrValue() : getCrValue();
  }

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
