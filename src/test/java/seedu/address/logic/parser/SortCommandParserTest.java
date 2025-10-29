package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_name_success() {
        String userInput = "name";
        SortCommand expectedCommand = new SortCommand(SortType.NAME);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_timestamp_success() {
        String userInput = "timestamp";
        SortCommand expectedCommand = new SortCommand(SortType.TIMESTAMP);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, SortCommand.COMMAND_WORD, expectedMessage);
    }
}
