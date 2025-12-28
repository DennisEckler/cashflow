package dev.eckler.cashflow.domain.overview;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/api/overview")
public class OverviewController {

    OverviewService overviewService;

    OverviewController(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping
    public List<OverviewSummary> getOverview(@AuthenticationPrincipal Jwt jwt) {
        String userID = jwt.getSubject();
        return overviewService.getOverview(userID);
    }

}
