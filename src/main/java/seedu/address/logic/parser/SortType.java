package seedu.address.logic.parser;

import static seedu.address.model.Comparators.NAME_COMPARATOR;
import static seedu.address.model.Comparators.TIMESTAMP_COMPARATOR;

import java.util.Comparator;

import seedu.address.model.person.Person;

public enum SortType {
    NAME, TIMESTAMP;

    public static final String UNKNOWN_TYPE = "Valid sort types are name and timestamp.";

    public Comparator<Person> getComparator() {
        switch (this) {
        case NAME:
            return NAME_COMPARATOR;
        case TIMESTAMP:
            return TIMESTAMP_COMPARATOR;
        default:
            throw new IllegalStateException("Unexpected SortType: " + this);
        }
    }

    public static boolean isValidSortType(String sortType) {
        try {
            SortType.valueOf(sortType.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
