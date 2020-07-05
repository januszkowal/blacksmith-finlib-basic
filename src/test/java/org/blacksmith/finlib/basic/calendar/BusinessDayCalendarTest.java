package org.blacksmith.finlib.basic.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.blacksmith.commons.datetime.DateRange;
import org.blacksmith.finlib.basic.calendar.policy.CombinedHolidayPolicy;
import org.blacksmith.finlib.basic.calendar.policy.DatePartHolidayPolicy;
import org.blacksmith.finlib.basic.calendar.policy.StandardWeekDayPolicy;
import org.blacksmith.finlib.basic.calendar.policy.helper.DatePartInMemoryProvider;
import org.blacksmith.finlib.basic.calendar.policy.helper.StandardDatePartExtractors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("BusinessDayCalendarTest")
@ExtendWith(org.blacksmith.test.TimingExtension.class)
public class BusinessDayCalendarTest {
  @Test
  public void holidayByWeekDay1() {
    /*
    holidays: 2019-05-15, 2019-05-16, 2019-05-17
    * */
    DatePartInMemoryProvider<LocalDate> hyc = DatePartInMemoryProvider.of(
        LocalDate.of(2019,5,15),
        LocalDate.of(2019,5,16),
        LocalDate.of(2019,6,15));
    DatePartHolidayPolicy<LocalDate> ymdProvider = new DatePartHolidayPolicy<>(StandardDatePartExtractors.DATE,hyc);
    BusinessDayCalendar cal = new BusinessDayCalendarWithPolicy(CombinedHolidayPolicy.of(ymdProvider));
    assertEquals(LocalDate.of(2019, 5,14),cal.nextOrSame(LocalDate.of(2019,5,14)));
    assertEquals(LocalDate.of(2019, 5,17),cal.nextOrSame(LocalDate.of(2019,5,15)));
    assertEquals(LocalDate.of(2019, 5,17),cal.nextOrSame(LocalDate.of(2019,5,16)));
    assertEquals(LocalDate.of(2019, 6,16),cal.nextOrSame(LocalDate.of(2019,6,15)));
    assertEquals(LocalDate.of(2019, 6,16),cal.nextOrSame(LocalDate.of(2019,6,16)));
    assertEquals(LocalDate.of(2019, 6,17),cal.nextOrSame(LocalDate.of(2019,6,17)));
    //
    assertEquals(LocalDate.of(2019, 5,14),cal.next(LocalDate.of(2019,5,13)));
    assertEquals(LocalDate.of(2019, 5,17),cal.next(LocalDate.of(2019,5,14)));
    assertEquals(LocalDate.of(2019, 5,17),cal.next(LocalDate.of(2019,5,15)));
    assertEquals(LocalDate.of(2019, 6,16),cal.next(LocalDate.of(2019,6,15)));
    //
    assertEquals(LocalDate.of(2019, 5,14),cal.next(LocalDate.of(2019,5,13),1));
    assertEquals(LocalDate.of(2019, 5,17),cal.next(LocalDate.of(2019,5,14),1));
    assertEquals(LocalDate.of(2019, 5,17),cal.next(LocalDate.of(2019,5,15),1));
    assertEquals(LocalDate.of(2019, 6,16),cal.next(LocalDate.of(2019,6,15),1));
    //
    assertEquals(LocalDate.of(2019, 5,13),cal.previous(LocalDate.of(2019,5,14)));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,15)));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,16)));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,17)));
    assertEquals(LocalDate.of(2019, 6,17),cal.previous(LocalDate.of(2019,6,18)));
    //
    assertEquals(LocalDate.of(2019, 5,13),cal.previous(LocalDate.of(2019,5,14),1));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,15),1));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,16),1));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,17),1));
    assertEquals(LocalDate.of(2019, 6,17),cal.previous(LocalDate.of(2019,6,18),1));
    //
    assertEquals(LocalDate.of(2019, 5,11),cal.next(LocalDate.of(2019,5,10),1));
    assertEquals(LocalDate.of(2019, 5,12),cal.next(LocalDate.of(2019,5,10),2));
    assertEquals(LocalDate.of(2019, 5,13),cal.next(LocalDate.of(2019,5,10),3));
    //
    assertEquals(LocalDate.of(2019, 5,14),cal.next(LocalDate.of(2019,5,13),1));
    assertEquals(LocalDate.of(2019, 5,17),cal.next(LocalDate.of(2019,5,13),2));
    assertEquals(LocalDate.of(2019, 5,18),cal.next(LocalDate.of(2019,5,13),3));
    //
    assertEquals(LocalDate.of(2019, 5,9),cal.previous(LocalDate.of(2019,5,10),1));
    assertEquals(LocalDate.of(2019, 5,8),cal.previous(LocalDate.of(2019,5,10),2));
    assertEquals(LocalDate.of(2019, 5,7),cal.previous(LocalDate.of(2019,5,10),3));
    //
    assertEquals(LocalDate.of(2019, 5,17),cal.previous(LocalDate.of(2019,5,18),1));
    assertEquals(LocalDate.of(2019, 5,14),cal.previous(LocalDate.of(2019,5,18),2));
    assertEquals(LocalDate.of(2019, 5,13),cal.previous(LocalDate.of(2019,5,18),3));
    //
    assertEquals(LocalDate.of(2019, 5,11),cal.shift(LocalDate.of(2019,5,10),1));
    assertEquals(LocalDate.of(2019, 5,12),cal.shift(LocalDate.of(2019,5,10),2));
    assertEquals(LocalDate.of(2019, 5,13),cal.shift(LocalDate.of(2019,5,10),3));
    assertEquals(LocalDate.of(2019, 5,17),cal.shift(LocalDate.of(2019,5,18),-1));
    assertEquals(LocalDate.of(2019, 5,14),cal.shift(LocalDate.of(2019,5,18),-2));
    assertEquals(LocalDate.of(2019, 5,13),cal.shift(LocalDate.of(2019,5,18),-3));
  }

  @Test
  public void dayCountTest1() {
    BusinessDayCalendar noHolidaysCalendar = BusinessDayCalendarWithPolicy.of((d)->false);
    BusinessDayCalendar weekendCalendar = BusinessDayCalendarWithPolicy.of(CombinedHolidayPolicy.of(
        StandardWeekDayPolicy.SAT_SUN));
    assertEquals(30,noHolidaysCalendar.businessDaysCount(DateRange.closedOpen(LocalDate.of(2019, 1, 1),LocalDate.of(2019,
        1,31))));
    assertEquals(31,noHolidaysCalendar.businessDaysCount(DateRange.closed(LocalDate.of(2019, 1, 1),LocalDate.of(2019, 1,31))));
    assertEquals(22,weekendCalendar.businessDaysCount(DateRange.closedOpen(LocalDate.of(2019, 1, 1),LocalDate.of(2019,
        1,31))));
    assertEquals(23,weekendCalendar.businessDaysCount(DateRange.closed(LocalDate.of(2019, 1, 1),LocalDate.of(2019, 1,31))));
  }
}
