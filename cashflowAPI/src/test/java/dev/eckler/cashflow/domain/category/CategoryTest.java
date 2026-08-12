package dev.eckler.cashflow.domain.category;

import static org.assertj.core.api.Assertions.assertThat;

import dev.eckler.cashflow.common.DefaultValueSupplier;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void testGetCategoryDefaultName() {
        String label = "Haushalt";
        Category c = new Category(label, DefaultValueSupplier.TEST_USER);
        assertThat(c.getIdentifier().iterator().next().getLabel()).isEqualTo(
            label + "_" + Category.CATEGORY_LABEL_SUFFIX);
    }
}
