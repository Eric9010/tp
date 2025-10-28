package seedu.address.logic.parser;

import static seedu.address.model.Comparators.NAME_COMPARATOR;
import static seedu.address.model.Comparators.TIMESTAMP_COMPARATOR;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Represents the type of sorting that can be applied to a list of {@link Person} objects.
 * Supports sorting by NAME or TIMESTAMP. Pinned persons should be handled separately
 * in a combined comparator to appear first in the list.
 */
public enum SortType {
    NAME, TIMESTAMP;

    public static final String UNKNOWN_TYPE = "Valid sort types are name and timestamp.";

    /**
     * Returns the comparator corresponding to this sort type.
     *
     * @return a {@code Comparator} for {@code Person} objects
     * @throws IllegalStateException if the enum value is unexpected (should never happen)
     */
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

    /**
     * Checks if the given string represents a valid sort type.
     *
     * @param sortType the string to check
     * @return true if the string corresponds to a valid {@code SortType}, false otherwise
     */
    public static boolean isValidSortType(String sortType) {
        try {
            SortType.valueOf(sortType.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
