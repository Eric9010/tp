package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PinCommand;
import seedu.address.model.person.Name;

public class PinCommandParserTest {

    private PinCommandParser parser = new PinCommandParser();

    @Test
    public void parse_validArgs_returnsPinCommandByIndex() {
        // Tests if parser correctly creates a PinCommand with Index given a valid integer.
        assertParseSuccess(parser, "1", new PinCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgs_returnsPinCommandByName() {
        // Tests if parser correctly creates a PinCommand with Name given a valid name.
        Name targetName = ALICE.getName();
        assertParseSuccess(parser, targetName.fullName, new PinCommand(targetName));
    }

    @Test
    public void parse_invalidArgsIndexZero_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given "0" as input.
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNegativeIndex_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given a negative integer as input.
        assertParseFailure(parser, "-5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNonIntegerIndexOrName_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given a non-integer input.
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsNameFormat_throwsParseException() {
        // Tests if parser correctly throws a ParseException when given a name with a symbol as input.
        assertParseFailure(parser, "Alex@Yeoh", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PinCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Tests if parser correctly throws a ParseException when an empty string or whitespace as input.
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PinCommand.MESSAGE_USAGE));
    }
}
