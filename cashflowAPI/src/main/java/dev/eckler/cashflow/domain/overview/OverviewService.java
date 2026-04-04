package dev.eckler.cashflow.domain.overview;

import dev.eckler.cashflow.domain.transaction.TransactionRepository;
import dev.eckler.cashflow.openapi.model.OverviewSummaryResponse;
import dev.eckler.cashflow.openapi.model.TransactionType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OverviewService {

    private final TransactionRepository tr;

    public OverviewService(TransactionRepository tr) {
        this.tr = tr;
    }

    public ResponseEntity<List<OverviewSummaryResponse>> getOverview(String userID) {
        Map<String, List<Overview>> list = tr.getOverview(userID).stream()
                .map(this::createOverview)
                .collect(groupYearMonthNaturalOrder());
        return ResponseEntity.ok(createOverviewRows(list));
    }

    private List<OverviewSummaryResponse> createOverviewRows(Map<String, List<Overview>> mapOfOverview) {
        List<OverviewSummaryResponse> rows = new ArrayList<>();
        mapOfOverview.forEach((period, list) -> {
            OverviewSummary row = new OverviewSummary();
            row.setMonth(list.get(0).month());
            row.setYear(list.get(0).year());
            list.forEach(row::accumulateAmount);
            rows.add(row);
        });
        return rows;
    }

    private Overview createOverview(OverviewEntry oe) {
        return new Overview(oe.getYear(), StringUtils.leftPad(oe.getMonth(), 2, '0'),
                TransactionType.valueOf(oe.getType()), oe.getAmount());
    }

    private static Collector<Overview, ?, Map<String, List<Overview>>> groupYearMonthNaturalOrder() {
        return Collectors.groupingBy(o -> o.year() + o.month(), TreeMap::new, Collectors.toList());
    }



}
