package dev.eckler.myData.transaktion;

import java.io.File;
import java.sql.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransaktionController {

  private final TransaktionRepository transaktionRepository;

  public TransaktionController(TransaktionRepository transaktionRepository) {
    this.transaktionRepository = transaktionRepository;
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
  public void uploadFile(@RequestBody File csvFile) {
    System.out.println(csvFile.getName());

  }

}
