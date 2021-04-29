package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

public interface ICreditDebit {//<T extends ICreditDebit<T>> {
  BigDecimal getCr();
  BigDecimal getDt();

  default boolean isZero() {
    return getCr().equals(BigDecimal.ZERO) && getDt().equals(BigDecimal.ZERO);
  }

  ICreditDebit add(ICreditDebit other);
  ICreditDebit add(BigDecimal ocr, BigDecimal odt);

  ICreditDebit subtract(ICreditDebit other);
  ICreditDebit subtract(BigDecimal ocr, BigDecimal odt);

  ICreditDebit swap();
  ICreditDebit negate();

  ICreditDebit clone();
}
