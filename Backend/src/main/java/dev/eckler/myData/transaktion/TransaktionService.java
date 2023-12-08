package dev.eckler.myData.transaktion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import org.springframework.stereotype.Service;

import dev.eckler.myData.shared.Category;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TransaktionService {

  private static final Map<Category, String[]> IDENTIFIER = new HashMap<Category, String[]>() {
    {
      put(Category.DENNIS, new String[] { "bertelsmann", "abas", "neschen", "mait", "arvato" });
      put(Category.SVETI, new String[] { "kammann" });
      put(Category.MIETE, new String[] { "rainer klenke" });
      put(Category.STROM, new String[] { "e.on energie" });
      put(Category.INTERNET, new String[] { "vodafone" });
      put(Category.HANDY, new String[] { "telefonica" });
      put(Category.VERSICHERUNG, new String[] { "lvm landw.versicherungsverein" });
      put(Category.GEZ, new String[] { "beitragsservice von ard" });
      put(Category.ABONNEMENT, new String[] { "spotify ab", "igm herford", "netflix" });
      put(Category.LEBENSMITTEL, new String[] { "wez" });
      put(Category.HAUSHALTSMITTEL, new String[] { "rossmann" });
      put(Category.KLEIDUNG, new String[] { "c+a", "zalando" });
      put(Category.MOBILITAET, new String[] { "hauptzollamt bielefeld", "unicredit", "aral ag", "jet dankt" });
      put(Category.GESCHENKE, new String[] {});
      put(Category.AUSGEHEN, new String[] { "landbaeckerei niemeyer" });
      put(Category.SONSTIGES, new String[] { "elsner catering", "ing" });

    }
  };

  private TransaktionRepository transaktionRepository;

  private TransaktionService(TransaktionRepository transaktionRepository) {
    this.transaktionRepository = transaktionRepository;
  }

  List<Transaktion> convertCsvToTransaktionList(InputStream fileInputStream) throws IOException {
    boolean monthCheckCalled = false;
    List<Transaktion> transaktions = new ArrayList<>();
    String line = "";
    Category category = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    reader.readLine();
    while ((line = reader.readLine()) != null) {

      String[] columns = line.split(";");

      if (columns.length == 0 || columns.length < 8) {
        continue;
      }
      if (!monthCheckCalled) {
        if (skipIfMonthYearAlreadyExist(columns)) {
          return transaktions;
        }
        monthCheckCalled = true;
      }

      try {
        Date date = new Date(formatter.parse(columns[1]).getTime());
        float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
        String agent = columns[2];
        String bookingText = columns[3];
        String purpose = columns[4];
        if (columns[7].trim() == "") {
          category = categorize(agent, purpose);
        } else {
          category = Enum.valueOf(Category.class, columns[7].toUpperCase());
        }

        Transaktion transaktion = new Transaktion(date, agent, bookingText, purpose, amount, category);
        transaktions.add(transaktion);

      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    reader.close();
    return transaktions;
  }

  private boolean skipIfMonthYearAlreadyExist(String[] columns) {
    String year = columns[1].substring(6, 10);
    String month = columns[1].substring(3, 5);
    if (this.transaktionRepository.getNumberOfYearMonthMatches(year, month) > 0) {
      return true;
    }
    return false;
  }

  private Category categorize(String agent, String purpose) {
    for (Map.Entry<Category, String[]> elementOfMap : IDENTIFIER.entrySet()) {
      Category entryCategory = elementOfMap.getKey();
      String[] values = elementOfMap.getValue();

      for (String identifier : values) {
        if (agent.toLowerCase().contains(identifier) || purpose.toLowerCase().contains(identifier)) {
          return entryCategory;
        }
      }
    }
    ;
    return Category.LEER;
  }

}
