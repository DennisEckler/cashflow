package dev.eckler.cashflow.domain.category;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import dev.eckler.cashflow.util.DefaultLabelBuilder;

public class CategoryTest {
    @Test
    void testGetCategoryDefaultName() {
        Category c = new Category();
        String result = Arrays.asList(DefaultLabelBuilder.createLabelFor(c).split("\\.")).getLast();
        assertTrue(StringUtils.equals("Category_default", result));
    }
}
