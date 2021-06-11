package org.blacksmith.finlib.basic.datetime;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.blacksmith.commons.enums.EnumUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeUnitEnumTest {

  @Test
  void getEnumByName() {
    assertEquals(TimeUnit.DAY, EnumUtils.getEnumByName(TimeUnit.class, "DAY"));
    assertNull(EnumUtils.getEnumByName(TimeUnit.class, "DAYx"));
    assertEquals(TimeUnit.WEEK, EnumUtils.getEnumByName(TimeUnit.class, "DAYx", TimeUnit.WEEK));
  }

  @Test
  void getEnumNameMap() {
    assertThat(EnumUtils.getEnumNameMap(TimeUnit.class))
        .containsAllEntriesOf(Map.of(TimeUnit.DAY, TimeUnit.DAY.name(),
            TimeUnit.WEEK, TimeUnit.WEEK.name(),
            TimeUnit.MONTH, TimeUnit.MONTH.name(),
            TimeUnit.QUARTER, TimeUnit.QUARTER.name(),
            TimeUnit.HALF_YEAR, TimeUnit.HALF_YEAR.name(),
            TimeUnit.YEAR, TimeUnit.YEAR.name()));
  }

  @Test
  void getAttrEnumMap() {
    assertThat(EnumUtils.getValueEnumMap(TimeUnit.class, TimeUnit::symbol))
        .containsAllEntriesOf(Map.of(TimeUnit.DAY.symbol(), TimeUnit.DAY,
            TimeUnit.WEEK.symbol(), TimeUnit.WEEK,
            TimeUnit.MONTH.symbol(), TimeUnit.MONTH,
            TimeUnit.QUARTER.symbol(), TimeUnit.QUARTER,
            TimeUnit.HALF_YEAR.symbol(), TimeUnit.HALF_YEAR,
            TimeUnit.YEAR.symbol(), TimeUnit.YEAR));
  }

  @Test
  void getEnumAttrMap() {
    assertThat(EnumUtils.getEnumValueMap(TimeUnit.class, TimeUnit::symbol))
        .containsAllEntriesOf(Map.of(TimeUnit.DAY, TimeUnit.DAY.symbol(),
            TimeUnit.WEEK, TimeUnit.WEEK.symbol(),
            TimeUnit.MONTH, TimeUnit.MONTH.symbol(),
            TimeUnit.QUARTER, TimeUnit.QUARTER.symbol(),
            TimeUnit.HALF_YEAR, TimeUnit.HALF_YEAR.symbol(),
            TimeUnit.YEAR, TimeUnit.YEAR.symbol()));
  }

  @Test
  void getEnumList() {
    assertThat(EnumUtils.getEnumList(TimeUnit.class))
        .containsExactlyInAnyOrderElementsOf(EnumSet.allOf(TimeUnit.class));
  }

  @Test
  void getEnumSet() {
    assertThat(EnumUtils.getEnumSet(TimeUnit.class))
        .containsExactlyInAnyOrderElementsOf(EnumSet.allOf(TimeUnit.class));
  }

  @Test
  void getEnumNames() {
    assertIterableEquals(List.of("DAY", "YEAR"), EnumUtils.getEnumNames(List.of(TimeUnit.DAY, TimeUnit.YEAR)));
  }

  @Test
  void getEnumNamesList() {
    assertIterableEquals(List.of("DAY", "WEEK", "MONTH", "QUARTER", "HALF_YEAR", "YEAR"),
        EnumUtils.getEnumNamesList(TimeUnit.class));
  }

  @Test
  void getEnumNamesArray() {
    assertArrayEquals(Arrays.array("DAY", "WEEK", "MONTH", "QUARTER", "HALF_YEAR", "YEAR"),
        EnumUtils.getEnumNamesArray(TimeUnit.class));
  }

  @Test
  void isValidEnum() {
    assertTrue(EnumUtils.isValidEnum(TimeUnit.class, "WEEK"));
    assertFalse(EnumUtils.isValidEnum(TimeUnit.class, "Week"));
  }

  @Test
  void getName() {
    assertEquals("WEEK", EnumUtils.getName(TimeUnit.WEEK));
  }

  @Test
  void inList() {
    assertTrue(EnumUtils.inList(TimeUnit.DAY, List.of(TimeUnit.DAY)));
    assertTrue(EnumUtils.inList(TimeUnit.DAY, List.of(TimeUnit.MONTH, TimeUnit.DAY)));
    assertTrue(EnumUtils.inList(TimeUnit.DAY, List.of(TimeUnit.WEEK, TimeUnit.DAY)));
    assertFalse(EnumUtils.inList(TimeUnit.DAY, List.of(TimeUnit.MONTH)));
    assertFalse(EnumUtils.inList(TimeUnit.DAY, List.of()));
  }

  @Test
  void inArray() {
    assertTrue(EnumUtils.inArray(TimeUnit.DAY, Arrays.array(TimeUnit.DAY)));
    assertTrue(EnumUtils.inArray(TimeUnit.DAY, Arrays.array(TimeUnit.MONTH, TimeUnit.DAY)));
    assertTrue(EnumUtils.inArray(TimeUnit.DAY, Arrays.array(TimeUnit.WEEK, TimeUnit.DAY)));
    assertFalse(EnumUtils.inArray(TimeUnit.DAY, Arrays.array(TimeUnit.MONTH)));
    assertFalse(EnumUtils.inArray(TimeUnit.DAY, Arrays.array()));
  }
}
