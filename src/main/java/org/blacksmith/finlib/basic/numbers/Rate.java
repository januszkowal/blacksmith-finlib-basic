package org.blacksmith.finlib.basic.numbers;

import java.math.BigDecimal;

public class Rate extends DecimalRounded<Rate> {

  public static final int DEFAULT_PRECISION = 9;

  public static final Rate ZERO = new Rate(BigDecimal.ZERO,DEFAULT_PRECISION);
  public static final Rate ONE = new Rate(BigDecimal.ONE, DEFAULT_PRECISION);

  /**
   * Single parameter constructors constructs instance with default decimal places
   * <p>
   * All constructors - fraction lower than precision will be truncate.
   */

  public Rate(DecimalRounded<?> value) {
    super(value,value.decimalPlaces());
  }

  public Rate(DecimalRounded<?> value, int decimalPlaces) {
    super(value,decimalPlaces);
  }

  public Rate(BigDecimal value, int decimalPlaces) {
    super(value,decimalPlaces);
  }

  public Rate(BigDecimal value) {
    super(value,DEFAULT_PRECISION);
  }

  public Rate(double value, int decimalPlaces) {
    super(value,decimalPlaces);
  }

  public Rate(double value) {
    super(value,DEFAULT_PRECISION);
  }

  public Rate(long value, int decimalPlaces) {
    super(value,decimalPlaces);
  }

  public Rate(long value) {
    super(value,DEFAULT_PRECISION);
  }

  public Rate(String value, int decimalPlaces) {
    super(value,decimalPlaces);
  }

  public Rate(String value) {
    super(value,DEFAULT_PRECISION);
  }

  public static Rate of(DecimalRounded<?> value, int decimalPlaces) {
    return new Rate(value, decimalPlaces);
  }

  public static Rate of(DecimalRounded<?> value) {
    return new Rate(value);
  }

  public static Rate of(BigDecimal value, int decimalPlaces) {
    return new Rate(value, decimalPlaces);
  }

  public static Rate of(BigDecimal value) {
    return new Rate(value);
  }

  public static Rate of(String value, int decimalPlaces) {
    return new Rate(value,decimalPlaces);
  }

  public static Rate of(String value) {
    return new Rate(value);
  }

  public static Rate of(double value, int decimalPlaces) {
    return new Rate(value, decimalPlaces);
  }

  public static Rate of(double value) {
    return new Rate(value);
  }

  public static Rate of(long value, int decimalPlaces) {
    return new Rate(value, decimalPlaces);
  }

  public static Rate of(long value) {
    return new Rate(value);
  }

  @Override
  protected Rate create(BigDecimal value, int decimalPlaces) {
    return new Rate(value,decimalPlaces);
  }
}
