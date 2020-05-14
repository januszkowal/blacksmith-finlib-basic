package org.blacksmith.finlib.basic;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Value;


@Value
public class CrDtA implements CrDt {

  private final BigDecimal cr;
  private final BigDecimal dt;

  public BigDecimal getCr() {
    return this.cr;
  }

  public BigDecimal getDt() {
    return this.dt;
  }

  public static final CrDtA ZERO = CrDtA.of(BigDecimal.ZERO, BigDecimal.ZERO);


  public static CrDtA of(CrDt value) {
    return new CrDtA(value.getCr(), value.getDt());
  }

  @JsonCreator
  public static CrDtA of(@JsonProperty("cr") BigDecimal cr, @JsonProperty("dt") BigDecimal dt) {
    return new CrDtA(cr, dt);
  }

  public static CrDtA ofCR(BigDecimal cr) {
    return new CrDtA(cr, BigDecimal.ZERO);
  }

  public static CrDtA ofDT(BigDecimal dt) {
    return new CrDtA(BigDecimal.ZERO, dt);
  }

  public static CrDtA ofAmount(BigDecimal amount) {
    return amount.signum() > 0 ? new CrDtA(amount, BigDecimal.ZERO) : new CrDtA(BigDecimal.ZERO, amount.abs());
  }

  public static CrDtA ofAmount(BigDecimal amount, BookingSide side) {
    return side == BookingSide.C ? new CrDtA(amount, BigDecimal.ZERO) : new CrDtA(BigDecimal.ZERO, amount);
  }

  public static CrDtA ofAmountWithAlign(BigDecimal amount, BookingSide side) {
    return amount.signum() * side.sign() > 0 ?
        new CrDtA(amount.abs(), BigDecimal.ZERO) : new CrDtA(BigDecimal.ZERO, amount.abs());
  }

  @Override
  public CrDtA add(CrDt other) {
    return CrDtA.of(this.cr.add(other.getCr()), this.dt.add(other.getDt()));
  }

  @Override
  public CrDtA add(BigDecimal ocr, BigDecimal odt) {
    return CrDtA.of(this.cr.add(ocr), this.dt.add(odt));
  }

}
