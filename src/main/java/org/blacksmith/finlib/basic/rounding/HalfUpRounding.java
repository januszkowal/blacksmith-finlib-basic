package org.blacksmith.finlib.basic.rounding;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class HalfUpRounding implements Rounding {

  private static final int MIN_DECIMAL_PLACES = -15;
  private static final int MAX_DECIMAL_PLACES = 15;
  private static final int CACHE_SIZE = MAX_DECIMAL_PLACES - MIN_DECIMAL_PLACES + 1;
  private static final HalfUpRounding[] CACHE = new HalfUpRounding[CACHE_SIZE];

  private final int decimalPlaces;
  private final int fraction;
  private final transient BigDecimal fractionDecimal;
  private final UnaryOperator<BigDecimal> function;

  public HalfUpRounding(int decimalPlaces, int fraction) {

    if (decimalPlaces < -15 || decimalPlaces > 255) {
      throw new IllegalArgumentException("Invalid decimal places, must be from -15 to 255 inclusive");
    }
    if (fraction < 0 || fraction > 256) {
      throw new IllegalArgumentException("Invalid fraction, must be from 0 to 256 inclusive");
    }
    this.decimalPlaces = decimalPlaces;
    this.fraction = (fraction <= 1 ? 0 : fraction);
    this.fractionDecimal = (fraction <= 1 ? null : BigDecimal.valueOf(this.fraction));
    this.function = (fraction > 1) ? this::roundWithFraction : this::roundWithoutFraction;
  }

  public static HalfUpRounding ofDecimalPlaces(int decimalPlaces) {
    if (decimalPlaces >= MIN_DECIMAL_PLACES && decimalPlaces <= MAX_DECIMAL_PLACES) {
      return CACHE[decimalPlaces - MIN_DECIMAL_PLACES];
    }
    return new HalfUpRounding(decimalPlaces, 0);
  }

  public static HalfUpRounding ofFractionalDecimalPlaces(int decimalPlaces, int fraction) {
    return new HalfUpRounding(decimalPlaces, fraction);
  }

  @Override
  public BigDecimal round(BigDecimal value) {
    return function.apply(value);
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  @Override
  public int hashCode() {
    return (this.decimalPlaces << 16) + this.fraction;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    HalfUpRounding other = (HalfUpRounding) obj;
    return (decimalPlaces == other.decimalPlaces) && (fraction == other.fraction);
  }

  @Override
  public String toString() {
    return "Round to " + (fraction > 1 ? "1/" + fraction + " of " : "") + decimalPlaces + "dp";
  }

  static {
    for (int i = 0; i < CACHE_SIZE; i++) {
      CACHE[i] = new HalfUpRounding(i + MIN_DECIMAL_PLACES, 0);
    }
  }

  private BigDecimal roundWithFraction(BigDecimal value) {
    return value
        .multiply(fractionDecimal)
        .setScale(decimalPlaces, java.math.RoundingMode.HALF_UP)
        .divide(fractionDecimal);
  }

  private BigDecimal roundWithoutFraction(BigDecimal value) {
    return value.setScale(decimalPlaces, java.math.RoundingMode.HALF_UP);
  }

}
