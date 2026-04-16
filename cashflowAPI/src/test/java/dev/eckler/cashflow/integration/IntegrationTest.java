package dev.eckler.cashflow.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.eckler.cashflow.domain.transaction.Transaction;
import dev.eckler.cashflow.domain.transaction.TransactionRepository;
import dev.eckler.cashflow.openapi.model.FileDescription;
import java.io.IOException;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import tools.jackson.databind.ObjectMapper;

class IntegrationTest extends BaseIntegrationTest {

    private static final String API_PATH = "/api/v1";
    private static final String UPLOAD_PATH = API_PATH + "/transaction/upload";

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testCreateTransactions() throws Exception {
        mockMvc.perform(
                multipart(UPLOAD_PATH).file(getMockMultipartFile()).file(getMultipartFileDescription())
                    .with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isNoContent());
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions.size()).isEqualTo(990);
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

}
