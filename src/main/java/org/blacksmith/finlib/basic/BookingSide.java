package org.blacksmith.finlib.basic;

public enum BookingSide {
  C("C",1),
  D("D",-1);
  private final String value;
  private final int sign;
  BookingSide(String value, int sign)
  {
    this.value = value;
    this.sign = sign;
  }
  public static BookingSide of(String value)
  {
    return BookingSide.valueOf(value);
  }
  public String value()
  {
    return this.value;
  }
  public BookingSide negate()
  {
    return (this==BookingSide.C) ? BookingSide.D : BookingSide.C;
  }
  public int sign()
  {
    return this.sign;
  }
}
