package dev.eckler.cashflow.model.overview;

import dev.eckler.cashflow.model.transaction.TransactionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET})
public class OverviewController {

  TransactionRepository transactionRepository;
  OverviewService overviewService;

  OverviewController(TransactionRepository transactionRepository, OverviewService overviewService) {
    this.transactionRepository = transactionRepository;
    this.overviewService = overviewService;

  }

  @GetMapping("/overview")
  @PreAuthorize("hasAuthority('ROLE_developer')")
  public List<OverviewRow> getOverview(@RequestHeader("Authorization") String request) {
    List<OverviewEntry> entries = transactionRepository.getOverview();
    List<OverviewRow> summary = overviewService.createOverviewRows(entries);
    return summary;
  }

}
