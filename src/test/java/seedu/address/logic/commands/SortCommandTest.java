package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Comparators.NAME_COMPARATOR;
import static seedu.address.model.Comparators.TIMESTAMP_COMPARATOR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookUnsorted;

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
}
