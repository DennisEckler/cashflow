package dev.eckler.cashflow.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest{

  @Autowired
  private MockMvc mockMvc;
  
 @Test
  void testUnauthorized() throws Exception {
    mockMvc.perform(get("/api/transaction/uncategorized"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void testAuthorized() throws Exception {
    mockMvc.perform(get("/api/transaction/uncategorized").with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andExpect(status().isOk());
  }

}
