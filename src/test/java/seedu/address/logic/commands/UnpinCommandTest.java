package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnpinCommand}.
 */
public class UnpinCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Reset model and pin the first person to enable each test.
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        if (!firstPerson.isPinned()) {
            Person pinnedFirstPerson = firstPerson.pin();
            model.setPerson(firstPerson, pinnedFirstPerson);
            model.updatePersonListSort();
        }
    }

    @Test
    public void execute_validIndexPinnedPersonUnfilteredList_success() {
        // Tests successful execution of UnpinCommand using a valid index for a pinned person.
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(personToUnpin.isPinned());

        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person expectedUnpinnedPerson = personToUnpin.unpin();
        expectedModel.setPerson(personToUnpin, expectedUnpinnedPerson);
        expectedModel.updatePersonListSort();

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        // Tests execution of UnpinCommand with an out-of-bounds index;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexNotPinnedPerson_throwsCommandException() {
        // Tests execution of UnpinCommand on an already unpinned person.
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertFalse(secondPerson.isPinned());

        UnpinCommand unpinCommand = new UnpinCommand(INDEX_SECOND_PERSON);
        assertCommandFailure(unpinCommand, model, UnpinCommand.MESSAGE_PERSON_NOT_PINNED);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        // Tests execution of UnpinCommand using a valid index when the list is filtered.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertFalse(model.getFilteredPersonList().isEmpty());
        Person personToUnpin = model.getFilteredPersonList().get(0);
        assertTrue(personToUnpin.isPinned());
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin.getName());

        CommandResult commandResult = unpinCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Person personInAddressBookAfterUnpin = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.isSamePerson(personToUnpin)) // Find the actual person object
                .findFirst()
                .orElse(null);
        assertNotNull(personInAddressBookAfterUnpin);
        assertFalse(personInAddressBookAfterUnpin.isPinned());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Tests execution of UnpinCommand with an index that is valid in the unfiltered list
        // but out of bounds for the filtered list.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON; // Index 2
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // Tests the equals() method in UnpinCommand.
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        // Tests the toString() method in UnpinCommand.
        Index targetIndex = Index.fromOneBased(1);
        UnpinCommand unpinCommand = new UnpinCommand(targetIndex);
        String expected = UnpinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unpinCommand.toString());
    }
}
