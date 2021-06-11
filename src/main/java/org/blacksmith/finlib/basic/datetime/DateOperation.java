package org.blacksmith.finlib.basic.datetime;

import java.time.temporal.Temporal;

public interface DateOperation {
  <T extends Temporal> T addTo(T temporal);

  <T extends Temporal> T subtractFrom(T temporal);
}
