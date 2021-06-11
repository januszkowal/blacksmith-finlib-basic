package org.blacksmith.finlib.basic.datetime;

import java.io.Serializable;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import org.blacksmith.commons.arg.ArgChecker;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Frequency implements DateOperationExt, Serializable {
  /**
   * A periodic frequency of one day.
   * Also known as daily.
   * There are considered to be 364 events per year with this frequency.
   */
  public static final Frequency P1D = new Frequency(1, TimeUnit.DAY);
  /**
   * A periodic frequency of 1 week (7 days).
   * Also known as weekly.
   * There are considered to be 52 events per year with this frequency.
   */
  public static final Frequency P1W = new Frequency(1, TimeUnit.WEEK);
  /**
   * A periodic frequency of 2 weeks (14 days).
   * Also known as bi-weekly.
   * There are considered to be 26 events per year with this frequency.
   */
  public static final Frequency P2W = new Frequency(2, TimeUnit.WEEK);
  /**
   * A periodic frequency of 4 weeks (28 days).
   * Also known as lunar.
   * There are considered to be 13 events per year with this frequency.
   */
  public static final Frequency P4W = new Frequency(4, TimeUnit.WEEK);
  /**
   * A periodic frequency of 13 weeks (91 days).
   * There are considered to be 4 events per year with this frequency.
   */
  public static final Frequency P13W = new Frequency(13, TimeUnit.WEEK);
  /**
   * A periodic frequency of 26 weeks (182 days).
   * There are considered to be 2 events per year with this frequency.
   */
  public static final Frequency P26W = new Frequency(26, TimeUnit.WEEK);
  /**
   * A periodic frequency of 52 weeks (364 days).
   * There is considered to be 1 event per year with this frequency.
   */
  public static final Frequency P52W = new Frequency(52, TimeUnit.WEEK);
  //  private final Period period;
  /**
   * A periodic frequency of 1 month.
   * Also known as monthly.
   * There are 12 events per year with this frequency.
   */
  public static final Frequency P1M = new Frequency(1, TimeUnit.MONTH);
  /**
   * A periodic frequency of 2 months.
   * Also known as bi-monthly.
   * There are 6 events per year with this frequency.
   */
  public static final Frequency P2M = new Frequency(2, TimeUnit.MONTH);
  /**
   * A periodic frequency of 3 months.
   * Also known as quarterly.
   * There are 4 events per year with this frequency.
   */
  public static final Frequency P3M = new Frequency(3, TimeUnit.MONTH);
  /**
   * A periodic frequency of 4 months.
   * There are 3 events per year with this frequency.
   */
  public static final Frequency P4M = new Frequency(4, TimeUnit.MONTH);
  /**
   * A periodic frequency of 6 months.
   * Also known as semi-annual.
   * There are 2 events per year with this frequency.
   */
  public static final Frequency P6M = new Frequency(6, TimeUnit.MONTH);
  /**
   * A periodic frequency of 9 months.
   * Also known as semi-annual.
   * There are 2 events per year with this frequency.
   */
  public static final Frequency P9M = new Frequency(9, TimeUnit.MONTH);
  /**
   * A periodic frequency of 12 months (1 year).
   * Also known as annual.
   * There is 1 event per year with this frequency.
   */
  public static final Frequency P1Y = new Frequency(1, TimeUnit.YEAR);
  /**
   * A periodic frequency of 2 years.
   * Also known as zero-coupon.
   * This is represented using the period 10,000 years.
   * There are no events per year with this frequency.
   */
  public static final Frequency P2Y = new Frequency(2, TimeUnit.YEAR);
  /**
   * A periodic frequency of 3 years.
   * Also known as zero-coupon.
   * This is represented using the period 10,000 years.
   * There are no events per year with this frequency.
   */
  public static final Frequency P3Y = new Frequency(3, TimeUnit.YEAR);
  /**
   * A periodic frequency of 5 years.
   * Also known as zero-coupon.
   * This is represented using the period 10,000 years.
   * There are no events per year with this frequency.
   */
  public static final Frequency P5Y = new Frequency(5, TimeUnit.YEAR);
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
  public static final Frequency TERM = new Frequency(TERM_YEARS, TimeUnit.YEAR, "TERM");
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

  public Frequency(final Period period) {
    ArgChecker.isFalse(period.isZero(), "Frequency period must not be zero");
    ArgChecker.isFalse(period.isNegative(), "Frequency period must not be negative");
    //TODO multi-unit periods
    if (period.getUnits().size() > 1) {
      throw new IllegalArgumentException("Multiple unit period not supported");
    }
//    if (period.getUnits().size() > 0 && period.getDays() > 0) {
//      throw new IllegalArgumentException("Multiple unit period not supported");
//    }
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
      this.amount = period.getMonths();
    } else {
      this.unit = TimeUnit.DAY;
      this.amount = period.getDays();
    }
    this.period = frequencyToPeriod(this.amount, this.unit);
    setEvents();
  }

  public Frequency(final int amount, final TimeUnit unit) {
    this.unit = unit;
    this.amount = amount;
    this.name = periodName(this.amount, this.unit);
    this.period = frequencyToPeriod(this.amount, this.unit);
    setEvents();
  }

  public Frequency(final int amount, final TimeUnit unit, String name) {
    this.unit = unit;
    this.amount = amount;
    this.name = name;
    this.period = frequencyToPeriod(this.amount, this.unit);
    setEvents();
  }

  public static Frequency of(String period) {
    ArgChecker.checkStringLength(period, 2, 10);
    int amount = Integer.parseInt(period, 0, period.length() - 1, 10);
    TimeUnit unit = TimeUnit.ofSymbol(period.substring(period.length() - 1));
    return new Frequency(amount, unit);
  }

  public static Frequency ofPeriod(Period period) {
    return new Frequency(period);
  }

  public static Frequency ofDays(int days) {
    if (days % 7 == 0) {
      return ofWeeks(days / 7);
    } else {
      return new Frequency(days, TimeUnit.DAY);
    }
  }

  public static Frequency ofDaysWithoutAlignToWeeks(int days) {
    return new Frequency(days, TimeUnit.DAY);
  }

  /**
   * Obtains an instance backed by a period of weeks.
   *
   * @param weeks the number of weeks
   * @return the periodic frequency
   * @throws IllegalArgumentException if weeks is negative or zero
   */
  public static Frequency ofWeeks(int weeks) {
    switch (weeks) {
      case 1:
        return P1W;
      case 2:
        return P2W;
      case 4:
        return P4W;
      case 13:
        return P13W;
      case 26:
        return P26W;
      case 52:
        return P52W;
      default:
        return new Frequency(weeks, TimeUnit.WEEK);
    }
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
    if (months % 12 == 0) {
      return ofYears(months / 12);
    } else {
      switch (months) {
        case 1:
          return P1M;
        case 2:
          return P2M;
        case 3:
          return P3M;
        case 4:
          return P4M;
        case 6:
          return P6M;
        default:
          if (months > MAX_MONTHS) {
            throw new IllegalArgumentException(FrequencyUtils.maxMonthMsg(MAX_MONTHS));
          }
          return new Frequency(months, TimeUnit.MONTH);
      }
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
    switch (years) {
      case 1:
        return P1Y;
      case 2:
        return P2Y;
      case 3:
        return P3Y;
      case 5:
        return P5Y;
      default:
        if (years > MAX_YEARS) {
          throw new IllegalArgumentException(FrequencyUtils.maxYearMsg(MAX_YEARS));
        }
        return new Frequency(years, TimeUnit.YEAR);
    }
  }

  public static Period frequencyToPeriod(final int amount, final TimeUnit unit) {
    switch (unit) {
      case DAY:
        return Period.ofDays(amount);
      case WEEK:
        return Period.ofWeeks(amount);
      case MONTH:
        return Period.ofMonths(amount);
      case QUARTER:
        return Period.ofMonths(amount * 3);
      case HALF_YEAR:
        return Period.ofMonths(amount * 6);
      case YEAR:
        return Period.ofYears(amount);
      default:
        return null;
    }
  }

  public boolean isAnnual() {
    return isAnnual;
  }

  public int getMonths() {
    return this.months;
  }

  public Period toPeriod() {
    return frequencyToPeriod(this.amount, this.unit);
  }

  @Override
  public String toString() {
    return String.format("%d%s", amount, unit.symbol());
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
  public <T extends Temporal> T addTo(T temporal, int i) {
    return this.unit.addTo(temporal, i * amount);
  }

  @Override
  public <T extends Temporal> T addToWithEomAdjust(T temporal, boolean eomAadjust) {
    return (T)this.unit.addToWithEomAdjust(temporal, amount, eomAadjust);
  }

  @Override
  public <T extends Temporal> T addToWithEomAdjust(T temporal, int i, boolean eomAadjust) {
    return (T)this.unit.addToWithEomAdjust(temporal, i * amount, eomAadjust);
  }

  @Override
  public <T extends Temporal> T subtractFrom(T temporal) {
    return this.unit.subtractFrom(temporal, amount);
  }

  @Override
  public <T extends Temporal> T subtractFrom(T temporal, int i) {
//        return (T)this.period.subtractFrom(temporal);
    return this.unit.subtractFrom(temporal, i * amount);
  }

  private void setEvents() {
    if (amount == 0) {
      return;
    }
    this.period = frequencyToPeriod(this.amount, this.unit);
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

  private String periodName(final int amount, final TimeUnit unit) {
    return amount + unit.symbol();
  }
}
