package dev.eckler.cashflow.domain.transaction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.eckler.cashflow.domain.identifier.IdentifierService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TransactionRepository repository;

  @MockitoBean
  private TransactionService service;

  @MockitoBean
  private IdentifierService identifierService;

  @Test
  void testUnauthorized() throws Exception {
    Mockito.when(repository.findAllByIdentifierIsNullAndUserID("test")).thenReturn(List.of(new Transaction()));
    mockMvc.perform(get("/api/transaction/uncategorized"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void testAuthorized() throws Exception {
    Mockito.when(repository.findAllByIdentifierIsNullAndUserID("test")).thenReturn(List.of(new Transaction()));
    mockMvc.perform(get("/api/transaction/uncategorized")
        .with(SecurityMockMvcRequestPostProcessors.jwt()))
      .andExpect(status().isOk());
  }
}
