package dev.eckler.myData.transaktion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransaktionController {

  private final TransaktionRepository transaktionRepository;
  private final TransaktionService transaktionService;

  public TransaktionController(TransaktionRepository transaktionRepository, TransaktionService transaktionService) {
    this.transaktionRepository = transaktionRepository;
    this.transaktionService = transaktionService;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/update-list")
  public Transaktion getTransaktion() {
    Date date = new Date(20231010);
    Transaktion transaktion = new Transaktion(date, "agent", "bookingText", "the purpose", 21.3f);
    return transaktion;
  }

  @PostMapping("/add")
  public Transaktion addTransaktion(@RequestBody Transaktion request) {
    return this.transaktionRepository.save(request);
  }

  @PostMapping("/file-upload")
  public void uploadFile(@RequestParam("file") MultipartFile csvFile) throws IOException {
    InputStream stream = csvFile.getInputStream();
    List<Transaktion> transaktions = new ArrayList<>();
    transaktions.addAll(this.transaktionService.convertCsvToTransaktionList(stream));
    transaktions.forEach((ele) -> {
      System.out.println(ele.toString());
    });

  }

}
