package org.blacksmith.finlib.basic.numbers;

import java.io.Serializable;
import java.math.BigDecimal;

public class Amount extends DecimalRounded<Amount> implements Serializable {

  public static final int DEFAULT_PRECISION = 2;
  public static final Amount ZERO = new Amount(BigDecimal.ZERO, DEFAULT_PRECISION);
  public static final Amount ONE = new Amount(BigDecimal.ONE, DEFAULT_PRECISION);
  public static final Amount TEN = new Amount(BigDecimal.TEN, DEFAULT_PRECISION);
  public static final Amount HUNDRED = new Amount(100, DEFAULT_PRECISION);
  private static final long serialVersionUID = 1L;

  public Amount(DecimalRounded<?> value) {
    super(value, value.decimalPlaces());
  }

  public Amount(DecimalRounded<?> value, int decimalPlaces) {
    super(value, decimalPlaces);
  }

  public Amount(BigDecimal value, int decimalPlaces) {
    super(value, decimalPlaces);
  }

  public Amount(BigDecimal value) {
    super(value, DEFAULT_PRECISION);
  }

  public Amount(long value, int decimalPlaces) {
    super(value, decimalPlaces);
  }

  public Amount(long value) {
    super(value, DEFAULT_PRECISION);
  }

  public Amount(double value, int decimalPlaces) {
    super(value, decimalPlaces);
  }

  public Amount(double value) {
    super(value, DEFAULT_PRECISION);
  }

  public Amount(String value, int decimalPlaces) {
    super(value, decimalPlaces);
  }

  public Amount(String value) {
    super(value, DEFAULT_PRECISION);
  }

  public static Amount of(DecimalRounded<?> value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }

  public static Amount of(DecimalRounded<?> value) {
    return new Amount(value);
  }

  public static Amount of(BigDecimal value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }

  public static Amount of(BigDecimal value) {
    return new Amount(value, DEFAULT_PRECISION);
  }

  public static Amount of(String value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }

  public static Amount of(String value) {
    return new Amount(value);
  }

  public static Amount of(double value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }

  public static Amount of(double value) {
    return new Amount(value);
  }

  public static Amount of(long value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }

  public static Amount of(long value) {
    return new Amount(value);
  }

  @Override
  protected Amount create(BigDecimal value, int decimalPlaces) {
    return new Amount(value, decimalPlaces);
  }
}
