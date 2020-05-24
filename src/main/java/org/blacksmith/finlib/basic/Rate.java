package org.blacksmith.finlib.basic;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import org.blacksmith.commons.arg.Validate;

public class Rate {

  private static final int MAX_SCALE = 9;
  public static final Rate ZERO = new Rate(BigDecimal.ZERO);
  private final BigDecimal value;

  public Rate(Rate value) {
    Validate.notNull(value);
    this.value = value.getValue();
  }

  public Rate(BigDecimal value) {
    Validate.notNull(value);
    this.value = alignBigDecimalValue(value);
  }

  /**
   * Constructs an instance based on text representation. Format with dot will be accepted, for example 1.25
   * <p>
   * Fraction lower than precision will be truncate.
   */
  public Rate(String value) {
    this(new BigDecimal(value));
  }

  /**
   * Constructs and instance based on Double instance.
   * <p>
   * Fraction lower than precision will be truncate.
   */
  public Rate(double value) {
    this(BigDecimal.valueOf(value));
  }

  public Rate(long value) {
    this(BigDecimal.valueOf(value));
  }

  public static Rate of(Rate value) {
    return new Rate(value);
  }

  public static Rate of(BigDecimal value) {
    return new Rate(value);
  }

  public static Rate of(String value) {
    return new Rate(value);
  }

  public static Rate of(double value) {
    return new Rate(value);
  }

  public static Rate of(long value) {
    return new Rate(value);
  }

  private BigDecimal alignBigDecimalValue(BigDecimal value) {
    return value.setScale(MAX_SCALE, RoundingMode.HALF_UP);
  }

  @JsonValue
  public BigDecimal getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.value.toPlainString();
  }

  public double doubleValue() {return this.value.doubleValue();}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Rate rate = (Rate) o;
    return Objects.equals(value, rate.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
