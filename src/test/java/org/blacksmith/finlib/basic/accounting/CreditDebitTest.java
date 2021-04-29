package org.blacksmith.finlib.basic.accounting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditDebitTest {
  @Test
  public void addTest() {
    ICreditDebit crdt = CreditDebit.of(8.004d, 4.00001d);
    ICreditDebit other = CreditDebit.of(-3.1d, 7.15d);
    var result = crdt.add(other);
  }

}
