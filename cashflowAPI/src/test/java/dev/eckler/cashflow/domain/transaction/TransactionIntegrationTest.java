package dev.eckler.cashflow.domain.transaction;

import dev.eckler.cashflow.base.BaseIntegrationTest;
import dev.eckler.cashflow.common.DefaultValueSupplier;
import dev.eckler.cashflow.openapi.model.FileDescription;
import dev.eckler.cashflow.summary.MonthlySummary;
import dev.eckler.cashflow.summary.PivotService;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionIntegrationTest extends BaseIntegrationTest {

    private static final String API_PATH = "/api/v1";
    private static final String UPLOAD_PATH = API_PATH + "/transaction/upload";

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PivotService pivotService;

    @Test
    @Sql("/test-setup.sql")
    void shouldCreateCategorizedTransactionsAndMonthlySummaryHasExpectedValues() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_PATH).file(getMockMultipartFile()).file(getMultipartFileDescription())
                    .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isNoContent());

        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(990).extracting(Transaction::getIdentifier).doesNotContainNull();

        List<MonthlySummary> monthlySummary = pivotService.getMonthlyPivot(DefaultValueSupplier.TEST_USER);
        assertThat(monthlySummary).hasSize(12);
        assertMonthlySummary(
            monthlySummary,
            2025,
            1,
            Map.of(
                "Groceries", new BigDecimal("-934.15"),
                "Miete", new BigDecimal("-63.19"),
                "Insurance", BigDecimal.ZERO,
                "Strom", new BigDecimal("-91.46"),
                "Internet", new BigDecimal("-229.66"),
                "Abonnements", new BigDecimal("-183.43"),
                "Transport", new BigDecimal("-469.57"),
                "Fun", new BigDecimal("-369.05"),
                "Handy", new BigDecimal("-72.36")
            ),
            "-2412.87"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            2,
            Map.of(
                "Groceries", new BigDecimal("-557.72"),
                "Miete", new BigDecimal("-35.97"),
                "Insurance", new BigDecimal("-46.10"),
                "Strom", new BigDecimal("-81.51"),
                "Internet", new BigDecimal("-159.42"),
                "Abonnements", new BigDecimal("-460.64"),
                "Transport", new BigDecimal("-409.78"),
                "Fun", new BigDecimal("-350.69"),
                "Handy", new BigDecimal("-86.33")
            ),
            "-2188.16"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            3,
            Map.of(
                "Groceries", new BigDecimal("-745.50"),
                "Miete", new BigDecimal("-61.46"),
                "Insurance", new BigDecimal("-10.10"),
                "Strom", new BigDecimal("-148.21"),
                "Internet", new BigDecimal("-118.89"),
                "Abonnements", new BigDecimal("-266.12"),
                "Transport", new BigDecimal("-459.11"),
                "Fun", new BigDecimal("-664.40"),
                "Handy", new BigDecimal("-15.21")
            ),
            "-2489.00"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            4,
            Map.of(
                "Groceries", new BigDecimal("-860.67"),
                "Miete", new BigDecimal("-62.71"),
                "Insurance", BigDecimal.ZERO,
                "Strom", new BigDecimal("-138.32"),
                "Internet", new BigDecimal("-198.16"),
                "Abonnements", new BigDecimal("-197.37"),
                "Transport", new BigDecimal("-460.68"),
                "Fun", new BigDecimal("-510.67"),
                "Handy", new BigDecimal("-147.28")
            ),
            "-2575.86"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            5,
            Map.of(
                "Groceries", new BigDecimal("-1125.97"),
                "Miete", new BigDecimal("-11.29"),
                "Insurance", new BigDecimal("-6.12"),
                "Strom", new BigDecimal("-20.87"),
                "Internet", new BigDecimal("-68.52"),
                "Abonnements", new BigDecimal("-219.46"),
                "Transport", new BigDecimal("-675.15"),
                "Fun", new BigDecimal("-336.43"),
                "Handy", new BigDecimal("-99.37")
            ),
            "-2563.18"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            6,
            Map.of(
                "Groceries", new BigDecimal("-615.91"),
                "Miete", new BigDecimal("-216.64"),
                "Insurance", new BigDecimal("-95.16"),
                "Strom", new BigDecimal("-152.91"),
                "Internet", new BigDecimal("-102.13"),
                "Abonnements", new BigDecimal("-477.11"),
                "Transport", new BigDecimal("-688.53"),
                "Fun", new BigDecimal("-597.08"),
                "Handy", new BigDecimal("-140.72")
            ),
            "-3086.19"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            7,
            Map.of(
                "Groceries", new BigDecimal("-747.25"),
                "Miete", new BigDecimal("-106.55"),
                "Insurance", new BigDecimal("-65.87"),
                "Strom", new BigDecimal("-113.92"),
                "Internet", new BigDecimal("-54.48"),
                "Abonnements", new BigDecimal("-168.70"),
                "Transport", new BigDecimal("-194.39"),
                "Fun", new BigDecimal("-618.67"),
                "Handy", new BigDecimal("-182.34")
            ),
            "-2252.17"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            8,
            Map.of(
                "Groceries", new BigDecimal("-779.63"),
                "Miete", new BigDecimal("-170.48"),
                "Insurance", new BigDecimal("-184.17"),
                "Strom", new BigDecimal("-159.50"),
                "Internet", new BigDecimal("-276.95"),
                "Abonnements", new BigDecimal("-188.27"),
                "Transport", new BigDecimal("-395.32"),
                "Fun", new BigDecimal("-301.63"),
                "Handy", new BigDecimal("-26.11")
            ),
            "-2482.06"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            9,
            Map.of(
                "Groceries", new BigDecimal("-511.85"),
                "Miete", new BigDecimal("-64.92"),
                "Insurance", new BigDecimal("-14.07"),
                "Strom", new BigDecimal("-326.97"),
                "Internet", new BigDecimal("-144.88"),
                "Abonnements", new BigDecimal("-182.18"),
                "Transport", new BigDecimal("-408.32"),
                "Fun", new BigDecimal("-409.14"),
                "Handy", new BigDecimal("-188.57")
            ),
            "-2250.90"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            10,
            Map.of(
                "Groceries", new BigDecimal("-736.97"),
                "Miete", BigDecimal.ZERO,
                "Insurance", new BigDecimal("-28.51"),
                "Strom", new BigDecimal("-101.02"),
                "Internet", new BigDecimal("-317.85"),
                "Abonnements", new BigDecimal("-314.98"),
                "Transport", new BigDecimal("-564.21"),
                "Fun", new BigDecimal("-456.15"),
                "Handy", new BigDecimal("-295.62")
            ),
            "-2815.31"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            11,
            Map.of(
                "Groceries", new BigDecimal("-621.69"),
                "Miete", new BigDecimal("-372.43"),
                "Insurance", new BigDecimal("-7.20"),
                "Strom", new BigDecimal("-89.93"),
                "Internet", new BigDecimal("-108.32"),
                "Abonnements", new BigDecimal("-226.39"),
                "Transport", new BigDecimal("-647.10"),
                "Fun", new BigDecimal("-447.08"),
                "Handy", new BigDecimal("-161.66")
            ),
            "-2681.80"
        );

        assertMonthlySummary(
            monthlySummary,
            2025,
            12,
            Map.of(
                "Groceries", new BigDecimal("-641.52"),
                "Miete", new BigDecimal("-157.36"),
                "Insurance", new BigDecimal("-19.90"),
                "Strom", new BigDecimal("-46.96"),
                "Internet", new BigDecimal("-174.49"),
                "Abonnements", new BigDecimal("-261.26"),
                "Transport", new BigDecimal("-405.06"),
                "Fun", new BigDecimal("-229.46"),
                "Handy", new BigDecimal("-151.36")
            ),
            "-2087.37"
        );
    }

    private @NonNull MockMultipartFile getMockMultipartFile() throws IOException {
        return new MockMultipartFile(
            "file",
            "transactions.csv",
            "text/csv",
            new ClassPathResource("transactions.csv").getInputStream()
        );
    }

    private @NonNull MockMultipartFile getMultipartFileDescription() {
        return new MockMultipartFile(
            "fileDescription",
            "",
            "application/json",
            new ObjectMapper().writeValueAsBytes(getFileDescription())
        );
    }

    private @NonNull FileDescription getFileDescription() {
        FileDescription fileDescription = new FileDescription();
        fileDescription.dateIdx(1);
        fileDescription.amountIdx(5);
        fileDescription.purposeIdx(2);
        fileDescription.sourceIdx(3);
        fileDescription.year("2023");
        fileDescription.month("8");
        return fileDescription;
    }

    private void assertMonthlySummary(
        List<MonthlySummary> summaries,
        int year,
        int month,
        Map<String, BigDecimal> categoryTotals,
        String totalExpenses
    ) {
        MonthlySummary summary = summaries.stream()
            .filter(s -> s.year() == year && s.month() == month)
            .findFirst()
            .orElseThrow(() -> new AssertionError(
                "No MonthlySummary found for " + year + "-" + month
            ));

        assertThat(summary.categoryTotals())
            .containsExactlyInAnyOrderEntriesOf(categoryTotals);

        assertThat(summary.totalExpenses())
            .isEqualByComparingTo(totalExpenses);
    }

}
