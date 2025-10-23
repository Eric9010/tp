package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagCategoriesTest {

    @Test
    public void isIndustry_trueForWhitelisted_andCaseInsensitive() {
        assertTrue(TagCategories.isIndustry("tech"));
        assertTrue(TagCategories.isIndustry("TECH"));
        assertTrue(TagCategories.isIndustry("Finance"));
    }

    @Test
    public void isIndustry_falseForNonIndustryOrNull() {
        assertFalse(TagCategories.isIndustry("friends"));
        assertFalse(TagCategories.isIndustry("owesMoney"));
        assertFalse(TagCategories.isIndustry(null));
    }
}
