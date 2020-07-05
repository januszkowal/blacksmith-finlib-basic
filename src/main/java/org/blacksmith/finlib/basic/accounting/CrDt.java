package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;

public interface CrDt {
  BigDecimal getCr();
  BigDecimal getDt();

  /* returns a new instance*/
  CrDt add(CrDt other);
  CrDt add(BigDecimal ocr, BigDecimal odt);
}
