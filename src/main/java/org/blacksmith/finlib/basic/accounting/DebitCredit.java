package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "dt", "cr" })
public class DebitCredit implements IDebitCredit<DebitCredit> {

  private final BigDecimal dt;
  private final BigDecimal cr;

  public DebitCredit(BigDecimal dt, BigDecimal cr) {
    ArgChecker.notNull(dt, "Debit must be not null");
    ArgChecker.notNull(cr, "Credit must be not null");
    this.dt = dt;
    this.cr = cr;
  }

  public DebitCredit(IDebitCredit value) {
    ArgChecker.notNull(value, "Credit/Debit must be not null");
    ArgChecker.notNull(value.getDt(), "Debit must be not null");
    ArgChecker.notNull(value.getCr(), "Credit must be not null");
    this.dt = value.getDt();
    this.cr = value.getCr();
  }

  @Override
  public BigDecimal getDt() {
    return this.dt;
  }

  @Override
  public BigDecimal getCr() {
    return this.cr;
  }

  public static final DebitCredit ZERO = DebitCredit.of(BigDecimal.ZERO, BigDecimal.ZERO);

  public static DebitCredit of(IDebitCredit value) {
    return new DebitCredit(value);
  }

  @JsonCreator
  public static DebitCredit of(@JsonProperty("dt") BigDecimal dt, @JsonProperty("cr") BigDecimal cr) {
    return new DebitCredit(dt, cr);
  }

  public static DebitCredit of(double dt, double cr) {
    return new DebitCredit(BigDecimal.valueOf(dt), BigDecimal.valueOf(cr));
  }

  public static DebitCredit of(long dt, long cr) {
    return new DebitCredit(BigDecimal.valueOf(dt), BigDecimal.valueOf(cr));
  }

  public static DebitCredit ofNullable(BigDecimal dt, BigDecimal cr) {
    return new DebitCredit(dt == null ? BigDecimal.ZERO : dt, cr == null ? BigDecimal.ZERO : cr);
  }

  public static DebitCredit ofDt(BigDecimal dt) {
    return new DebitCredit(dt, BigDecimal.ZERO);
  }

  public static DebitCredit ofCr(BigDecimal cr) {
    return new DebitCredit(BigDecimal.ZERO, cr);
  }

  public static DebitCredit ofAmount(BigDecimal amount) {
    return amount.signum() > 0 ? new DebitCredit(amount, BigDecimal.ZERO) : new DebitCredit(BigDecimal.ZERO, amount.abs());
  }

  public static DebitCredit ofAmount(BigDecimal amount, BookingSide side) {
    return side == BookingSide.D ? new DebitCredit(amount, BigDecimal.ZERO) : new DebitCredit(BigDecimal.ZERO, amount);
  }

  public static DebitCredit ofAmountWithAlign(BigDecimal amount, BookingSide side) {
    return amount.signum() * side.sign() > 0 ?
        new DebitCredit(amount.abs(), BigDecimal.ZERO) : new DebitCredit(BigDecimal.ZERO, amount.abs());
  }

  @Override
  public DebitCredit add(IDebitCredit augend) {
    return DebitCredit.of(this.dt.add(augend.getDt()), this.cr.add(augend.getCr()));
  }

  @Override
  public DebitCredit add(BigDecimal augendDt, BigDecimal augendCr) {
    return DebitCredit.of(this.dt.add(augendDt), this.cr.add(augendCr));
  }

  @Override
  public DebitCredit subtract(IDebitCredit subtrahend) {
    return new DebitCredit(this.dt.subtract(subtrahend.getDt()), this.cr.subtract(subtrahend.getCr()));
  }

  @Override
  public DebitCredit subtract(BigDecimal subtrahendDt, BigDecimal subtrahendCr) {
    return new DebitCredit(this.dt.subtract(subtrahendDt), this.cr.subtract(subtrahendCr));
  }

  @Override
  public DebitCredit swap() {
    return new DebitCredit(this.cr, this.dt);
  }

  @Override
  public DebitCredit negate() {
    return new DebitCredit(this.dt.negate(), this.cr.negate());
  }

  @Override
  public DebitCredit clone() {
    return new DebitCredit(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    DebitCredit that = (DebitCredit) o;
    return Objects.equals(dt, that.dt) && Objects.equals(cr, that.cr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dt, cr);
  }

  @Override
  public String toString() {
    return "CreditDebit{dt=" + dt + ", cr=" + cr + '}';
  }
}
