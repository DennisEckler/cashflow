package dev.eckler.cashflow.domain.overview;

import static dev.eckler.cashflow.shared.CashflowConst.USER_ID;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/overview")
public class OverviewController {

  OverviewService overviewService;

  OverviewController(OverviewService overviewService) {
    this.overviewService = overviewService;
  }

  @GetMapping("/")
  public List<OverviewSummary> getOverview() {
    return overviewService.getOverview(USER_ID);
  }

}
