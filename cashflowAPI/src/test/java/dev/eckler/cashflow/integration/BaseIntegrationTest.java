package dev.eckler.cashflow.integration;

import static org.mockito.Mockito.when;

import dev.eckler.cashflow.container.PostgreSQLContainerConfig;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLContainerConfig.class)
abstract class BaseIntegrationTest {

    private static final String TEST_USER = "test-user-123";

    @Autowired
    protected MockMvc mockMvc;
    @MockitoBean
    protected JwtUtil jwtUtil;
    @Autowired
    protected CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(TEST_USER);
    }


}