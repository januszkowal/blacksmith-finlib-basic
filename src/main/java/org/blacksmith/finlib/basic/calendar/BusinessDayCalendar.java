package org.blacksmith.finlib.basic.calendar;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.blacksmith.commons.arg.ArgChecker;
import org.blacksmith.commons.datetime.DateRange;
import org.blacksmith.commons.datetime.DateUtils;

public interface BusinessDayCalendar {
  /**
   * Checks if the specified date is a holiday.
   * <p>
   * This is the opposite of {@link #isBusinessDay(LocalDate)}.
   *
   * @param date the date to check
   * @return true if the specified date is a holiday
   * @throws IllegalArgumentException if the date is outside the supported range
   */
  boolean isHoliday(LocalDate date);

  /**
   * Checks if the specified date is a business day.
   * <p>
   * This is the opposite of {@link #isHoliday(LocalDate)}.
   *
   * @param date the date to check
   * @return true if the specified date is a business day
   * @throws IllegalArgumentException if the date is outside the supported range
   */
  default boolean isBusinessDay(LocalDate date) {
    ArgChecker.notNull(date);
    return !isHoliday(date);
  }

  /**
   * Shifts the date by the specified number of business days.
   * <p>
   * If the amount is zero, the input date is returned.
   * If the amount is positive, later business days are chosen.
   * If the amount is negative, earlier business days are chosen.
   *
   * @param date   the date to adjust
   * @param amount the number of business days to adjust by
   * @return the shifted date
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate shift(LocalDate date, int amount) {
    ArgChecker.notNull(date);
    LocalDate adjusted = date;
    if (amount > 0) {
      for (int i = 0; i < amount; i++) {
        adjusted = next(adjusted);
      }
    } else if (amount < 0) {
      for (int i = 0; i > amount; i--) {
        adjusted = previous(adjusted);
      }
    }
    return adjusted;
  }

  /**
   * Finds the next business day, always returning a later date.
   * <p>
   * Given a date, this method returns the next business day.
   *
   * @param date  the date to adjust
   * @return the first business day after the input date
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate next(LocalDate date) {
    ArgChecker.notNull(date);
    LocalDate adjusted = date.plusDays(1);
    return isHoliday(adjusted) ? next(adjusted) : adjusted;
  }

  /**
   * Finds next n-th business day,  always returning a later date.
   *
   * @param date   the date
   * @param amount the number of business days (n-th)
   * @return Next n-th business day
   */
  default LocalDate next(LocalDate date, int amount) {
    ArgChecker.notNull(date);
    LocalDate adjusted = date;
    for (int i = 0; i < amount; i++) {
      adjusted = next(adjusted);
    }
    return adjusted;
  }

  /**
   * Finds the next business day, returning the input date if it is a business day.
   * <p>
   * Given a date, this method returns a business day.
   * If the input date is a business day, it is returned.
   * Otherwise, the next business day is returned.
   *
   * @param date  the date to adjust
   * @return the input date if it is a business day, or the next business day
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate nextOrSame(LocalDate date) {
    ArgChecker.notNull(date);
    return isHoliday(date) ? next(date) : date;
  }

  /**
   * Finds the previous business day, always returning an earlier date.
   * <p>
   * Given a date, this method returns the previous business day.
   *
   * @param date  the date to adjust
   * @return the first business day before the input date
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate previous(LocalDate date) {
    ArgChecker.notNull(date);
    LocalDate adjusted = date.minusDays(1);
    return isHoliday(adjusted) ? previous(adjusted) : adjusted;
  }

  /**
   * Finds the n-th previous business day, always returning an earlier date.
   *
   * @param date   the date
   * @param amount they number o business days (n-th)
   * @return Prior n-th business day
   */
  default LocalDate previous(LocalDate date, int amount) {
    ArgChecker.notNull(date);
    LocalDate adjusted = date;
    for (int i = 0; i < amount; i++) {
      adjusted = previous(adjusted);
    }
    return adjusted;
  }

