package dev.eckler.cashflow.domain.category;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  CategoryService cs;

  @MockitoBean
  JwtUtil jwtUtil;

  private String USER_ID = "gojo";
  private String PATH = "/v1/api/category";

  @Test
  void testUnauthorized() throws Exception {
    mockMvc.perform(get(PATH))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCategories_whenNoCategeriesFound_shouldThrowErrorWithNoContentStatus() throws Exception {
    Mockito.when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(USER_ID);
    Mockito.when(cs.getCategoriesByUser(USER_ID))
        .thenThrow(new CategoryNotFoundException(USER_ID));
    mockMvc.perform(get(PATH)
        .with(SecurityMockMvcRequestPostProcessors.jwt()))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andExpect(result -> 
            assertEquals("The user: " + USER_ID + " dont have this category", result.getResolvedException().getMessage()));
  }

  @Test
  void getCategories_whenCategoriesArePresent_returnListOfCategories() throws Exception{

    CategoryResponse response = new CategoryResponse();
    response.setId(1L);
    List<CategoryResponse> categories = new ArrayList<>();
    categories.add(response);
    Mockito.when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(USER_ID);
    Mockito.when(cs.getCategoriesByUser(USER_ID)).thenReturn(categories);

    mockMvc.perform(get(PATH)
        .with(SecurityMockMvcRequestPostProcessors.jwt()))
      .andDo(print())
      .andExpect(jsonPath("$.size()", is(1)));
  }

  @Test
  void addCategory_whenInvalidObject_throwNotValidException() throws Exception{
    CategoryCreateRequest invalidRequest = new CategoryCreateRequest();
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(invalidRequest);
    mockMvc.perform(post(PATH)
        .with(SecurityMockMvcRequestPostProcessors.jwt())
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().is(400))
      .andExpect(result -> {
        System.out.println(StringUtils.join("Result: ", result.getResolvedException()));
        result.getResolvedException().getMessage();
        });
  }

}
