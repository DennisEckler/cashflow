import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.text.NumberFormatter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RandomCsvGenerator {

  public static void main(String[] args) {
    int numrows = 1000;

    String outputfilepath = "../Backend/src/main/resources/fakeData.csv";
    File fileToDelete = new File(outputfilepath);
    fileToDelete.delete();

    try (FileWriter writer = new FileWriter(outputfilepath)) {
      writer.append("buchung;valuta;auftraggeber/empfaenger;buchungstext;verwendungszweck;betrag;;\n");

      for (int i = 1; i <= numrows; i++) {
        String identifier = randompurpose();
        writer.append(generaterandomdate() + ";");
        writer.append(generaterandomdate() + ";");
        writer.append(randomagent() + ";");
        writer.append(randombookingtext() + ";");
        writer.append(identifier + ";");
        writer.append(randomAmount(identifier) + ";x;x\n");
      }

      System.out.println("random data written to '" + outputfilepath + "'.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static final String[] IDENTIFIER = {
      "bertelsmann", "abas", "neschen", "mait", "arvato",
      "kammann",
      "rainer klenke", "Logemann Vermoegensverwaltung",
      "e.on energie", "Stromio GmbH",
      "vodafone",
      "telefonica", "Drillisch Online GmbH",
      "lvm landw.versicherungsverein",
      "beitragsservice von ard",
      "spotify ab", "igm herford", "netflix", "BoRa Sports GmbH", "FitX Deutschland GmbH",
      "wez", "LIDL", "E-CENTER", "KAUFLAND", "ALDI", "Combi Verbrauchermarkt",
      "rossmann", "DM DROGERIEMARKT",
      "c+a", "zalando",
      "hauptzollamt bielefeld", "unicredit", "aral ag", "jet dankt", "Deutsche Tamoil GmbH",
      "TAS Minden",
      "landbaeckerei niemeyer",
      "elsner catering", "ing", "xxx", "yyy", "pppp"
  };

  private static String generaterandomdate() {
    LocalDate startdate = LocalDate.of(2020, 1, 1);
    LocalDate enddate = LocalDate.of(2023, 12, 31);
    long days = ChronoUnit.DAYS.between(startdate, enddate);
    int randomdifference = new Random().nextInt((int) days + 1);
    return startdate.plusDays(randomdifference).toString();
  }

  private static String randompurpose() {
    return IDENTIFIER[new Random().nextInt(IDENTIFIER.length)];
  }

  private static String randomparticipant() {
    String[] purposes = { "business", "leisure", "other" };
    return purposes[new Random().nextInt(purposes.length)];
  }

  private static String randombookingtext() {
    String[] purposes = { "bookingtext", "gutschrift" };
    return purposes[new Random().nextInt(purposes.length)];
  }

  private static String randomagent() {
    String[] purposes = { "xxx", "other" };
    return purposes[new Random().nextInt(purposes.length)];
  }

  private static String randomAmount(String identifier) {
    DecimalFormat formatter = new DecimalFormat("#.##");
    Random random = new Random();
    Double amount = random.nextDouble() * 1000;
    if (identifier == "bertelsmann" || identifier == "abas" || identifier == "neschen" || identifier == "mait"
        || identifier == "arvato" || identifier == "kammann") {
      return String.valueOf(formatter.format(amount));
    } else {
      return String.valueOf(formatter.format(amount * -1));
    }
  }

}
