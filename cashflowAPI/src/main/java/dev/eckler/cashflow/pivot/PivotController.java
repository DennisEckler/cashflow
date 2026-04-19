package dev.eckler.cashflow.pivot;

import dev.eckler.cashflow.openapi.api.ExpensesApi;
import dev.eckler.cashflow.openapi.model.MonthlySummaryTest;
import dev.eckler.cashflow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PivotController implements ExpensesApi {

    public PivotController(PivotService pivotService, JwtUtil jwtUtil) {
        this.pivotService = pivotService;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private final PivotService pivotService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<List<MonthlySummaryTest>> getExpenses() {
        List<MonthlySummary> monthlySummaries = pivotService.getMonthlyPivot(jwtUtil.readSubjectFromSecurityContext());
        return ResponseEntity.ok(pivotService.mapMonthlySummaryToTest(monthlySummaries));
    }
}
