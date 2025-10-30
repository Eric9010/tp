package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Comparators.NAME_COMPARATOR;
import static seedu.address.model.Comparators.PIN_COMPARATOR;
import static seedu.address.model.Comparators.TIMESTAMP_COMPARATOR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookUnsorted;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPins;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookUnsorted(), new UserPrefs());

    @Test
    public void execute_sortByName_success() {
        SortCommand sortCommand = new SortCommand(SortType.NAME);

        String expectedMessage = String.format(String.format(SortCommand.MESSAGE_SUCCESS,
                                                SortType.NAME.name().toLowerCase()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(NAME_COMPARATOR);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByTimeStamp_success() {
        SortCommand sortCommand = new SortCommand(SortType.TIMESTAMP);

        String expectedMessage = String.format(String.format(SortCommand.MESSAGE_SUCCESS,
                SortType.TIMESTAMP.name().toLowerCase()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(TIMESTAMP_COMPARATOR);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sort_preservesPinnedOrder() {
        ModelManager pinTestModel = new ModelManager(getTypicalAddressBookWithPins(), new UserPrefs());

        SortCommand sortCommand = new SortCommand(SortType.NAME);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBookWithPins(), new UserPrefs());
        expectedModel.updateSortedPersonList(PIN_COMPARATOR.thenComparing(NAME_COMPARATOR));

        assertCommandSuccess(sortCommand, pinTestModel, String.format(SortCommand.MESSAGE_SUCCESS, "name"),
                expectedModel);
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(SortType.NAME);
        SortCommand sortSecondCommand = new SortCommand(SortType.TIMESTAMP);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(SortType.NAME);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }
}
