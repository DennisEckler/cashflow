package dev.eckler.myData.transaktion;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TransaktionController {

  @GetMapping("/transaktion")
  public Transaktion getTransaktion() {
    Transaktion transaktion = new Transaktion("Description", 4.32f, "20221001");
    return transaktion;
  }
}
