package org.blacksmith.finlib.basic.datetime;

import java.io.Serializable;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;

import org.blacksmith.commons.arg.ArgChecker;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Frequency implements DateOperationExt, Serializable {
  private static final Map<String, Frequency> frequencyMap = new HashMap<>();
  /**
   * A periodic frequency of one day, also known as daily
   */
  public static final Frequency P1D = addFrequency(1, TimeUnit.DAY);
  /**
   * A periodic frequency of two days.
   */
  public static final Frequency P2D = addFrequency(2, TimeUnit.DAY);
  /**
   * A periodic frequency of 1 week (7 days), also known as weekly.
   * There are considered to be 52 events per year with this frequency.
   */
  public static final Frequency P1W = addFrequency(1, TimeUnit.WEEK);
  /**
   * A periodic frequency of 2 weeks (14 days), also known as bi-weekly.
   * There are considered to be 26 events per year with this frequency.
   */
  public static final Frequency P2W = addFrequency(2, TimeUnit.WEEK);
  /**
   * A periodic frequency of 4 weeks (28 days), also known as lunar.
   * There are considered to be 13 events per year with this frequency.
   */
  public static final Frequency P4W = addFrequency(4, TimeUnit.WEEK);
  /**
   * A periodic frequency of 13 weeks (91 days).
   * There are considered to be 4 events per year with this frequency.
   */
  public static final Frequency P13W = addFrequency(13, TimeUnit.WEEK);
  /**
   * A periodic frequency of 26 weeks (182 days).
   * There are considered to be 2 events per year with this frequency.
   */
  public static final Frequency P26W = addFrequency(26, TimeUnit.WEEK);
  /**
   * A periodic frequency of 52 weeks (364 days).
   * There is considered to be 1 event per year with this frequency.
   */
  public static final Frequency P52W = addFrequency(52, TimeUnit.WEEK);
  /**
   * A periodic frequency of 1 month, also known as monthly.
   * There are 12 events per year with this frequency.
   */
  public static final Frequency P1M = addFrequency(1, TimeUnit.MONTH);
  /**
   * A periodic frequency of 2 months, also known as bi-monthly.
   * There are 6 events per year with this frequency.
   */
  public static final Frequency P2M = addFrequency(2, TimeUnit.MONTH);
  /**
   * A periodic frequency of 3 months, also known as quarterly.
   * There are 4 events per year with this frequency.
   */
  public static final Frequency P3M = addFrequency(3, TimeUnit.MONTH);
  /**
   * A periodic frequency of 4 months.
   * There are 3 events per year with this frequency.
   */
  public static final Frequency P4M = addFrequency(4, TimeUnit.MONTH);
  /**
   * A periodic frequency of 6 months, also known as semi-annual.
   * There are 2 events per year with this frequency.
   */
  public static final Frequency P6M = addFrequency(6, TimeUnit.MONTH);
  /**
   * A periodic frequency of 9 months.
   * There are 2 events per year with this frequency.
   */
  public static final Frequency P9M = addFrequency(9, TimeUnit.MONTH);
  /**
   * A periodic frequency of 12 months (1 year), also known as annual.
   * There is 1 event per year with this frequency.
   */
  public static final Frequency P1Y = addFrequency(1, TimeUnit.YEAR);
  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1;
  /**
   * The artificial maximum length of a normal tenor in years.
   */
  private static final int MAX_YEARS = 1_000;
  /**
   * The artificial maximum length of a normal tenor in months.
   */
  private static final int MAX_MONTHS = MAX_YEARS * 12;
  /**
   * The artificial length in years of the 'Term' frequency.
   */
  private static final int TERM_YEARS = 10_000;
  /**
   * A periodic frequency matching the term.
   * Also known as zero-coupon.
   * This is represented using the period 10,000 years.
   * There are no events per year with this frequency.
   */
  public static final Frequency TERM = addFrequency(TERM_YEARS, TimeUnit.YEAR, "TERM");
  @EqualsAndHashCode.Include
  private final TimeUnit unit;
  @EqualsAndHashCode.Include
  private final int amount;
  boolean isAnnual;
  @ToString.Include
  private String name;
  private int months;
  private int eventsPerYear;
  private double eventsPerYearEstimate;
  private Period period;

  private Frequency(final Period period) {
    ArgChecker.isFalse(period.isZero(), "Frequency period must not be zero");
    ArgChecker.isFalse(period.isNegative(), "Frequency period must not be negative");
    //TODO multi-unit periods
    if (period.getUnits().size() > 1 && period.getDays() > 0) {
      throw new IllegalArgumentException("Multiple unit period not supported");
    }
    if (period.getYears() > 0) {
      if (period.getMonths() == 0) {
        this.unit = TimeUnit.YEAR;
        this.amount = period.getYears();
      } else {
        this.unit = TimeUnit.MONTH;
        this.amount = (int) period.toTotalMonths();
      }
    } else if (period.getMonths() > 0) {
      this.unit = TimeUnit.MONTH;
      this.amount = (int) period.toTotalMonths();
    } else {
      this.unit = TimeUnit.DAY;
      this.amount = period.getDays();
    }
    this.period = this.unit.toPeriod(this.amount);
    setEvents();
  }

  private Frequency(final int amount, final TimeUnit unit) {
    ArgChecker.isTrue(amount > 0, "Amount must be greater than zero");
    ArgChecker.notNull(unit, "Unit must be not null");
    this.unit = unit;
    this.amount = amount;
    this.name = frequencyName(this.amount, this.unit);
    this.period = this.unit.toPeriod(this.amount);
    setEvents();
  }

  private Frequency(final int amount, final TimeUnit unit, String name) {
    ArgChecker.isTrue(amount > 0, "Amount must be greater than zero");
    ArgChecker.notNull(unit, "Unit must be not null");
    this.unit = unit;
    this.amount = amount;
    this.name = name;
    this.period = this.unit.toPeriod(this.amount);
    setEvents();
  }

  public static Frequency parse(String frequency) {
    ArgChecker.checkStringLength(frequency, 3, 10);
    ArgChecker.isTrue(frequency.substring(0, 1).equals("P"), "Frequency must begins with P");
    int amount = Integer.parseInt(frequency, 1, frequency.length() - 1, 10);
    String frequencyUnit = frequency.substring(frequency.length() - 1);
    TimeUnit unit = TimeUnit.ofSymbol(frequencyUnit);
    return new Frequency(amount, unit);
  }

  public static Frequency of(final int amount, final TimeUnit unit) {
    return new Frequency(amount, unit);
  }

  public static Frequency ofPeriod(Period period) {
    return new Frequency(period);
  }

  public static Frequency ofDays(int days) {
    ArgChecker.isTrue(days > 0, "Amount of days must be greater than zero");
    if (days % 7 == 0) {
      return ofWeeks(days / 7);
    } else {
      return createIfNotExists(days, TimeUnit.DAY);
    }
  }

  public static Frequency createIfNotExists(int amount, TimeUnit unit) {
    String name = frequencyName(amount, unit);
    Frequency frequency = frequencyMap.get(name);
    if (frequency == null) {
      frequency = new Frequency(amount, unit);
    }
    return frequency;
  }

  /**
   * Obtains an instance backed by a period of weeks.
   *
   * @param weeks the number of weeks
   * @return the periodic frequency
   * @throws IllegalArgumentException if weeks is negative or zero
   */
  public static Frequency ofWeeks(int weeks) {
    ArgChecker.isTrue(weeks > 0, "Amount of weeks must be greater than zero");
    return createIfNotExists(weeks, TimeUnit.WEEK);
  }

  /**
   * Obtains an instance backed by a period of months.
   * <p>
   * Months are not normalized into years.
   *
   * @param months the number of months
   * @return the periodic frequency
   * @throws IllegalArgumentException if months is negative, zero or over 12,000
   */
  public static Frequency ofMonths(int months) {
    ArgChecker.isTrue(months > 0, "Amount of months must be greater than zero");
    if (months > MAX_MONTHS) {
      throw new IllegalArgumentException(FrequencyUtils.maxMonthMsg(MAX_MONTHS));
    }
    if (months % 12 == 0) {
      return ofYears(months / 12);
    } else {
      return createIfNotExists(months, TimeUnit.MONTH);
    }
  }

  /**
   * Obtains an instance backed by a period of years.
   *
   * @param years the number of years
   * @return the periodic frequency
   * @throws IllegalArgumentException if years is negative, zero or over 1,000
   */
  public static Frequency ofYears(int years) {
    ArgChecker.isTrue(years > 0, "Amount of years must be greater than zero");
    if (years > MAX_YEARS) {
      throw new IllegalArgumentException(FrequencyUtils.maxYearMsg(MAX_YEARS));
    }
    return createIfNotExists(years, TimeUnit.YEAR);
  }

  private static String frequencyName(final int amount, final TimeUnit unit) {
    return "P" + amount + unit.symbol();
  }

  private static Frequency addFrequency(int amount, TimeUnit unit) {
    Frequency frequency = new Frequency(amount, unit);
    frequencyMap.put(frequency.name, frequency);
    return frequency;
  }

  private static Frequency addFrequency(int amount, TimeUnit unit, String name) {
    Frequency frequency = new Frequency(amount, unit, name);
    frequencyMap.put(frequency.name, frequency);
    return frequency;
  }

  public boolean isAnnual() {
    return isAnnual;
  }

  public int getMonths() {
    return this.months;
  }

  public Period toPeriod() {
    return this.period;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public int eventsPerYear() {
    return this.eventsPerYear;
  }

  public double eventsPerYearEstimate() {
    return this.eventsPerYearEstimate;
  }

  @Override
  public <T extends Temporal> T addTo(T temporal) {
    //    return (T)this.period.addTo(temporal);
    return this.unit.addTo(temporal, amount);
  }

  @Override
  public <T extends Temporal> T subtractFrom(T temporal) {
    return this.unit.subtractFrom(temporal, amount);
  }

  @Override
  public <T extends Temporal> T addTo(T temporal, int i) {
    return this.unit.addTo(temporal, i * amount);
  }

  @Override
  public <T extends Temporal> T subtractFrom(T temporal, int i) {
    //        return (T)this.period.subtractFrom(temporal);
    return this.unit.subtractFrom(temporal, i * amount);
  }

  @Override
  public <T extends Temporal> T addToWithEomAdjust(T temporal, boolean eomAadjust) {
    return (T) this.unit.addToWithEomAdjust(temporal, amount, eomAadjust);
  }

  @Override
  public <T extends Temporal> T addToWithEomAdjust(T temporal, int i, boolean eomAadjust) {
    return (T) this.unit.addToWithEomAdjust(temporal, i * amount, eomAadjust);
  }

  private void setEvents() {
    if (amount == 0) {
      return;
    }
    this.period = this.unit.toPeriod(this.amount);
    long unitSecs = 0;
    switch (unit) {
      case DAY:
        unitSecs = amount * ChronoUnit.DAYS.getDuration().getSeconds();
        eventsPerYear = (364 % amount == 0) ? 364 / amount : 0;
        eventsPerYearEstimate = 364d / amount;
        //eventsPerYearEstimate = ChronoUnit.YEARS.getDuration().getSeconds() / unitSecs;
        break;
      case WEEK:
        unitSecs = amount * ChronoUnit.WEEKS.getDuration().getSeconds();
        int days = amount * 7;
        eventsPerYear = (364 % days == 0) ? 364 / days : 0;
        eventsPerYearEstimate = 364d / days;
        //eventsPerYearEstimate = ChronoUnit.YEARS.getDuration().getSeconds() / unitSecs;
        break;
      case MONTH:
        isAnnual = amount % 12 == 0;
        eventsPerYear = (12 % amount == 0) ? 12 / amount : 0;
        eventsPerYearEstimate = 12d / amount;
        months = amount;
        break;
      case QUARTER:
        isAnnual = amount % 4 == 0;
        eventsPerYear = (4 % amount == 0) ? 4 / amount : 0;
        eventsPerYearEstimate = 4d / amount;
        months = amount * 3;
        break;
      case HALF_YEAR:
        isAnnual = amount % 2 == 0;
        eventsPerYear = (2 % amount == 0) ? 2 / amount : 0;
        eventsPerYearEstimate = 2d / amount;
        months = amount * 6;
        break;
      case YEAR:
        isAnnual = true;
        eventsPerYear = (1 % amount == 0) ? 1 / amount : 0;
        eventsPerYearEstimate = 1d / amount;
        months = amount * 12;
        break;
    }
  }
}
