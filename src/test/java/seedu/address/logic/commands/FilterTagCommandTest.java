package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

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
    public void parse_validArgs_returnsFilterTagCommand() throws Exception {
        FilterTagCommand command = FilterTagCommand.parse("friends colleagues");
        assertEquals(new FilterTagCommand(Arrays.asList("friends", "colleagues")), command);
    }

    @Test
    public void parse_emptyArgs_throwsException() {
        Exception e = assertThrows(Exception.class, () -> FilterTagCommand.parse("   "));
        assertEquals("Tag keywords cannot be empty!", e.getMessage());
    }

    @Test
    public void execute_singleTagMatch_success() {
        FilterTagCommand command = new FilterTagCommand(Arrays.asList("friends"));

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
        FilterTagCommand command = new FilterTagCommand(Arrays.asList("nonexistentTag"));

        expectedModel.updateFilteredPersonList(person -> false);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        FilterTagCommand firstCommand = new FilterTagCommand(Arrays.asList("friends"));
        FilterTagCommand sameCommand = new FilterTagCommand(Arrays.asList("friends"));
        FilterTagCommand differentCommand = new FilterTagCommand(Arrays.asList("colleagues"));

        assertEquals(firstCommand, sameCommand);
        assertEquals(firstCommand, firstCommand);
        assertNotEquals(firstCommand, null);
        assertNotEquals(firstCommand, 1);
        assertNotEquals(firstCommand, differentCommand);
    }


    @Test
    public void toStringMethod() {
        FilterTagCommand command = new FilterTagCommand(Arrays.asList("friends"));
        String expected = FilterTagCommand.class.getCanonicalName() + "{tagKeywords=" + "[friends]" + "}";
        assertEquals(expected, command.toString());
    }
}
