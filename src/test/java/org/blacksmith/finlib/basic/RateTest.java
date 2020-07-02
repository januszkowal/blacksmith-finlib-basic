package org.blacksmith.finlib.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RateTest {
  @Test
  public void createRate() {
    Assertions.assertEquals(3.123456789d, Rate.of(3.123456789d).doubleValue());
    Assertions.assertEquals(3.12345678d, Rate.of(3.12345678d,8).doubleValue());
    Assertions.assertEquals(3.1234568d, Rate.of(3.12345678d,7).doubleValue());
    Assertions.assertEquals(3.123457d, Rate.of(3.12345678d,6).doubleValue());
    Assertions.assertEquals(3.12346d, Rate.of(3.12345678d,5).doubleValue());
    Assertions.assertEquals(3.1235d, Rate.of(3.12345678d,4).doubleValue());
    Assertions.assertEquals(3.123d, Rate.of(3.12345678d,3).doubleValue());
    Assertions.assertEquals(3.12d, Rate.of(3.12345678d,2).doubleValue());
  }

  @Test
  public void testMultiply() {
    Assertions.assertEquals(6.246912d, Rate.of(3.123456d).multiply(2).doubleValue());
    Assertions.assertEquals(0.312345679d, Rate.of(3.123456789d).multiply(Rate.of(0.1d)).doubleValue());
    Assertions.assertEquals(0.31234567d, Rate.of(3.1234567d,8).multiply(Rate.of(0.1d)).doubleValue());
    Assertions.assertEquals(0.3123457d, Rate.of(3.1234567d,7).multiply(Rate.of(0.1d)).doubleValue());
    Assertions.assertEquals(0.312346d, Rate.of(3.1234567d,6).multiply(Rate.of(0.1d)).doubleValue());
    Assertions.assertEquals(0.3123, Rate.of(3.1234d,4).multiply(Rate.of(0.1d)).doubleValue());
  }

}
