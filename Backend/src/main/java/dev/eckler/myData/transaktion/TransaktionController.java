package dev.eckler.myData.transaktion;

import java.sql.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TransaktionController {

  private final TransaktionRepository transaktionRepository;

  public TransaktionController(TransaktionRepository transaktionRepository) {
    this.transaktionRepository = transaktionRepository;
  }

  @GetMapping("/transaktion")
  public Iterable<Transaktion> getTransaktions() {
    Date date = new Date(20231010);
    this.transaktionRepository.save(
        new Transaktion(date, "agent", "some bookin done", "the purpose", 2.23f));
    return transaktionRepository.findAll();
  }

  @PostMapping("/add")
  public Transaktion addTransaktion(@RequestBody Transaktion request) {
    return this.transaktionRepository.save(request);
  }

}
