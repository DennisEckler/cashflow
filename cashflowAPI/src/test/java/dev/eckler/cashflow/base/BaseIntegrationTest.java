package dev.eckler.cashflow.base;

import dev.eckler.cashflow.auth.TestJwtTokenFactory;
import dev.eckler.cashflow.common.DefaultValueSupplier;
import dev.eckler.cashflow.testcontainer.PostgreSQLContainerConfig;
import dev.eckler.cashflow.util.JwtUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(PostgreSQLContainerConfig.class)
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @MockitoBean
    private JwtDecoder jwtDecoder;
    @MockitoBean
    JwtUtil jwtUtil;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port + "/api/v1/";
        when(jwtDecoder.decode(anyString()))
            .thenReturn(TestJwtTokenFactory.forUsername(DefaultValueSupplier.TEST_USER));
        when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(DefaultValueSupplier.TEST_USER);
    }

}