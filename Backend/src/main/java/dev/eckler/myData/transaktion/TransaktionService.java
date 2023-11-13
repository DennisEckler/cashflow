package dev.eckler.myData.transaktion;

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

@Service
public class TransaktionService {

  public List<Transaktion> convertCsvToTransaktionList(InputStream fileInputStream) throws IOException {
    List<Transaktion> transaktions = new ArrayList<>();
    String line = "";

    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

    reader.readLine();
    while ((line = reader.readLine()) != null) {

      String[] columns = line.split(";");
      SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
      try {
        Date date = new Date(formatter.parse(columns[1]).getTime());
        float amount = Float.parseFloat(columns[5].replaceAll("\\.", "").replace(',', '.'));
        Transaktion transaktion = new Transaktion(date, columns[2], columns[3], columns[4], amount);
        transaktions.add(transaktion);
      } catch (ParseException e) {
        System.out.println(e);

      }
    }
    reader.close();

    return transaktions;
  }

}
