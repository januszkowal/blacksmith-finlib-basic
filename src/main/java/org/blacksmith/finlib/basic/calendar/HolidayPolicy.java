package org.blacksmith.finlib.basic.calendar;

import java.time.LocalDate;

/**
 * Returns if the specified date is holiday
 * */
@FunctionalInterface
public interface HolidayPolicy {
  boolean isHoliday(LocalDate date);
}
