package dev.eckler.cashflow.model.transaction;

import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.category.CategoryRepository;
import dev.eckler.cashflow.model.identifier.Identifier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;

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

  List<Transaction> convertCsvToTransaktionList(InputStream fileInputStream, final String USERID)
      throws IOException {
    boolean monthCheckCalled = false;
    List<Transaction> transactions = new ArrayList<>();
    Iterable<Category> categories = categoryRepository.findAllByUserID(USERID);
    String line;
    Identifier identifier = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {
      String[] columns = line.split(";");
      Date date = parseDate(columns[1]);
      float amount = Float.parseFloat(columns[5].replace("\\.", "").replace(',', '.'));
      String source = columns[2];
      String purpose = columns[4];
      identifier = categorize(categories, source, purpose);

      Transaction transaction = new Transaction(date, amount, USERID, purpose, source, identifier);
      transactions.add(transaction);
    }
    reader.close();
    return transactions;
  }

  private boolean skipIfPeriodAlreadyExist(String[] columns) {
    String year = columns[1].substring(6, 10);
    String month = columns[1].substring(3, 5);
    return this.transactionRepository.getNumberOfYearMonthMatches(year, month) > 0;
  }

  private Identifier categorize(Iterable<Category> categories, String source, String purpose) {
    for (Category category : categories) {
      for (Identifier identifier : category.getIdentifier()) {
        if (source.trim().toLowerCase().contains(identifier.getIdentifierLabel().toLowerCase())
            || purpose.trim().toLowerCase()
            .contains(identifier.getIdentifierLabel().toLowerCase())) {
          return identifier;
        }
      }
    }
    return null;
  }

  private Date parseDate(String date) {
    List<String> formats = Arrays.asList("dd.MM.yyyy", "dd/MM/yyyy", "yyyy-MM-dd");
    for (String format : formats) {
      try {
        return new Date(new SimpleDateFormat(format).parse(date).getTime());
      } catch (ParseException e) {
        logger.error("Format {} not Supported for this date: {}", format, date);
      }
    }
    return null;
  }

}
