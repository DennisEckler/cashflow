package dev.eckler.cashflow.domain.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import dev.eckler.cashflow.container.PostgreSQLContainerConfig;
import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.openapi.model.FileDescription;
import dev.eckler.cashflow.pivot.MonthlySummary;
import dev.eckler.cashflow.pivot.PivotService;
import java.io.IOException;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Import(PostgreSQLContainerConfig.class)
class TransactionServiceTest {

    public static final String USER_ID = "gojo";

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PivotService pivotService;


    @Test
    @Sql("/test-setup.sql")
    void createTransactions() throws IOException {
        transactionService.createTransactions(getMockMultipartFile(), getFileDescription(),
            USER_ID);
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions).hasSize(990);
        assertThat(transactions.stream().filter(t -> t.getIdentifier() == null).toList()).hasSize(
            0);

        List<Category> categories = categoryRepository.findAll();
        List<MonthlySummary> pivot = pivotService.getMonthlyPivot(USER_ID);
        System.out.println(categories);
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

    private @NonNull MockMultipartFile getMockMultipartFile() throws IOException {
        return new MockMultipartFile(
            "file",
            "transactions.csv",
            "text/csv",
            new ClassPathResource("transactions.csv").getInputStream()
        );
    }

}
