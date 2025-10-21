package seedu.address.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
