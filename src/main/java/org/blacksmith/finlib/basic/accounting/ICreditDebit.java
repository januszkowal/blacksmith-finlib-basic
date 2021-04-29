package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

public interface ICreditDebit <T extends ICreditDebit<T>> {
  BigDecimal getCr();
  BigDecimal getDt();

  default boolean isZero() {
    return getCr().equals(BigDecimal.ZERO) && getDt().equals(BigDecimal.ZERO);
  }

  T add(ICreditDebit other);
  T add(BigDecimal ocr, BigDecimal odt);

  T subtract(ICreditDebit other);
  T subtract(BigDecimal ocr, BigDecimal odt);

  T swap();
  T negate();

  T clone();
}
