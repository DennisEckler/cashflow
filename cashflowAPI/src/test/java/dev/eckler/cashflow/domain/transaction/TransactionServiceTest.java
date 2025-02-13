package dev.eckler.cashflow.domain.transaction;

import static dev.eckler.cashflow.domain.transaction.TransactionService.parseAmount;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {

  @Test
  public void parseAmountTest1() throws ParseException {
    BigDecimal bd = new BigDecimal("-5.86");
    assertEquals(bd, parseAmount("-5,86"));
  }

  @Test
  public void parseAmountTest2() throws ParseException {
    BigDecimal bd = new BigDecimal("-1111.61");
    assertEquals(bd, parseAmount("-1.111,61"));
  }

  @Test
  public void parseAmountTest3() throws ParseException {
    BigDecimal bd = new BigDecimal("1111.46");
    assertEquals(bd, parseAmount("1.111,46"));
  }

  @Test
  public void parseAmountTest4() throws ParseException {
    BigDecimal bd1 = new BigDecimal("-3438234.09");
    BigDecimal bd2 = new BigDecimal("3438234.09");
    assertEquals(bd1, parseAmount("-3.438.234,09"));
    assertEquals(bd2, parseAmount("3.438.234,09"));
  }

}
