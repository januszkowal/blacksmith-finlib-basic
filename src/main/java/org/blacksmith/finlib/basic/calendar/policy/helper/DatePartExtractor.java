package org.blacksmith.finlib.basic.calendar.policy.helper;

import java.time.LocalDate;

public interface DatePartExtractor<U> {
  U extract(LocalDate date);
}
