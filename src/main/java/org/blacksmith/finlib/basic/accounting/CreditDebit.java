package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditDebit implements ICreditDebit<CreditDebit> {

  private final BigDecimal cr;
  private final BigDecimal dt;

  public CreditDebit(BigDecimal cr, BigDecimal dt) {
    ArgChecker.notNull(cr, "Credit must be not null");
    ArgChecker.notNull(dt, "Debit must be not null");
    this.cr = cr;
    this.dt = dt;
  }

  public CreditDebit(ICreditDebit crdt) {
    ArgChecker.notNull(crdt, "Credit/Debit must be not null");
    ArgChecker.notNull(crdt.getCr(), "Credit must be not null");
    ArgChecker.notNull(crdt.getDt(), "Debit must be not null");
    this.cr = crdt.getCr();
    this.dt = crdt.getDt();
  }

  @Override
  public BigDecimal getCr() {
    return this.cr;
  }

  @Override
  public BigDecimal getDt() {
    return this.dt;
  }

  public static final CreditDebit ZERO = CreditDebit.of(BigDecimal.ZERO, BigDecimal.ZERO);

  public static CreditDebit of(ICreditDebit value) {
    return new CreditDebit(value);
  }

  @JsonCreator
  public static CreditDebit of(@JsonProperty("cr") BigDecimal cr, @JsonProperty("dt") BigDecimal dt) {
    return new CreditDebit(cr, dt);
  }

  public static CreditDebit of(double cr, double dt) {
    return new CreditDebit(BigDecimal.valueOf(cr), BigDecimal.valueOf(dt));
  }

  public static CreditDebit ofNullable(BigDecimal cr, BigDecimal dt) {
    return new CreditDebit(cr == null ? BigDecimal.ZERO : cr, dt == null ? BigDecimal.ZERO : dt);
  }

  public static CreditDebit ofCr(BigDecimal cr) {
    return new CreditDebit(cr, BigDecimal.ZERO);
  }

  public static CreditDebit ofDt(BigDecimal dt) {
    return new CreditDebit(BigDecimal.ZERO, dt);
  }

  public static CreditDebit ofAmount(BigDecimal amount) {
    return amount.signum() > 0 ? new CreditDebit(amount, BigDecimal.ZERO) : new CreditDebit(BigDecimal.ZERO, amount.abs());
  }

  public static CreditDebit ofAmount(BigDecimal amount, BookingSide side) {
    return side == BookingSide.C ? new CreditDebit(amount, BigDecimal.ZERO) : new CreditDebit(BigDecimal.ZERO, amount);
  }

  public static CreditDebit ofAmountWithAlign(BigDecimal amount, BookingSide side) {
    return amount.signum() * side.sign() > 0 ?
        new CreditDebit(amount.abs(), BigDecimal.ZERO) : new CreditDebit(BigDecimal.ZERO, amount.abs());
  }

  @Override
  public CreditDebit add(ICreditDebit other) {
    return CreditDebit.of(this.cr.add(other.getCr()), this.dt.add(other.getDt()));
  }

  @Override
  public CreditDebit add(BigDecimal ocr, BigDecimal odt) {
    return CreditDebit.of(this.cr.add(ocr), this.dt.add(odt));
  }

  @Override
  public CreditDebit subtract(ICreditDebit other) {
    return new CreditDebit(this.cr.subtract(other.getCr()), this.dt.subtract(other.getDt()));
  }

  @Override
  public CreditDebit subtract(BigDecimal ocr, BigDecimal odt) {
    return new CreditDebit(this.cr.subtract(ocr), this.dt.subtract(odt));
  }

  @Override
  public CreditDebit swap() {
    return new CreditDebit(this.dt, this.cr);
  }

  @Override
  public CreditDebit negate() {
    return new CreditDebit(this.cr.negate(), this.dt.negate());
  }

  @Override
  public CreditDebit clone() {
    return new CreditDebit(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CreditDebit that = (CreditDebit) o;
    return Objects.equals(cr, that.cr) && Objects.equals(dt, that.dt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cr, dt);
  }

  @Override
  public String toString() {
    return "CreditDebit{cr=" + cr + ", dt=" + dt + '}';
  }
}
