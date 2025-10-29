package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.SortType.isValidSortType;

import org.junit.jupiter.api.Test;

public class SortTypeTest {

    @Test
    public void isValidSortType_validSortType_true() {
        assertTrue(isValidSortType("name"));
        assertTrue(isValidSortType("timestamp"));
        assertTrue(isValidSortType("NaMe"));
    }

    @Test
    public void isValidSortType_invalidSortType_false() {
        assertFalse(isValidSortType("email"));
    }
}
