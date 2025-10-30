package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortTypeTest {

    @Test
    public void isValidSortType() {
        //null
        assertThrows(NullPointerException.class, () -> SortType.isValidSortType(null));

        //empty spaces
        assertFalse(SortType.isValidSortType(""));
        assertFalse(SortType.isValidSortType(" "));

        //invalid
        assertFalse(SortType.isValidSortType("time")); //substring
        assertFalse(SortType.isValidSortType("named")); // superstring
        assertFalse(SortType.isValidSortType("123"));
        assertFalse(SortType.isValidSortType("!?}"));

        //valid
        assertTrue(SortType.isValidSortType("name"));
        assertTrue(SortType.isValidSortType("timestamp"));
        assertTrue(SortType.isValidSortType("NaMe"));
    }
}
