package org.blacksmith.finlib.basic.datetime;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FrequencyUtils {
  public static String maxMonthMsg(int maxMonths) {
    DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ENGLISH));
    return "Months must not exceed " + formatter.format(maxMonths);
  }

  public static String maxYearMsg(int maxYears) {
    DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ENGLISH));
    return "Years must not exceed " + formatter.format(maxYears);
  }
}
