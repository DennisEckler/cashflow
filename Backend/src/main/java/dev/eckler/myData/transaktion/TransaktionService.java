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

  public List<Transaktion> convertCsvToTransaktionList(InputStream fileInputStream) throws IOException {
    List<Transaktion> transaktions = new ArrayList<>();
    String line = "";
    Category category = null;
    int counter = 0;

    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {
      System.out.println(++counter + "  " + line);
      String[] columns = line.split(";");
      if (columns.length == 0 || columns.length < 8) {
        continue;
      }
      SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
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
        System.out.println(e);

      }
    }
    reader.close();

    return transaktions;
  }

  public Category categorize(String agent, String purpose) {
    Map<Category, String[]> map = new HashMap<Category, String[]>() {
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

    for (Map.Entry<Category, String[]> elementOfMap : map.entrySet()) {
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
