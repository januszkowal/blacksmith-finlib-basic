package org.blacksmith.finlib.basic.calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.blacksmith.finlib.basic.calendar.policy.StandardWeekDayPolicy;
import org.blacksmith.finlib.basic.calendar.policy.WeekDayPolicy;
import org.junit.jupiter.api.Test;

public class DatePartHolidayPolicyTest {

  @Test
  public void standardWeekDayPolicyTest() {
    HolidayPolicy policy = StandardWeekDayPolicy.SAT_SUN;
    assertFalse(policy.isHoliday(LocalDate.of(2019, 5, 15)));
    assertTrue(policy.isHoliday(LocalDate.of(2019, 5, 25)));
    assertTrue(policy.isHoliday(LocalDate.of(2019, 5, 26)));
  }

  @Test
  public void userDefinedWeekDayPolicyTest() {
    HolidayPolicy policy = WeekDayPolicy.of(3,4);
    assertTrue(policy.isHoliday(LocalDate.of(2019, 5, 15)));
    assertTrue(policy.isHoliday(LocalDate.of(2019, 5, 16)));
    assertFalse(policy.isHoliday(LocalDate.of(2019, 5, 25)));
    assertFalse(policy.isHoliday(LocalDate.of(2019, 5, 26)));
  }
}
