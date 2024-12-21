package dev.eckler.cashflow.domain.transaction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import dev.eckler.cashflow.domain.identifier.IdentifierService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransactionRepository repository;

  @MockBean
  private TransactionService service;

  @MockBean
  private IdentifierService identifierService;

  @Test
  void testUnauthorized() throws Exception {
    Mockito.when(repository.findAllByIdentifierIsNull()).thenReturn(List.of(new Transaction()));
    mockMvc.perform(get("/api/transaction/uncategorized")).andDo(print()).andExpect(status().isUnauthorized());
  }

  @Test
  void testAuthorized() throws Exception {
    Mockito.when(repository.findAllByIdentifierIsNull()).thenReturn(List.of(new Transaction()));
    mockMvc.perform(get("/api/transaction/uncategorized").with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andDo(print()).andExpect(status().isOk());
  }
}
