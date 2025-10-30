package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
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
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", "f2f",
                "Google Headquarters", "H");
        assertParseSuccess(parser, "1 t/Google Interview s/2025-09-10 15:00 e/2025-09-10 15:50 m/f2f "
                + "l/Google Headquarters pr/H", new EventCommand(Index.fromOneBased(1), event));
    }


    @Test
    public void parse_optionalFieldsMissing_success() {
        // no optional fields
        Event event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", null,
                null, null);
        assertParseSuccess(parser, "1 t/Google Interview s/2025-09-10 15:00 e/2025-09-10 15:50",
                new EventCommand(Index.fromOneBased(1), event));

        // no mode
        event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", null,
                "Google Headquarters", null);
        assertParseSuccess(parser, "1 t/Google Interview s/2025-09-10 15:00 e/2025-09-10 15:50 "
                + "l/Google Headquarters", new EventCommand(Index.fromOneBased(1), event));

        // no location
        event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", "f2f",
                null, null);
        assertParseSuccess(parser, "1 t/Google Interview s/2025-09-10 15:00 e/2025-09-10 15:50 m/f2f",
                new EventCommand(Index.fromOneBased(1), event));

        // no priority
        event = new Event("Google Interview", "2025-09-10 15:00", "2025-09-10 15:50", "f2f",
                "Google Headquarters", null); // priority is null
        assertParseSuccess(parser, "1 t/Google Interview s/2025-09-10 15:00 e/2025-09-10 15:50 m/f2f "
                + "l/Google Headquarters", new EventCommand(Index.fromOneBased(1), event));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // no index specified
        assertParseFailure(parser, "t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00", MESSAGE_INVALID_FORMAT);

        // no title specified
        assertParseFailure(parser, "1 s/2025-08-15 15:00 e/2025-08-15 15:00", MESSAGE_INVALID_FORMAT);

        // no start specified
        assertParseFailure(parser, "1 t/Google Interview e/2025-08-15 15:00", MESSAGE_INVALID_FORMAT);

        // no end specified
        assertParseFailure(parser, "1 t/Google Interview s/2025-08-15 15:00", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicatedField_failure() {
        // duplicated event name
        assertParseFailure(parser, "1 t/Google Interview t/Google Interview s/2025-08-15 15:00 "
                + "e/2025-08-15 15:00", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TITLE));

        // duplicated date
        assertParseFailure(parser, "1 t/Google Interview s/2025-08-15 15:00 s/2025-08-15 15:00 "
                + "e/2025-08-15 15:00", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START));

        // duplicated time
        assertParseFailure(parser, "1 t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00 "
                + "e/2025-08-15 15:00", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_END));

        // duplicated mode
        assertParseFailure(parser, "1 t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00 m/f2f m/zoom",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODE));

        // duplicated location
        assertParseFailure(parser, "1 t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00 l/Kent Ridge "
                + "l/Jurong", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // zero index
        assertParseFailure(parser, "0 t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00",
                MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, "-2 t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00",
                MESSAGE_INVALID_FORMAT);

        // non-integer index
        assertParseFailure(parser, "some index t/Google Interview s/2025-08-15 15:00 e/2025-08-15 15:00",
                MESSAGE_INVALID_FORMAT);
    }
}
