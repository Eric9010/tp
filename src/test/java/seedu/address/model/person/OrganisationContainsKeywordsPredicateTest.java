package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class OrganisationContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        var firstPredicate = new OrganisationContainsKeywordsPredicate(Collections.singletonList("Google"));
        var secondPredicate = new OrganisationContainsKeywordsPredicate(Arrays.asList("Google", "Microsoft"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        var firstPredicateCopy = new OrganisationContainsKeywordsPredicate(Collections.singletonList("Google"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        OrganisationContainsKeywordsPredicate predicate =
                new OrganisationContainsKeywordsPredicate(Collections.singletonList("Google"));
        Person person = new PersonBuilder().withAddress("Google").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        OrganisationContainsKeywordsPredicate predicate =
                new OrganisationContainsKeywordsPredicate(Collections.singletonList("Microsoft"));
        Person person = new PersonBuilder().withAddress("Google").build();
        assertFalse(predicate.test(person));
    }
}
