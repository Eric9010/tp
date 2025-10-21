package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.util.Comparator;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unsortedList_success() {
        SortCommand sortCommand = new SortCommand();

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(Comparator.comparing(person -> person.getName().fullName,
                String.CASE_INSENSITIVE_ORDER));

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}
