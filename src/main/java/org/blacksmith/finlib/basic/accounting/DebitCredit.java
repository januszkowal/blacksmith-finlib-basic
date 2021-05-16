package org.blacksmith.finlib.basic.accounting;

import java.math.BigDecimal;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "dr", "cr" })
public class DebitCredit implements IDebitCredit<DebitCredit> {

  private final BigDecimal dr;
  private final BigDecimal cr;

  public DebitCredit(BigDecimal dr, BigDecimal cr) {
    ArgChecker.notNull(dr, "Debit must be not null");
    ArgChecker.notNull(cr, "Credit must be not null");
    this.dr = dr;
    this.cr = cr;
  }

  public DebitCredit(IDebitCredit value) {
    ArgChecker.notNull(value, "Credit/Debit must be not null");
    ArgChecker.notNull(value.getDr(), "Debit must be not null");
    ArgChecker.notNull(value.getCr(), "Credit must be not null");
    this.dr = value.getDr();
    this.cr = value.getCr();
  }

  @Override
  public BigDecimal getDr() {
    return this.dr;
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
  public static DebitCredit of(@JsonProperty("dr") BigDecimal dr, @JsonProperty("cr") BigDecimal cr) {
    return new DebitCredit(dr, cr);
  }

  public static DebitCredit of(double dr, double cr) {
    return new DebitCredit(BigDecimal.valueOf(dr), BigDecimal.valueOf(cr));
  }

  public static DebitCredit of(long dr, long cr) {
    return new DebitCredit(BigDecimal.valueOf(dr), BigDecimal.valueOf(cr));
  }

  public static DebitCredit ofNullable(BigDecimal dr, BigDecimal cr) {
    return new DebitCredit(dr == null ? BigDecimal.ZERO : dr, cr == null ? BigDecimal.ZERO : cr);
  }

  public static DebitCredit ofdr(BigDecimal dr) {
    return new DebitCredit(dr, BigDecimal.ZERO);
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
    return DebitCredit.of(this.dr.add(augend.getDr()), this.cr.add(augend.getCr()));
  }

  @Override
  public DebitCredit add(BigDecimal augenddr, BigDecimal augendCr) {
    return DebitCredit.of(this.dr.add(augenddr), this.cr.add(augendCr));
  }

  @Override
  public DebitCredit subtract(IDebitCredit subtrahend) {
    return new DebitCredit(this.dr.subtract(subtrahend.getDr()), this.cr.subtract(subtrahend.getCr()));
  }

  @Override
  public DebitCredit subtract(BigDecimal subtrahenddr, BigDecimal subtrahendCr) {
    return new DebitCredit(this.dr.subtract(subtrahenddr), this.cr.subtract(subtrahendCr));
  }

  @Override
  public DebitCredit swap() {
    return new DebitCredit(this.cr, this.dr);
  }

  @Override
  public DebitCredit negate() {
    return new DebitCredit(this.dr.negate(), this.cr.negate());
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
    return Objects.equals(dr, that.dr) && Objects.equals(cr, that.cr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dr, cr);
  }

  @Override
  public String toString() {
    return "CreditDebit{dr=" + dr + ", cr=" + cr + '}';
  }
}
