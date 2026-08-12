package dev.eckler.cashflow.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    private static final String UNCATEGORIZED_PATH = "/api/v1/transaction/uncategorized";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(get(UNCATEGORIZED_PATH))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthorized() throws Exception {
        mockMvc.perform(get(UNCATEGORIZED_PATH).with(SecurityMockMvcRequestPostProcessors.jwt()))
            .andExpect(status().isOk());
    }

}
