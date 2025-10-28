package seedu.address.logic.parser;

import java.util.Comparator;

import seedu.address.model.person.Person;

public enum SortType {
    NAME, TIMESTAMP;

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
}
