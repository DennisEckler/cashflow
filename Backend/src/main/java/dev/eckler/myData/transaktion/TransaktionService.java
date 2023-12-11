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
      put(Category.MIETE, new String[] { "rainer klenke", "Logemann Vermoegensverwaltung" });
      put(Category.STROM, new String[] { "e.on energie", "Stromio GmbH" });
      put(Category.INTERNET, new String[] { "vodafone" });
      put(Category.HANDY, new String[] { "telefonica", "Drillisch Online GmbH" });
      put(Category.VERSICHERUNG, new String[] { "lvm landw.versicherungsverein" });
      put(Category.GEZ, new String[] { "beitragsservice von ard" });
      put(Category.ABONNEMENT,
          new String[] { "spotify ab", "igm herford", "netflix", "BoRa Sports GmbH", "FitX Deutschland GmbH" });
      put(Category.LEBENSMITTEL,
          new String[] { "wez", "LIDL", "E-CENTER", "KAUFLAND", "ALDI", "Combi Verbrauchermarkt" });
      put(Category.HAUSHALTSMITTEL, new String[] { "rossmann", "DM DROGERIEMARKT" });
      put(Category.KLEIDUNG, new String[] { "c+a", "zalando" });
      put(Category.MOBILITAET,
          new String[] { "hauptzollamt bielefeld", "unicredit", "aral ag", "jet dankt", "Deutsche Tamoil GmbH",
              "TAS Minden" });
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
    int counter = 0;
    List<Transaktion> transaktions = new ArrayList<>();
    String line = "";
    Category category = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
    reader.readLine();
    while ((line = reader.readLine()) != null) {
      System.out.println(++counter);
      String[] columns = line.split(";");
      Date date = formatDateToDatabaseFormat(columns[1]);
      float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
      String agent = columns[2];
      String bookingText = columns[3];
      String purpose = columns[4];
      if (columns[7].equals("x")) {
        category = categorize(agent, purpose);
      } else {
        category = Enum.valueOf(Category.class, columns[7].toUpperCase());
      }
      Transaktion transaktion = new Transaktion(date, agent, bookingText, purpose, amount, category);
      transaktions.add(transaktion);
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
        if (agent.trim().toLowerCase().contains(identifier.toLowerCase())
            || purpose.trim().toLowerCase().contains(identifier.toLowerCase())) {
          return entryCategory;
        }
      }
    }
    return Category.LEER;
  }

  List<Transaktion> convertCsvToTransaktionListInit(InputStream fileInputStream) throws IOException {
    List<Transaktion> transaktions = new ArrayList<>();
    String line = "";
    Category category = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {
      String[] columns = line.split(";");
      Date date = formatDateToDatabaseFormat(columns[1]);
      float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
      String agent = columns[2];
      String bookingText = columns[3];
      String purpose = columns[4];
      if (columns[7].equals("x")) {
        category = categorize(agent, purpose);
      } else {
        category = Enum.valueOf(Category.class, columns[7].toUpperCase());
      }
      Transaktion transaktion = new Transaktion(date, agent, bookingText, purpose, amount, category);
      transaktions.add(transaktion);
    }
    reader.close();
    return transaktions;
  }

  private Date formatDateToDatabaseFormat(String unformattedDate) {
    SimpleDateFormat dotsFormatter = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat slashFormatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      return new Date(dotsFormatter.parse(unformattedDate).getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    try {
      return new Date(slashFormatter.parse(unformattedDate).getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;

  }

}
