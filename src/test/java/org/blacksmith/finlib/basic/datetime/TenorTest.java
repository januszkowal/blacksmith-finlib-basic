package org.blacksmith.finlib.basic.datetime;

import java.time.LocalDate;
import java.time.Period;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TenorTest {

  @Test
  void createPeriodTest() {
    var tenor2Y = Tenor.ofYears(2);
    Assertions.assertThat(tenor2Y.toString()).isEqualTo("2Y");
    Assertions.assertThat(tenor2Y.getPeriod().toString()).isEqualTo("P2Y");
    var tenor2M = Tenor.of(Period.between(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 3, 1)));
    Assertions.assertThat(tenor2M.toString()).isEqualTo("2M");
    Assertions.assertThat(tenor2M.getPeriod().toString()).isEqualTo("P2M");
    var tenor1M27D = Tenor.of(Period.between(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 3, 1).minusDays(1)));
    Assertions.assertThat(tenor1M27D.toString()).isEqualTo("1M27D");
    Assertions.assertThat(tenor1M27D.getPeriod().toString()).isEqualTo("P1M27D");  }
}
