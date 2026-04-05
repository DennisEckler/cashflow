package dev.eckler.cashflow.domain.overview;

import dev.eckler.cashflow.openapi.api.OverviewApi;
import dev.eckler.cashflow.openapi.model.OverviewSummaryResponse;
import dev.eckler.cashflow.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OverviewController implements OverviewApi {

    private final OverviewService overviewService;
    private final JwtUtil jwtUtil;

    OverviewController(OverviewService overviewService, JwtUtil jwtUtil) {
        this.overviewService = overviewService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<List<OverviewSummaryResponse>> getOverview() {
        String userID = jwtUtil.readSubjectFromSecurityContext();
        return overviewService.getOverview(userID);
    }
}
