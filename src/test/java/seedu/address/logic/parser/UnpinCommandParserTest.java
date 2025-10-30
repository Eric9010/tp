package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpinCommand;
import seedu.address.model.person.Name;

/**
 * As we are only doing parsing by index, our test cases focus only on index-related parse errors.
 */
public class UnpinCommandParserTest {

    private UnpinCommandParser parser = new UnpinCommandParser();

    @Test
    public void parse_validIndex_returnsUnpinCommand() {
        // Tests if parser correctly creates an UnpinCommand with Index given a valid integer.
        assertParseSuccess(parser, "1", new UnpinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validName_throwsParseException() {
        // Tests if parser correctly creates an UnpinCommand with Name given a valid name.
        assertParseSuccess(parser, " Alex Yeoh", new UnpinCommand(new Name("Alex Yeoh")));
    }

    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given '0' as input.
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnpinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNegative_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given a negative integer as input.
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnpinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given a empty string or whitespace as input.
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnpinCommand.MESSAGE_USAGE));
    }
}
