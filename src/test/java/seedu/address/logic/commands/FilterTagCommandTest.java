package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration and unit tests for {@code FilterTagCommand}.
 */
public class FilterTagCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_singleTagMatch_success() {
        FilterTagCommand command = new FilterTagCommand(Collections.singletonList("friends"));

        expectedModel.updateFilteredPersonList(person ->
                person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase("friends")));

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleTagsMatch_success() {
        FilterTagCommand command = new FilterTagCommand(Arrays.asList("owesMoney", "friends"));

        expectedModel.updateFilteredPersonList(person ->
                person.getTags().stream().anyMatch(tag ->
                        tag.tagName.equalsIgnoreCase("owesMoney") || tag.tagName.equalsIgnoreCase("friends")));

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatch_returnsEmptyList() {
        FilterTagCommand command = new FilterTagCommand(Collections.singletonList("nonexistentTag"));

        expectedModel.updateFilteredPersonList(person -> false);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        FilterTagCommand firstCommand = new FilterTagCommand(Collections.singletonList("friends"));
        FilterTagCommand sameCommand = new FilterTagCommand(Collections.singletonList("friends"));
        FilterTagCommand differentCommand = new FilterTagCommand(Arrays.asList("colleagues"));

        // same values → true
        assertEquals(firstCommand, sameCommand);

        // same object → true
        assertEquals(firstCommand, firstCommand);

        // null → false
        assertNotEquals(null, firstCommand);

        // different type → false
        assertNotEquals(1, firstCommand);

        // different keywords → false
        assertNotEquals(firstCommand, differentCommand);
    }


    @Test
    public void toStringMethod() {
        FilterTagCommand command = new FilterTagCommand(Collections.singletonList("friends"));
        String expected = FilterTagCommand.class.getCanonicalName() + "{tagKeywords=" + "[friends]" + "}";
        assertEquals(expected, command.toString());
    }
}
