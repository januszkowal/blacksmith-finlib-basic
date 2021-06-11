package org.blacksmith.finlib.basic.datetime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeUnitTest {

  @Test
  void ofSymbol() {
    assertEquals(TimeUnit.DAY, TimeUnit.ofSymbol("D"));
    assertEquals(TimeUnit.WEEK, TimeUnit.ofSymbol("W"));
    assertEquals(TimeUnit.MONTH, TimeUnit.ofSymbol("M"));
    assertEquals(TimeUnit.QUARTER, TimeUnit.ofSymbol("Q"));
    assertEquals(TimeUnit.HALF_YEAR, TimeUnit.ofSymbol("H"));
    assertEquals(TimeUnit.YEAR, TimeUnit.ofSymbol("Y"));
//    assertNull(TimeUnit.ofSymbol("X"));
  }

  @Test
  void symbol() {
    assertEquals("D", TimeUnit.DAY.symbol());
    assertEquals("W", TimeUnit.WEEK.symbol());
    assertEquals("M", TimeUnit.MONTH.symbol());
    assertEquals("Q", TimeUnit.QUARTER.symbol());
    assertEquals("H", TimeUnit.HALF_YEAR.symbol());
    assertEquals("Y", TimeUnit.YEAR.symbol());
  }

  @Test
  void addTo() {
    LocalDate date = LocalDate.parse("2020-06-23");
    assertEquals(date.plusDays(3), TimeUnit.DAY.addTo(date, 3));
    assertEquals(date.plusDays(14), TimeUnit.WEEK.addTo(date, 2));
    assertEquals(date.plusMonths(3), TimeUnit.QUARTER.addTo(date, 1));
    assertEquals(date.plusMonths(6), TimeUnit.QUARTER.addTo(date, 2));
    assertEquals(date.plusMonths(12), TimeUnit.QUARTER.addTo(date, 4));
    assertEquals(date.plusMonths(6), TimeUnit.HALF_YEAR.addTo(date, 1));
    assertEquals(date.plusMonths(12), TimeUnit.HALF_YEAR.addTo(date, 2));
    assertEquals(date.plusYears(1), TimeUnit.YEAR.addTo(date, 1));
    assertEquals(date.plusYears(2), TimeUnit.YEAR.addTo(date, 2));
    assertEquals(date.plusDays(-3), TimeUnit.DAY.addTo(date, -3));
    assertEquals(date.plusDays(-14), TimeUnit.WEEK.addTo(date, -2));
    assertEquals(date.plusMonths(-3), TimeUnit.QUARTER.addTo(date, -1));
    assertEquals(date.plusMonths(-6), TimeUnit.QUARTER.addTo(date, -2));
    assertEquals(date.plusMonths(-12), TimeUnit.QUARTER.addTo(date, -4));
    assertEquals(date.plusMonths(-6), TimeUnit.HALF_YEAR.addTo(date, -1));
    assertEquals(date.plusMonths(-12), TimeUnit.HALF_YEAR.addTo(date, -2));
    assertEquals(date.plusYears(-1), TimeUnit.YEAR.addTo(date, -1));
    assertEquals(date.plusYears(-2), TimeUnit.YEAR.addTo(date, -2));
  }

  @Test
  void minusFrom() {
    LocalDate date = LocalDate.parse("2020-06-23");
    assertEquals(date.minusDays(3), TimeUnit.DAY.subtractFrom(date, 3));
    assertEquals(date.minusDays(14), TimeUnit.WEEK.subtractFrom(date, 2));
    assertEquals(date.minusMonths(3), TimeUnit.QUARTER.subtractFrom(date, 1));
    assertEquals(date.minusMonths(6), TimeUnit.QUARTER.subtractFrom(date, 2));
    assertEquals(date.minusMonths(12), TimeUnit.QUARTER.subtractFrom(date, 4));
    assertEquals(date.minusMonths(6), TimeUnit.HALF_YEAR.subtractFrom(date, 1));
    assertEquals(date.minusMonths(12), TimeUnit.HALF_YEAR.subtractFrom(date, 2));
    assertEquals(date.minusYears(1), TimeUnit.YEAR.subtractFrom(date, 1));
    assertEquals(date.minusYears(2), TimeUnit.YEAR.subtractFrom(date, 2));
    assertEquals(date.minusDays(-3), TimeUnit.DAY.subtractFrom(date, -3));
    assertEquals(date.minusDays(-14), TimeUnit.WEEK.subtractFrom(date, -2));
    assertEquals(date.minusMonths(-3), TimeUnit.QUARTER.subtractFrom(date, -1));
    assertEquals(date.minusMonths(-6), TimeUnit.QUARTER.subtractFrom(date, -2));
    assertEquals(date.minusMonths(-12), TimeUnit.QUARTER.subtractFrom(date, -4));
    assertEquals(date.minusMonths(-6), TimeUnit.HALF_YEAR.subtractFrom(date, -1));
    assertEquals(date.minusMonths(-12), TimeUnit.HALF_YEAR.subtractFrom(date, -2));
    assertEquals(date.minusYears(-1), TimeUnit.YEAR.subtractFrom(date, -1));
    assertEquals(date.minusYears(-2), TimeUnit.YEAR.subtractFrom(date, -2));
  }

  @Test
  void unitName() {
    assertEquals("Day", TimeUnit.DAY.unitName());
  }

  @Test
  void chronoUnit() {
    assertEquals(ChronoUnit.DAYS, TimeUnit.DAY.chronoUnit());
    assertEquals(1, TimeUnit.DAY.chronoUnitCount());

    assertEquals(ChronoUnit.WEEKS, TimeUnit.WEEK.chronoUnit());
    assertEquals(1, TimeUnit.WEEK.chronoUnitCount());

    assertEquals(ChronoUnit.MONTHS, TimeUnit.MONTH.chronoUnit());
    assertEquals(1, TimeUnit.MONTH.chronoUnitCount());

    assertEquals(ChronoUnit.MONTHS, TimeUnit.QUARTER.chronoUnit());
    assertEquals(3, TimeUnit.QUARTER.chronoUnitCount());

    assertEquals(ChronoUnit.MONTHS, TimeUnit.HALF_YEAR.chronoUnit());
    assertEquals(6, TimeUnit.HALF_YEAR.chronoUnitCount());

    assertEquals(ChronoUnit.YEARS, TimeUnit.YEAR.chronoUnit());
    assertEquals(1, TimeUnit.YEAR.chronoUnitCount());
  }

  @Test
  void operationsTest() {
    LocalDate date = LocalDate.parse("2020-01-10");
    assertEquals(date.plusDays(1), TimeUnit.DAY.addTo(date));
    assertEquals(date.plusDays(5), TimeUnit.DAY.addTo(date, 5));
    assertEquals(date.plusDays(-5), TimeUnit.DAY.addTo(date, -5));
    assertEquals(date.minusDays(1), TimeUnit.DAY.subtractFrom(date));
    assertEquals(date.minusDays(5), TimeUnit.DAY.subtractFrom(date, 5));
    assertEquals(date.minusDays(-5), TimeUnit.DAY.subtractFrom(date, -5));

    assertEquals(date.plusWeeks(1), TimeUnit.WEEK.addTo(date));
    assertEquals(date.plusWeeks(2), TimeUnit.WEEK.addTo(date, 2));
    assertEquals(date.plusWeeks(-2), TimeUnit.WEEK.addTo(date, -2));
    assertEquals(date.minusWeeks(1), TimeUnit.WEEK.subtractFrom(date));
    assertEquals(date.minusWeeks(2), TimeUnit.WEEK.subtractFrom(date, 2));
    assertEquals(date.minusWeeks(-2), TimeUnit.WEEK.subtractFrom(date, -2));

    assertEquals(date.plusMonths(1), TimeUnit.MONTH.addTo(date));
    assertEquals(date.plusMonths(2), TimeUnit.MONTH.addTo(date, 2));
    assertEquals(date.plusMonths(-2), TimeUnit.MONTH.addTo(date, -2));
    assertEquals(date.minusMonths(1), TimeUnit.MONTH.subtractFrom(date));
    assertEquals(date.minusMonths(2), TimeUnit.MONTH.subtractFrom(date, 2));
    assertEquals(date.minusMonths(-2), TimeUnit.MONTH.subtractFrom(date, -2));

    assertEquals(date.plusMonths(3), TimeUnit.QUARTER.addTo(date));
    assertEquals(date.plusMonths(6), TimeUnit.QUARTER.addTo(date, 2));
    assertEquals(date.plusMonths(-6), TimeUnit.QUARTER.addTo(date, -2));
    assertEquals(date.minusMonths(3), TimeUnit.QUARTER.subtractFrom(date));
    assertEquals(date.minusMonths(6), TimeUnit.QUARTER.subtractFrom(date, 2));
    assertEquals(date.minusMonths(-6), TimeUnit.QUARTER.subtractFrom(date, -2));

    assertEquals(date.plusMonths(6), TimeUnit.HALF_YEAR.addTo(date));
    assertEquals(date.plusMonths(18), TimeUnit.HALF_YEAR.addTo(date, 3));
    assertEquals(date.plusMonths(-18), TimeUnit.HALF_YEAR.addTo(date, -3));
    assertEquals(date.minusMonths(6), TimeUnit.HALF_YEAR.subtractFrom(date));
    assertEquals(date.minusMonths(18), TimeUnit.HALF_YEAR.subtractFrom(date, 3));
    assertEquals(date.minusMonths(-18), TimeUnit.HALF_YEAR.subtractFrom(date, -3));

    assertEquals(date.plusYears(1), TimeUnit.YEAR.addTo(date));
    assertEquals(date.plusYears(3), TimeUnit.YEAR.addTo(date, 3));
    assertEquals(date.plusYears(-3), TimeUnit.YEAR.addTo(date, -3));
    assertEquals(date.minusYears(1), TimeUnit.YEAR.subtractFrom(date));
    assertEquals(date.minusYears(3), TimeUnit.YEAR.subtractFrom(date, 3));
    assertEquals(date.minusYears(-3), TimeUnit.YEAR.subtractFrom(date, -3));
  }
}
