package org.blacksmith.finlib.basic.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import org.blacksmith.commons.arg.ArgChecker;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class DecimalRounded<T extends DecimalRounded<T>>
    implements Comparable<DecimalRounded<?>> {

  private final BigDecimal value;
  private final int decimalPlaces;

  /**
   * All constructors - fraction lower than precision will be truncated.
   */
  public DecimalRounded(DecimalRounded<?> value) {
    ArgChecker.notNull(value);
    this.decimalPlaces = value.decimalPlaces;
    this.value = value.getValue();
  }

  public DecimalRounded(DecimalRounded<?> other, int decimalPlaces) {
    ArgChecker.notNull(other);
    this.decimalPlaces = decimalPlaces;
    this.value = alignBigDecimalValue(other.value, decimalPlaces);
  }

  public DecimalRounded(BigDecimal value, int decimalPlaces) {
    ArgChecker.notNull(value);
    this.decimalPlaces = decimalPlaces;
    this.value = alignBigDecimalValue(value, decimalPlaces);
  }

  public DecimalRounded(String value, int decimalPlaces) {
    this(new BigDecimal(value), decimalPlaces);
  }

  public DecimalRounded(double value, int decimalPlaces) {
    this(BigDecimal.valueOf(value), decimalPlaces);
  }

  public DecimalRounded(long value, int decimalPlaces) {
    this(BigDecimal.valueOf(value), decimalPlaces);
  }

  public static <T extends DecimalRounded<T>> T min(T v1, T v2) {
    return v1.compareTo(v2) > 0 ? v2 : v1;
  }

  public static <T extends DecimalRounded<T>> T max(T v1, T v2) {
    return v1.compareTo(v2) > 0 ? v1 : v2;
  }

  public T add(BigDecimal augend, int decimalPlaces) {
    return create(this.value.add(augend), decimalPlaces);
  }

  public T add(BigDecimal augend) {
    return add(augend, this.decimalPlaces);
  }

  public T add(DecimalRounded<?> augend, int decimalPlaces) {
    return add(augend.value, decimalPlaces);
  }

  public T add(DecimalRounded<?> augend) {
    return add(augend.value);
  }

  public T add(double augend, int decimalPlaces) {
    return add(BigDecimal.valueOf(augend), decimalPlaces);
  }

  public T add(double augend) {
    return add(augend, decimalPlaces);
  }

  public T subtract(BigDecimal subtrahend, int decimalPlaces) {
    return create(this.value.subtract(subtrahend), decimalPlaces);
  }

  public T subtract(BigDecimal subtrahend) {
    return subtract(subtrahend, decimalPlaces);
  }

  public T subtract(DecimalRounded<?> subtrahend, int decimalPlaces) {
    return subtract(subtrahend.value, decimalPlaces);
  }

  public T subtract(DecimalRounded<?> subtrahend) {
    return subtract(subtrahend.value);
  }

  public T subtract(double subtrahend, int decimalPlaces) {
    return subtract(BigDecimal.valueOf(subtrahend), decimalPlaces);
  }

  /* multiply */

  public T subtract(double subtrahend) {
    return subtract(subtrahend, decimalPlaces);
  }

  public T multiply(BigDecimal multiplicand, int decimalPlaces) {
    return create(this.value.multiply(multiplicand), decimalPlaces);
  }

  public T multiply(BigDecimal multiplicand) {
    return multiply(multiplicand, this.decimalPlaces);
  }

  public T multiply(DecimalRounded<?> multiplicand, int decimalPlaces) {
    return multiply(multiplicand.value, decimalPlaces);
  }

  public T multiply(DecimalRounded<?> multiplicand) {
    return multiply(multiplicand.value);
  }

  public T multiply(double multiplicand, int decimalPlaces) {
    return multiply(new BigDecimal(multiplicand), decimalPlaces);
  }

  /* divide */

  public T multiply(double multiplicand) {
    return multiply(multiplicand, decimalPlaces);
  }

  public T divide(BigDecimal divisor, int decimalPlaces) {
    return create(this.value.divide(divisor, decimalPlaces + 1, RoundingMode.HALF_UP), decimalPlaces);
  }

  public T divide(BigDecimal divisor) {
    return divide(divisor, decimalPlaces);
  }

  public T divide(DecimalRounded<?> divisor, int decimalPlaces) {
    return divide(divisor.value, decimalPlaces);
  }

  public T divide(DecimalRounded<?> divisor) {
    return divide(divisor.value);
  }

  public T divide(double divisor, int decimalPlaces) {
    return divide(new BigDecimal(divisor), decimalPlaces);
  }

  public T divide(double divisor) {
    return divide(divisor, decimalPlaces);
  }

  public T inverse(int decimalPlaces) {
    return create(BigDecimal.ONE.divide(this.value, decimalPlaces+1, RoundingMode.HALF_UP), decimalPlaces);
  }

  public T inverse() {
    return inverse(this.decimalPlaces);
  }

  public T negate() {
    return create(this.value.negate(), this.decimalPlaces);
  }

  public T abs() {
    return create(this.value.abs(), this.decimalPlaces);
  }

  @JsonValue
  public BigDecimal getValue() {
    return value;
  }

  public double doubleValue() {
    return this.value.doubleValue();
  }

  public int decimalPlaces() {
    return this.decimalPlaces;
  }

  public boolean isPositiveOrZero() {
    return this.value.signum() >= 0;
  }

  public boolean isPositive() {
    return this.value.signum() > 0;
  }

  public boolean isZero() {
    return this.value.signum() == 0;
  }

  public boolean isNegative() {
    return this.value.signum() < 0;
  }

  public boolean isNegativeOrZero() {
    return value.signum() <= 0;
  }

  @Override
  public int compareTo(DecimalRounded o) {
    return this.value.compareTo(o.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !DecimalRounded.class.isAssignableFrom(o.getClass())) {
      return false;
    }
    final DecimalRounded<?> dr = (DecimalRounded<?>) o;
    return value.compareTo(dr.value) == 0;
  }

  @Override
  public String toString() {
    return this.value.toPlainString();
  }

  protected abstract T create(BigDecimal value, int decimalPlaces);

  private BigDecimal alignBigDecimalValue(BigDecimal value, int decimalPlaces) {
    return value.setScale(decimalPlaces, RoundingMode.HALF_UP);
  }
}
