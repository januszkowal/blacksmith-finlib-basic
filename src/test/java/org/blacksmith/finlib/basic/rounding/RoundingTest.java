package org.blacksmith.finlib.basic.rounding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.UnaryOperator;
import org.blacksmith.commons.string.Pair;
import org.junit.jupiter.api.Test;

public class RoundingTest {

  //@Test
  public void roundingUp() {
    Rounding rounding4 = RoundingFactory.of(RoundingMode.UP, 1, 4);
    assertEquals(123.35,rounding4.round(123.36d));
    Rounding rounding81 = RoundingFactory.of(RoundingMode.UP, -3, 8);
    assertEquals(5125.0,rounding81.round(5123.00));
    assertEquals(5125.0,rounding81.round(5124.00));
    assertEquals(5125.0,rounding81.round(5125.00));
    assertEquals(5125.0,rounding81.round(5126.00));
    Rounding rounding82 = RoundingFactory.of(RoundingMode.UP, 3, 8);
    assertEquals(5123.0,rounding82.round(5123.00));
    assertEquals(5123.1,rounding82.round(5123.15));
    assertEquals(5123.2,rounding82.round(5123.20));
    assertEquals(5123.3,rounding82.round(5123.30));
  }

  @Test
  void roundUpWithoutFraction() {
    testList(List.of(
        Pair.of(249.999d,250.0d),
        Pair.of(250.000d,250.0d),
        Pair.of(256.025d,256.0d)),
        (v)->RoundingFactory.of(RoundingMode.UP, 0, 0).round(v),"UP 0 places 0 fraction");
    testList(List.of(
        Pair.of(249.999d,250.0d),
        Pair.of(250.000d,250.0d),
        Pair.of(256.025d,260.0d)),
        (v)->RoundingFactory.of(RoundingMode.UP, -1, 0).round(v),"UP -1 places 0 fraction");
    testList(List.of(
        Pair.of(249.999d,200.0d),
        Pair.of(250.000d,300.0d),
        Pair.of(256.025d,300.0d)),
        (v)->RoundingFactory.of(RoundingMode.UP, -2, 0).round(v),"UP -2 places 0 fraction");
  }

  @Test
  void roundUpWithFraction() {
    testList(List.of(
        Pair.of(1.999d,2.00d),
        Pair.of(1.900d,2.00d),
        Pair.of(1.800d,1.75d),
        Pair.of(1.700d,1.75d),
        Pair.of(1.650d,1.75d),
        Pair.of(1.600d,1.50d),
        Pair.of(1.500d,1.50d)),
        (v)->RoundingFactory.of(RoundingMode.UP, 0, 4).round(v),"UP 0 places 4 fraction");
  }

  private void testList(List<Pair<Double,Double>> testValues, UnaryOperator<Double> rnd, String description) {
    testValues.forEach(pv->testRnd(pv.getRight(),pv.getLeft(),rnd,pv.getLeft() + " after rounding with " + description));
  }

  private void testRnd(double expected, double value, UnaryOperator<Double> rnd, String description) {
    assertEquals(expected,rnd.apply(value),description);
  }
}
