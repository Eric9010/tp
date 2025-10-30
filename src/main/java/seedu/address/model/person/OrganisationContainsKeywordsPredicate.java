package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s organisation field contains any of the given keywords.
 */
public class OrganisationContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public OrganisationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrganisationContainsKeywordsPredicate
                && keywords.equals(((OrganisationContainsKeywordsPredicate) other).keywords));
    }
}