  /**
   * Finds the previous business day, returning the input date if it is a business day.
   * <p>
   * Given a date, this method returns a business day.
   * If the input date is a business day, it is returned.
   * Otherwise, the previous business day is returned.
   *
   * @param date  the date to adjust
   * @return the input date if it is a business day, or the previous business day
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate previousOrSame(LocalDate date) {
    ArgChecker.notNull(date);
    return isHoliday(date) ? previous(date) : date;
  }

  /**
   * Finds the next business day within the month, returning the input date if it is a business day,
   * or the last business day of the month if the next business day is in a different month.
   * <p>
   * Given a date, this method returns a business day.
   * If the input date is a business day, it is returned.
   * If the next business day is within the same month, it is returned.
   * Otherwise, the last business day of the month is returned.
   * <p>
   * Note that the result of this method may be earlier than the input date.
   * <p>
   *
   * @param date  the date to adjust
   * @return the input date if it is a business day, the next business day if within the same month
   *   or the last business day of the month
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate nextSameOrLastInMonth(LocalDate date) {
    ArgChecker.notNull(date);
    LocalDate adjusted = nextOrSame(date);
    return (adjusted.getMonthValue() != date.getMonthValue() ? previous(adjusted) : adjusted);
  }

  /**
   * Finds the previous business day within the month, returning the input date if it is a business day,
   * or the first business day of the next month if the previous business day is in a different month.
   * <p>
   * Given a date, this method returns a business day.
   * If the input date is a business day, it is returned.
   * If the next business day is within the same month, it is returned.
   * Otherwise, the last business day of the month is returned.
   * <p>
   * Note that the result of this method may be earlier than the input date.
   * <p>
   *
   * @param date  the date to adjust
   * @return the input date if it is a business day, the next business day if within the same month
   *   or the last business day of the month
   * @throws IllegalArgumentException if the calculation is outside the supported range
   */
  default LocalDate previousSameOrLastInMonth(LocalDate date) {
    ArgChecker.notNull(date);
    LocalDate adjusted = previousOrSame(date);
    return (adjusted.getMonthValue() != date.getMonthValue() ? next(date) : adjusted);
  }

  /**
   * Checks if the specified date is the last business day of the month.
   * <p>
   * This returns true if the date specified is the last valid business day of the month.
   *
   * @param date  the date to check
   * @return true if the specified date is the last business day of the month
   * @throws IllegalArgumentException if the date is outside the supported range
   */
  default boolean isLastBusinessDayOfMonth(LocalDate date) {
    ArgChecker.notNull(date);
    return isBusinessDay(date) && next(date).getMonthValue() != date.getMonthValue();
  }

  /**
   * Calculates the last business day of the month.
   * <p>
   * Given a date, this method returns the date of the last business day of the month.
   *
   * @param date  the date to check
   * @return true if the specified date is the last business day of the month
   * @throws IllegalArgumentException if the date is outside the supported range
   */
  default LocalDate lastBusinessDayOfMonth(LocalDate date) {
    ArgChecker.notNull(date);
    return previousOrSame(date.withDayOfMonth(date.lengthOfMonth()));
  }

  /**
   * Gets the stream of business days within range
   * <p>
   * If the dates are equal, an empty stream is returned.
   * If the end is before the start, an exception is thrown.
   *
   * @param range the date range
   * @return the stream of business days
   * @throws IllegalArgumentException if either date is outside the supported range
   */
  default Stream<LocalDate> businessDays(DateRange range) {
    ArgChecker.inOrderOrEqual(range.getLowerInclusive(), range.getUpperInclusive(), "Start date must be later or equal than end date");
    return DateUtils.streamOfDates(range)
        .filter(this::isBusinessDay);
  }

  /**
   * Calculates the number of business days within range.
   * <p>
   *
   * @param range the date range
   * @return the stream of business days
   * @throws IllegalArgumentException if either date is outside the supported range
   */
  default int businessDaysCount(DateRange range) {
    ArgChecker.notNull(range);
    return Math.toIntExact(businessDays(range).count());
  }

  /**
   * Gets the stream of holidays within range.
   * <p>
   * If the dates are equal, an empty stream is returned.
   * If the end is before the start, an exception is thrown.
   *
   * @param range  the date range
   * @return the stream of holidays
   * @throws IllegalArgumentException if either date is outside the supported range
   */
  default Stream<LocalDate> holidays(DateRange range) {
    ArgChecker.inOrderOrEqual(range.getLowerInclusive(), range.getUpperInclusive(), "Start date must be later or equal than end date");
    return DateUtils.streamOfDates(range)
        .filter(this::isHoliday);
  }
  /**
   * Calculates the number of holidays within range.
   * <p>
   * If the dates are equal, an empty stream is returned.
   * If the end is before the start, an exception is thrown.
   *
   * @param range  the date range
   * @return the stream of holidays
   * @throws IllegalArgumentException if either date is outside the supported range
   */
  default int holidaysCount(DateRange range) {
    return Math.toIntExact(holidays(range).count());
  }
}
