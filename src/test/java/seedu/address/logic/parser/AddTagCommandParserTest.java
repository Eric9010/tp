package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddTagCommandParserTest {

    private final AddTagsCommandParser parser = new AddTagsCommandParser();

    @Test
    public void parse_indexAndTags_success() throws Exception {
        AddTagsCommand command = parser.parse("1 friend colleague");
        assertTrue(command instanceof AddTagsCommand);
    }

    @Test
    public void parse_nameAndTags_success() throws Exception {
        AddTagsCommand command = parser.parse("Alex teammate");
        assertTrue(command instanceof AddTagsCommand);
    }

    @Test
    public void parse_noTags_failure() {
        assertParseFailure("1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
        assertParseFailure("Alex", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_failure() {
        assertParseFailure("  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
    }

    private void assertParseFailure(String input, String expectedMessage) {
        try {
            parser.parse(input);
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
            return;
        }
        throw new AssertionError("Expected ParseException not thrown for input: " + input);
    }
}

