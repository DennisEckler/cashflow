package dev.eckler.myData.overview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.eckler.myData.transaktion.TransaktionRepository;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = { RequestMethod.GET })
public class OverviewController {
  TransaktionRepository transaktionRepository;
  OverviewService overviewService;

  OverviewController(TransaktionRepository transaktionRepository, OverviewService overviewService) {
    this.transaktionRepository = transaktionRepository;
    this.overviewService = overviewService;

  }

  @GetMapping("/overview")
  @PreAuthorize("hasAuthority('ROLE_developer')")
  public List<OverviewRow> getOverview() {
    List<OverviewEntry> entries = transaktionRepository.getOverview();
    List<OverviewRow> summary = overviewService.createOverviewRows(entries);
    return summary;
  }

}
