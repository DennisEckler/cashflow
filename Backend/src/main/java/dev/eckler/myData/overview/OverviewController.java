package dev.eckler.myData.overview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import dev.eckler.myData.transaktion.TransaktionRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OverviewController {
  TransaktionRepository transaktionRepository;
  OverviewService overviewService;

  OverviewController(TransaktionRepository transaktionRepository, OverviewService overviewService) {
    this.transaktionRepository = transaktionRepository;
    this.overviewService = overviewService;

  }

  @GetMapping("/overview")
  public void getOverview() {
    this.overviewService.createOverviewRows(this.transaktionRepository.getOverview());
  }

}
