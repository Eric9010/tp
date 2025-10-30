package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.OrganisationContainsKeywordsPredicate;

public class FindOrganisationCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        OrganisationContainsKeywordsPredicate firstPredicate =
                new OrganisationContainsKeywordsPredicate(Collections.singletonList("first"));
        OrganisationContainsKeywordsPredicate secondPredicate =
                new OrganisationContainsKeywordsPredicate(Collections.singletonList("second"));

        FindOrganisationCommand findFirstCommand = new FindOrganisationCommand(firstPredicate);
        FindOrganisationCommand findSecondCommand = new FindOrganisationCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindOrganisationCommand findFirstCommandCopy = new FindOrganisationCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        OrganisationContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindOrganisationCommand command = new FindOrganisationCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Change keywords to ones that match your sample data’s addresses (organisations)
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        OrganisationContainsKeywordsPredicate predicate = preparePredicate("Jurong Clementi wall");
        FindOrganisationCommand command = new FindOrganisationCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Adjust this list depending on which TypicalPersons have those addresses
        assertEquals(Arrays.asList(ALICE, BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        OrganisationContainsKeywordsPredicate predicate =
                new OrganisationContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindOrganisationCommand findOrgCommand = new FindOrganisationCommand(predicate);
        String expected = FindOrganisationCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findOrgCommand.toString());
    }

    /**
     * Parses {@code userInput} into an {@code OrganisationContainsKeywordsPredicate}.
     */
    private OrganisationContainsKeywordsPredicate preparePredicate(String userInput) {
        return new OrganisationContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
