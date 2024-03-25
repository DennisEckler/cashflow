package dev.eckler.cashflow.model.overview;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import dev.eckler.cashflow.config.Oauth2Properties;
import dev.eckler.cashflow.model.transaction.TransactionRepository;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/overview")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET})
public class OverviewController {

  OverviewService overviewService;
  Oauth2Properties oauthProperties;

  OverviewController(Oauth2Properties oauthProperties, OverviewService overviewService) {
    this.overviewService = overviewService;
    this.oauthProperties = oauthProperties;
  }

  @GetMapping("/")
  @PreAuthorize("hasAuthority('ROLE_developer')")
  public Map<String, List<Overview>> getOverview(@RequestHeader("Authorization") String request) {
    String userID = getUserId(request, oauthProperties.issuerUri());
    return overviewService.getOverview(userID);
  }

}
