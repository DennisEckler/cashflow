package dev.eckler.cashflow.model.overview;

import dev.eckler.cashflow.model.transaction.TransactionRepository;
import dev.eckler.cashflow.shared.TransactionType;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class OverviewService {

  private final TransactionRepository tr;

  public OverviewService(TransactionRepository tr) {
    this.tr = tr;
  }


  public Map<String, List<Overview>> getOverview(String userID) {
    return tr.getOverview(userID).stream()
        .map(this::createOverview)
        .filter(this::byFixedAndVariable)
        .collect(groupYearMonthNaturalOrder());
  }


  private Overview createOverview(OverviewEntry oe) {
    return new Overview(oe.getYear(), StringUtils.leftPad(oe.getMonth(), 2, '0'),
        TransactionType.valueOf(oe.getType()), oe.getAmount());
  }

  private boolean byFixedAndVariable(Overview o) {
    return o.type() == TransactionType.FIXED || o.type() == TransactionType.VARIABLE;
  }

  private static Collector<Overview, ?, Map<String, List<Overview>>> groupYearMonthNaturalOrder() {
    return Collectors.groupingBy(o -> o.year() + o.month(), TreeMap::new, Collectors.toList());
  }

}

