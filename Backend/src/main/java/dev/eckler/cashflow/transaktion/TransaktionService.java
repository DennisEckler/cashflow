package dev.eckler.cashflow.transaktion;

import dev.eckler.cashflow.category.Category;
import dev.eckler.cashflow.category.CategoryRepository;
import dev.eckler.cashflow.identifier.Identifier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TransaktionService {

  private TransaktionRepository transaktionRepository;
  private CategoryRepository categoryRepository;

  private TransaktionService(TransaktionRepository transaktionRepository,
      CategoryRepository categoryRepository) {
    this.transaktionRepository = transaktionRepository;
    this.categoryRepository = categoryRepository;
  }

  List<Transaktion> convertCsvToTransaktionList(InputStream fileInputStream, final String USERID)
      throws IOException {
    boolean monthCheckCalled = false;
    List<Transaktion> transaktions = new ArrayList<>();
    Iterable<Category> categories = categoryRepository.findAllByUserID(USERID);
    String line = "";
    Identifier identifier = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {
      String[] columns = line.split(";");
      Date date = parseDate(columns[1]);
      float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
      String source = columns[2];
      String purpose = columns[4];
      identifier = categorize(categories, source, purpose);

      Transaktion transaktion = new Transaktion(date, amount, USERID, purpose, source, identifier);
      transaktions.add(transaktion);
    }
    reader.close();
    return transaktions;
  }

  private boolean skipIfPeriodAlreadyExist(String[] columns) {
    String year = columns[1].substring(6, 10);
    String month = columns[1].substring(3, 5);
    if (this.transaktionRepository.getNumberOfYearMonthMatches(year, month) > 0) {
      return true;
    }
    return false;
  }

  private Identifier categorize(Iterable<Category> categories, String source, String purpose) {
    for (Category category : categories) {
      for (Identifier identifier : category.getIdentifier()) {
        if (source.trim().toLowerCase().contains(identifier.getLabel().toLowerCase())
            || purpose.trim().toLowerCase().contains(identifier.getLabel().toLowerCase())) {
          return identifier;
        }
      }
    }
    return null;
  }

  private static Date parseDate(String date) {
    List<String> formats = Arrays.asList("dd.MM.yyyy", "dd/MM/yyyy", "yyyy-MM-dd");
    for (String format : formats) {
      try {
        return new Date(new SimpleDateFormat(format).parse(date).getTime());
      } catch (ParseException e) {
      }
    }
    return null;
  }

  public List<Transaktion> convertCsvToTransaktionListInit(InputStream fileInputStream,
      final String USERID)
      throws IOException {
    List<Transaktion> transaktions = new ArrayList<>();
    Iterable<Category> categories = categoryRepository.findAllByUserID(USERID);
    String line = "";
    Identifier identifier = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {
      String[] columns = line.split(";");
      Date date = parseDate(columns[1]);
      float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
      String source = columns[2];
      String purpose = columns[4];
      if (columns[7].equals("x")) {
        identifier = categorize(categories, source, purpose);
      } else {
        identifier = null;
      }
      Transaktion transaktion = new Transaktion(date, amount, USERID, purpose, source, identifier);
      transaktions.add(transaktion);
    }
    reader.close();
    return transaktions;
  }

}
