package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PinCommand}.
 */
public class PinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        // Tests successful execution of PinCommand using a valid index.
        Person personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin.getName());

        CommandResult commandResult = pinCommand.execute(model);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Person pinnedPerson = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.isSamePerson(personToPin))
                .findFirst()
                .orElse(null);
        assertTrue(pinnedPerson.isPinned());
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws CommandException {
        // Tests successful execution of PinCommand using a valid name .
        Name targetName = BENSON.getName();
        Person personToPin = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(targetName))
                .findFirst()
                .orElse(null);
        PinCommand pinCommand = new PinCommand(targetName);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin.getName());

        CommandResult commandResult = pinCommand.execute(model);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Person personInAddressBookAfterPin = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.isSamePerson(personToPin))
                .findFirst()
                .orElse(null);
        assertTrue(personInAddressBookAfterPin.isPinned());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        // Tests execution of PinCommand with an out-of-bounds index.
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        // Tests execution of PinCommand with a name that does not exist.
        Name invalidName = new Name("Invalid Name Here");
        PinCommand pinCommand = new PinCommand(invalidName);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void execute_alreadyPinnedIndex_throwsCommandException() {
        // Tests execution of PinCommand targeting a person who is already pinned.
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person pinnedFirstPerson = firstPerson.pin();
        model.setPerson(firstPerson, pinnedFirstPerson);
        model.updatePersonListSort();

        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON); // Try to pin person again.
        assertCommandFailure(pinCommand, model, PinCommand.MESSAGE_PERSON_ALREADY_PINNED);
    }

    @Test
    public void execute_pinLimitReached_throwsCommandException() {
        // Tests execution of PinCommand while the pin limit of 3 has already been reached.
        Person p1 = model.getFilteredPersonList().get(0);
        Person p2 = model.getFilteredPersonList().get(1);
        Person p3 = model.getFilteredPersonList().get(2);
        model.setPerson(p1, p1.pin());
        model.setPerson(p2, p2.pin());
        model.setPerson(p3, p3.pin());
        model.updatePersonListSort();

        // Try to pin a 4th person.
        Index fourthIndex = Index.fromOneBased(4);
        PinCommand pinCommand = new PinCommand(fourthIndex);

        assertCommandFailure(pinCommand, model, ModelManager.MESSAGE_MAX_PINNED_PERSONS_REACHED);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        // Tests execution of PinCommand using a valid index when the list is filtered.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToPin = model.getFilteredPersonList().get(0);
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin.getName());

        CommandResult commandResult = pinCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Person personInAddressBookAfterPin = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.isSamePerson(personToPin))
                .findFirst()
                .orElse(null);
        assertTrue(personInAddressBookAfterPin.isPinned());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Tests execution of PinCommand with an index that is valid in the unfiltered list
        // but out of bounds for the filtered list.
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON; // Index 2
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        // Tests the equals() method in PinCommand.
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);
        PinCommand pinAliceCommand = new PinCommand(ALICE.getName());
        PinCommand pinBensonCommand = new PinCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        assertTrue(pinAliceCommand.equals(pinAliceCommand));

        // same values -> returns true
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_PERSON);
        assertTrue(pinFirstCommand.equals(pinFirstCommandCopy));
        PinCommand pinAliceCommandCopy = new PinCommand(ALICE.getName());
        assertTrue(pinAliceCommand.equals(pinAliceCommandCopy));

        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand.equals(null));
        assertFalse(pinAliceCommand.equals(null));

        // different person (by index) -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));

        // different person (by name) -> returns false
        assertFalse(pinAliceCommand.equals(pinBensonCommand));

        // different identifier type (index vs name) -> returns false
        assertFalse(pinFirstCommand.equals(pinAliceCommand));
    }

    @Test
    public void toStringMethod() {
        // Tests the toString() method in PinCommand.
        Index targetIndex = Index.fromOneBased(1);
        PinCommand pinCommandByIndex = new PinCommand(targetIndex);
        String expectedByIndex = PinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", targetName=null}";
        assertEquals(expectedByIndex, pinCommandByIndex.toString());

        Name targetName = ALICE.getName();
        PinCommand pinCommandByName = new PinCommand(targetName);
        String expectedByName = PinCommand.class.getCanonicalName() + "{targetIndex=null"
                + ", targetName=" + targetName + "}";
        assertEquals(expectedByName, pinCommandByName.toString());
    }
}
