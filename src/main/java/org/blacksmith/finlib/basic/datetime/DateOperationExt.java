package org.blacksmith.finlib.basic.datetime;

import java.time.temporal.Temporal;

public interface DateOperationExt extends DateOperation {
  <T extends Temporal> T addTo(T temporal, int q);

  <T extends Temporal> T subtractFrom(T temporal, int q);

  <T extends Temporal> T addToWithEomAdjust(T temporal, boolean eomAdjust);

  <T extends Temporal> T addToWithEomAdjust(T temporal, int q, boolean eomAdjust);
}
