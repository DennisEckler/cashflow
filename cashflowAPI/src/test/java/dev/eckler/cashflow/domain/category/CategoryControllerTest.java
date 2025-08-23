package dev.eckler.cashflow.domain.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.model.CashflowValidationErrorResponse;
import dev.eckler.cashflow.openapi.model.CashflowValidationErrorResponseSubErrorInner;
import dev.eckler.cashflow.openapi.model.CategoryCreateRequest;
import dev.eckler.cashflow.openapi.model.CategoryResponse;
import dev.eckler.cashflow.openapi.model.CategoryUpdateRequest;
import dev.eckler.cashflow.openapi.model.IdentifierResponse;
import dev.eckler.cashflow.openapi.model.TransactionType;
import dev.eckler.cashflow.shared.CashflowConst;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    CategoryService cs;
    @MockitoBean
    JwtUtil jwtUtil;
    static ObjectMapper objectMapper;

    private String USER_ID = "gojo";
    private String PATH = "/v1/api/category";

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCategories_whenNoCategeriesFound_shouldThrowErrorWithNotFoundStatus() throws Exception {
        Mockito.when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(USER_ID);
        Mockito.when(cs.getCategoriesByUser(USER_ID))
                .thenThrow(new CategoryNotFoundException(USER_ID));
        mockMvc.perform(get(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("The user: " + USER_ID + " dont have this category",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void getCategories_whenCategoriesArePresent_returnListOfCategories() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        List<CategoryResponse> categories = new ArrayList<>();
        categories.add(response);
        Mockito.when(jwtUtil.readSubjectFromSecurityContext()).thenReturn(USER_ID);
        Mockito.when(cs.getCategoriesByUser(USER_ID)).thenReturn(categories);

        mockMvc.perform(get(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void addCategory_whenInvalidObject_throwNotValidException() throws Exception {
        CategoryCreateRequest invalidRequest = new CategoryCreateRequest();
        String json = objectMapper.writeValueAsString(invalidRequest);
        mockMvc.perform(post(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(ex -> {
                    CashflowValidationErrorResponse actualError = objectMapper.readValue(
                            ex.getResponse().getContentAsString(), CashflowValidationErrorResponse.class);
                    assertThat(actualError)
                            .usingRecursiveComparison()
                            .ignoringFields("datetime").isEqualTo(getExpectedError());
                });
    }

    @Test
    void addCategory_whenValidObject_saveAndReturnCategoryResponse() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest("Mobility");
        String json = objectMapper.writeValueAsString(request);
        Mockito.when(cs.createCategory(request)).thenReturn(mockedCreateResponse());
        mockMvc.perform(post(PATH)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void deleteCategory_whenIDNotExist_throwCategoryNotFound() throws Exception {
        Mockito.doThrow(new CategoryNotFoundException(USER_ID))
                .when(cs).deleteCategory(anyLong(), any());
        mockMvc.perform(delete(PATH + "/{id}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void deleteCategory_whenIDExist_shouldDeleteAndReturnNoContent() throws Exception {
        Mockito.doNothing().when(cs).deleteCategory(anyLong(), any());
        mockMvc.perform(delete(PATH + "/{id}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void updateCategory_whenInvalidRequest_shouldThrowBadRequestWithDetail() throws Exception {
        CategoryUpdateRequest cur = new CategoryUpdateRequest();
        String curJson = objectMapper.writeValueAsString(cur);
        mockMvc.perform(put(PATH + "/{id}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(curJson))
                .andExpect(jsonPath("$..field", containsInAnyOrder("label", "type", "id", "userID")));

    }

    @Test
    void updateCategory_whenValidRequest_shouldUpdateAndReturnCategory() throws Exception {
        CategoryUpdateRequest cur = validUpdateRequest();
        String curJson = objectMapper.writeValueAsString(cur);
        Mockito.when(cs.changeType(cur, null))
                .thenReturn(mockedCreateResponse());
        mockMvc.perform(put(PATH + "/{id}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(curJson))
                .andExpect(status().isOk());
    }

    private CashflowValidationErrorResponse getExpectedError() {
        CashflowValidationErrorResponse error = new CashflowValidationErrorResponse();
        error.setStatusCode(400);
        error.setMessage("Validation failed");

        CashflowValidationErrorResponseSubErrorInner subError = new CashflowValidationErrorResponseSubErrorInner();
        subError.setField("label");
        subError.setFieldError("must not be null | for this rejected value: null");

        ArrayList<CashflowValidationErrorResponseSubErrorInner> subErrorList = new ArrayList<>();
        subErrorList.add(subError);

        error.setSubError(subErrorList);
        return error;
    }

    private CategoryResponse mockedCreateResponse() {
        CategoryResponse response = new CategoryResponse();
        response.setId(123L);
        response.setUserID("gojo");
        response.setLabel("Mobility");
        response.setType(TransactionType.FIXED);

        IdentifierResponse identifierResponse = new IdentifierResponse();
        identifierResponse.setId(1L);
        identifierResponse.setLabel(CashflowConst.DEFAULT);
        List<IdentifierResponse> identifierList = new ArrayList<>();

        identifierList.add(identifierResponse);

        response.setIdentifier(identifierList);
        return response;

    }

    private CategoryUpdateRequest validUpdateRequest() {
        CategoryUpdateRequest updateRequest = new CategoryUpdateRequest();
        updateRequest.setId(123L);
        updateRequest.setUserID("gojo");
        updateRequest.setLabel("Mobility");
        updateRequest.setType(TransactionType.FIXED);

        IdentifierResponse identifierResponse = new IdentifierResponse();
        identifierResponse.setId(1L);
        identifierResponse.setLabel(CashflowConst.DEFAULT);
        List<IdentifierResponse> identifierList = new ArrayList<>();

        identifierList.add(identifierResponse);

        updateRequest.setIdentifier(identifierList);

        return updateRequest;
    }

}
