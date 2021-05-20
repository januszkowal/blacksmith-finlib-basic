package org.blacksmith.finlib.basic.rounding;

import java.math.BigDecimal;

public class HalfDownRounding implements Rounding {

  private static final int MIN_DECIMAL_PLACES = -15;
  private static final int MAX_DECIMAL_PLACES = 15;
  private static final int CACHE_SIZE = MAX_DECIMAL_PLACES - MIN_DECIMAL_PLACES + 1; //15
  private static final HalfDownRounding[] CACHE = new HalfDownRounding[CACHE_SIZE];

  private final int decimalPlaces;

  public HalfDownRounding(int decimalPlaces) {
    if (decimalPlaces < -15 || decimalPlaces > 255) {
      throw new IllegalArgumentException("Invalid decimal places, must be from -15 to 255 inclusive");
    }
    this.decimalPlaces = decimalPlaces;
  }

  public static HalfDownRounding ofDecimalPlaces(int decimalPlaces) {
    if (decimalPlaces >= MIN_DECIMAL_PLACES && decimalPlaces <= MAX_DECIMAL_PLACES) {
      return CACHE[decimalPlaces - MIN_DECIMAL_PLACES];
    }
    return new HalfDownRounding(decimalPlaces);
  }

  @Override
  public BigDecimal round(BigDecimal value) {
    return value.setScale(decimalPlaces, java.math.RoundingMode.DOWN);
  }

  public int getDecimalPlaces() {
    return decimalPlaces;
  }

  @Override
  public int hashCode() {
    return (this.decimalPlaces << 16);
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
    HalfDownRounding other = (HalfDownRounding) obj;
    return decimalPlaces == other.decimalPlaces;
  }

  @Override
  public String toString() {
    return "Truncate to " + decimalPlaces + "dp";
  }

  static {
    for (int i = 0; i < CACHE_SIZE; i++) {
      CACHE[i] = new HalfDownRounding(i + MIN_DECIMAL_PLACES);
    }
  }
}
