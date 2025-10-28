package seedu.address.logic.parser;

import java.util.Comparator;

import seedu.address.model.person.Event;
import seedu.address.model.person.Person;

public enum SortType {
    NAME, TIMESTAMP;

    public static final String UNKNOWN_TYPE = "Valid sort types are name and timestamp.";

    public Comparator<Person> getComparator() {
        switch (this) {
        case NAME:
            return Comparator.comparing(p -> p.getName().fullName.toLowerCase());
        case TIMESTAMP:
            return Comparator.comparing(Person::getDateAdded);
        default:
            //exception
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
