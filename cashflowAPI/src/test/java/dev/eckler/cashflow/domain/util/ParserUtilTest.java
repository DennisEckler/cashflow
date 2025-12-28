package dev.eckler.cashflow.domain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import dev.eckler.cashflow.util.ParserUtil;

@SpringJUnitConfig
@Import(ParserUtil.class)
public class ParserUtilTest {

    @Autowired
    private ParserUtil parserUtil;

    @Test
    public void parseAmountTest1() throws ParseException {
        BigDecimal bd = new BigDecimal("-5.86");
        assertEquals(bd, parserUtil.parseAmount("-5,86"));
    }

    @Test
    public void parseAmountTest2() throws ParseException {
        BigDecimal bd = new BigDecimal("-1111.61");
        assertEquals(bd, parserUtil.parseAmount("-1.111,61"));
    }

    @Test
    public void parseAmountTest3() throws ParseException {
        BigDecimal bd = new BigDecimal("1111.46");
        assertEquals(bd, parserUtil.parseAmount("1.111,46"));
    }

    @Test
    public void parseAmountTest4() throws ParseException {
        BigDecimal bd1 = new BigDecimal("-3438234.09");
        BigDecimal bd2 = new BigDecimal("3438234.09");
        assertEquals(bd1, parserUtil.parseAmount("-3.438.234,09"));
        assertEquals(bd2, parserUtil.parseAmount("3.438.234,09"));
    }

}
