package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FreeTimeCommand;

public class FreeTimeCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE);

    private FreeTimeCommandParser parser = new FreeTimeCommandParser();

    @Test
    public void parse_valid_success() {
        assertParseSuccess(parser, " h/6 d/2025-09-10", new FreeTimeCommand(6,
                LocalDate.of(2025, 9, 10)));
    }

    @Test
    public void parse_missingField_failure() {
        // missing hours
        assertParseFailure(parser, " d/2025-09-10", MESSAGE_INVALID_FORMAT);

        // missing date
        assertParseFailure(parser, " h/6", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicateField_failure() {
        // duplicate hours
        assertParseFailure(parser, " h/5 h/6 d/2025-09-10",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // duplicate date
        assertParseFailure(parser, " h/6 d/2025-09-10 d/2025-09-11",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_invalidHours_failure() {
        // non-integer
        assertParseFailure(parser, " h/some integer d/2025-09-10", MESSAGE_INVALID_FORMAT);

        // zero
        assertParseFailure(parser, " h/0 d/2025-09-10", MESSAGE_INVALID_FORMAT);

        // negative
        assertParseFailure(parser, " h/-1 d/2025-09-10", MESSAGE_INVALID_FORMAT);

        // more than 24
        assertParseFailure(parser, " h/25 d/2025-09-10", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        // wrong format
        assertParseFailure(parser, " h/6 d/10-09-2025", MESSAGE_INVALID_FORMAT);

        // given date does not exist
        assertParseFailure(parser, " h/6 d/2025-23-23", MESSAGE_INVALID_FORMAT);
    }
}
