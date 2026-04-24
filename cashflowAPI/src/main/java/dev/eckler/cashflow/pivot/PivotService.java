package dev.eckler.cashflow.pivot;

import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.openapi.model.MonthlySummaryTest;
import dev.eckler.cashflow.openapi.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PivotService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<MonthlySummary> getMonthlyPivot(String userId) {
        List<CategoryMonthRow> rows = categoryRepository.getMonthlyTotalExpensesPerCategory(userId);

        List<String> allCategories = categoryRepository.findAllByUserID(userId).stream()
            .filter(c -> c.getType() != TransactionType.IGNORE && c.getType() != TransactionType.INCOME)
            .map(Category::getLabel)
            .toList();

        return rows.stream()
            .collect(Collectors.groupingBy(
                r -> YearMonth.of(r.year(), r.month()),
                TreeMap::new,
                Collectors.toMap(CategoryMonthRow::category, CategoryMonthRow::total)
            ))
            .entrySet().stream()
            .map(e -> {
                Map<String, BigDecimal> totals = new HashMap<>(e.getValue());
                allCategories.forEach(cat -> totals.putIfAbsent(cat, BigDecimal.ZERO));

                BigDecimal totalExpenses = totals.values().stream()
                    .filter(v -> v.compareTo(BigDecimal.ZERO) < 0)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                return new MonthlySummary(e.getKey().getYear(), e.getKey().getMonthValue(), totals, totalExpenses);
            })
            .toList();
    }

    public List<MonthlySummaryTest> mapMonthlySummaryToTest(List<MonthlySummary> summaries){
        List<MonthlySummaryTest> response = new ArrayList<>();
        summaries.forEach(e ->{
            response.add(new MonthlySummaryTest(e.year(), e.month(), e.categoryTotals(), e.totalExpenses()));
        });
        return response;
    }


}
