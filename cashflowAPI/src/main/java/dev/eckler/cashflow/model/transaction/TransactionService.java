package dev.eckler.cashflow.model.transaction;

import dev.eckler.cashflow.exception.PeriodExistsException;
import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.category.CategoryRepository;
import dev.eckler.cashflow.model.identifier.Identifier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final CategoryRepository categoryRepository;
  private final Logger logger = LoggerFactory.getLogger(TransactionService.class);


  private TransactionService(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository) {
    this.transactionRepository = transactionRepository;
    this.categoryRepository = categoryRepository;
  }

  List<Transaction> parseCsv(InputStream fileInputStream, final String USERID, JSONObject json)
      throws JSONException {

    List<Transaction> transactions = new ArrayList<>();
    List<Category> categories = categoryRepository.findAllByUserID(USERID);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {

      final long BLANK_ROWS = json.getLong("blankRows");
      final int DATE_INDEX = json.getInt("date");
      final int AMOUNT_INDEX = json.getInt("amount");
      final int PURPOSE_INDEX = json.getInt("purpose");
      final int SOURCE_INDEX = json.getInt("source");
      final String YEAR = json.getString("year");
      final String MONTH = json.getString("month");

      skipIfPeriodAlreadyExist(YEAR, MONTH);

      reader.lines().skip(BLANK_ROWS).forEach(row -> {

            String[] column = row.split(";");

            LocalDate date = parseDate(column[DATE_INDEX]);
            BigDecimal amount = parseAmount(column[AMOUNT_INDEX]);
            String source = column[SOURCE_INDEX];
            String purpose = column[PURPOSE_INDEX];
            Identifier identifier = categorize(categories, source, purpose);
            Transaction transaction = new Transaction(date, amount, USERID, purpose, source,
                identifier);
            transactions.add(transaction);
          }
      );
      return transactions;
    } catch (IOException | PeriodExistsException e) {
      throw new RuntimeException(e);
    }
  }

  public static BigDecimal parseAmount(String amount) {
    DecimalFormat df = new DecimalFormat("#,###.00");
    try {
      return new BigDecimal(String.valueOf(df.parse(amount)));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }


  private void skipIfPeriodAlreadyExist(String year, String month) throws PeriodExistsException {
    if (transactionRepository.getNumberOfYearMonthMatches(year, month) > 0) {
      throw new PeriodExistsException(
          "This Period exists with year: " + year + " and month: " + month);
    }
  }

  private Identifier categorize(List<Category> categories, String source, String purpose) {
    for (Category category : categories) {
      for (Identifier identifier : category.getIdentifier()) {
        if (source.trim().toLowerCase()
            .contains(identifier.getIdentifierLabel().trim().toLowerCase())
            || purpose.trim().toLowerCase()
            .contains(identifier.getIdentifierLabel().trim().toLowerCase())) {
          return identifier;
        }
      }
    }
    return null;
  }

  private LocalDate parseDate(String date) {
    String format = "dd.MM.yyyy";
    try {
      return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    } catch (DateTimeParseException e) {
      logger.error("Format {} not Supported for this date: {}", format, date);
    }
    return null;
  }

}