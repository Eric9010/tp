package seedu.address.ui;

import java.util.Set;

/**
 * Utility for tag industries on the UI layer
 */
public class TagCategories {
    public static final Set<String> INDUSTRY_TAGS = Set.of(
            "tech", "finance", "fintech", "accounting",
            "consulting", "healthcare", "biotech", "pharma",
            "education", "government", "public sector",
            "energy", "utilities", "manufacturing",
            "media", "advertising", "marketing",
            "real estate", "property",
            "retail", "ecommerce",
            "logistics", "supplychain",
            "travel", "hospitality", "airlines",
            "nonprofit"
    );

    private TagCategories() {}

    public static boolean isIndustry(String tagName) {
        if (tagName == null || tagName.isBlank()) {
            return false;
        }
        return INDUSTRY_TAGS.contains(tagName.toLowerCase());
    }
}
