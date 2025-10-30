package seedu.address.model;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Provides commonly used {@code Comparator}s for {@code Person} objects.
 */
public class Comparators {
    /**
     * Comparator for sorting pinned contacts
     * 1. Pinned status
     * 2. Pin timestamp
     */
    public static final Comparator<Person> PIN_COMPARATOR = (p1, p2) -> {
        if (p1.isPinned() && !p2.isPinned()) {
            return -1; // p1 comes before p2
        } else if (!p1.isPinned() && p2.isPinned()) {
            return 1; // p2 comes before p1
        } else if (p1.isPinned() && p2.isPinned()) {
            // Both are pinned, sort by timestamp (earlier first)
            return p1.getPinTimestamp().compareTo(p2.getPinTimestamp());
        } else {
            // Both unpinned, sort by alphabetical order
            return p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName);
        }
    };

    public static final Comparator<Person> NAME_COMPARATOR =
            Comparator.comparing(p -> p.getName().fullName.toLowerCase());

    public static final Comparator<Person> TIMESTAMP_COMPARATOR =
            Comparator.comparing(Person::getDateAdded);
}
