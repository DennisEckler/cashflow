package dev.eckler.cashflow.model.overview;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.eckler.cashflow.model.transaction.TransactionRepository;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET})
public class OverviewController {

  TransactionRepository transactionRepository;
  OverviewService overviewService;
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;

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
