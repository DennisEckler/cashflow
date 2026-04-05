package dev.eckler.cashflow.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private String UNCATEGORIZED_URL = "/api/v1/transaction/uncategorized";

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(get(UNCATEGORIZED_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthorized() throws Exception {
        mockMvc.perform(get(UNCATEGORIZED_URL).with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());
    }

}
