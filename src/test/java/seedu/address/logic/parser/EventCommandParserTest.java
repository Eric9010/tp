package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EventCommand;
import seedu.address.model.person.Event;

public class EventCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE);

    private EventCommandParser parser = new EventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event event = new Event("Google Interview", "2025-09-10", "15:50", "f2f",
                "Google Headquarters", "Final Round");
        assertParseSuccess(parser, "1 e/Google Interview d/2025-09-10 t/15:50 m/f2f l/Google Headquarters "
                + "r/Final Round", new EventCommand(Index.fromOneBased(1), event));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no optional fields
        Event event = new Event("Google Interview", "2025-09-10", "15:50", null, null,
                null);
        assertParseSuccess(parser, "1 e/Google Interview d/2025-09-10 t/15:50",
                new EventCommand(Index.fromOneBased(1), event));

        // no mode
        event = new Event("Google Interview", "2025-09-10", "15:50", null,
                "Google Headquarters", "Final Round");
        assertParseSuccess(parser, "1 e/Google Interview d/2025-09-10 t/15:50 l/Google Headquarters "
                + "r/Final Round", new EventCommand(Index.fromOneBased(1), event));

        // no location
        event = new Event("Google Interview", "2025-09-10", "15:50", "f2f", null,
                "Final Round");
        assertParseSuccess(parser, "1 e/Google Interview d/2025-09-10 t/15:50 m/f2f r/Final Round ",
                new EventCommand(Index.fromOneBased(1), event));

        // no remarks
        event = new Event("Google Interview", "2025-09-10", "15:50", "f2f",
                "Google Headquarters", null);
        assertParseSuccess(parser, "1 e/Google Interview d/2025-09-10 t/15:50 m/f2f l/Google Headquarters ",
                new EventCommand(Index.fromOneBased(1), event));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // no index specified
        assertParseFailure(parser, "e/Google Interview d/2025-08-15 t/15:00", MESSAGE_INVALID_FORMAT);

        // no event name specified
        assertParseFailure(parser, "1 d/2025-08-15 t/15:00", MESSAGE_INVALID_FORMAT);

        // no date specified
        assertParseFailure(parser, "1 e/Google Interview t/15:00", MESSAGE_INVALID_FORMAT);

        // no time specified
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicatedField_failure() {
        // duplicated event name
        assertParseFailure(parser, "1 e/Google Interview e/Google Interview d/2025-08-15 t/15:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));

        // duplicated date
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15 d/2025-08-15 t/15:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // duplicated time
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15 t/15:00 t/15:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // duplicated mode
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15 t/15:00 m/f2f m/zoom",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODE));

        // duplicated location
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15 t/15:00 l/Kent Ridge l/Jurong",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // duplicated remark
        assertParseFailure(parser, "1 e/Google Interview d/2025-08-15 t/15:00 r/Remark 1 r/Remark 2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARKS));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // zero index
        assertParseFailure(parser, "0 e/Google Interview d/2025-08-15 t/15:00", MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, "-2 e/Google Interview d/2025-08-15 t/15:00", MESSAGE_INVALID_FORMAT);

        // non-integer index
        assertParseFailure(parser, "some index e/Google Interview d/2025-08-15 t/15:00",
                MESSAGE_INVALID_FORMAT);
    }
}
