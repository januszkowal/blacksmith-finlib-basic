package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IDebitCredit {
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

  IDebitCredit add(IDebitCredit augend);

  IDebitCredit add(BigDecimal augendDr, BigDecimal augendCr);

  IDebitCredit subtract(IDebitCredit subtrahend);

  IDebitCredit subtract(BigDecimal subtrahendDr, BigDecimal subtrahendCr);

  IDebitCredit swap();

  IDebitCredit negate();

  IDebitCredit clone();
}
