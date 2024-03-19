package dev.eckler.cashflow.model.transaction;

import static dev.eckler.cashflow.model.transaction.TransactionService.parseAmount;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {


  @Test
  public void parseAmountTest1() {
    BigDecimal bd = new BigDecimal("-5.86");
    assertEquals(bd, parseAmount("-5,86"));
  }
  @Test
  public void parseAmountTest2() {
    BigDecimal bd = new BigDecimal("-1111.61");
    assertEquals(bd, parseAmount("-1.111,61"));
  }
  @Test
  public void parseAmountTest3() {
    BigDecimal bd = new BigDecimal("1111.46");
    assertEquals(bd, parseAmount("1.111,46"));
  }
  @Test
  public void parseAmountTest4() {
    BigDecimal bd1 = new BigDecimal("-3438234.09");
    BigDecimal bd2 = new BigDecimal("3438234.09");
    assertEquals(bd1, parseAmount("-3.438.234,09"));
    assertEquals(bd2, parseAmount("3.438.234,09"));
  }

}