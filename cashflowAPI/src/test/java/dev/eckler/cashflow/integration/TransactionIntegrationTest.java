package dev.eckler.cashflow.integration;

import dev.eckler.cashflow.container.CashflowContainer;
import dev.eckler.cashflow.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
@Import(CashflowContainer.class)
public class TransactionIntegrationTest {

    private static final String API_PATH = "/api/v1";

    @Autowired
    RestTestClient restTestClient;

    @MockitoBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        when(jwtUtil.readSubjectFromSecurityContext()).thenReturn("test-user-123");
    }

    @Test
    void testCategorizeTransactions() {
        String endpointPath = API_PATH + "/transaction/categorize";

    }

    @Test
    void testCreateTransactions() {
        String endpointPath = API_PATH + "/transaction/upload";
        ClassPathResource csvFile = new ClassPathResource("transactions.csv");
        restTestClient.post()
        // build the multipart body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", csvFile);
    }

    @Test
    void testError() {

    }

    @Test
    void testGetUncategorizedTransactions() {

    }

    @Test
    void testRecategorizeTransactions() {

    }
}
