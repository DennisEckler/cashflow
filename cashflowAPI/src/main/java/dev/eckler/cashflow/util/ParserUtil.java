package dev.eckler.cashflow.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ParserUtil {

    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public BigDecimal parseAmount(String amount) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,###.00", symbols);
        return new BigDecimal(String.valueOf(df.parse(amount)));
    }

}
