package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventCommand;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class DeleteEventCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validIndices_success() {
        assertParseSuccess(parser, " 1 2", new DeleteEventCommand(Index.fromOneBased(1),
                Index.fromOneBased(2)));
    }

    @Test
    public void parse_oneIndex_failure() {
        assertParseFailure(parser, " 1 ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_threeIndices_failure() {
        assertParseFailure(parser, " 1 2 3", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_extraInput_failure() {
        assertParseFailure(parser, " 1 2 index", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidRecruiterIndex_failure() {
        // non-integer
        assertParseFailure(parser, " index 2", MESSAGE_INVALID_FORMAT);

        // zero
        assertParseFailure(parser, " 0 2", MESSAGE_INVALID_FORMAT);

        // negative
        assertParseFailure(parser, " -1 2", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidEventIndex_failure() {
        // non-integer
        assertParseFailure(parser, " 2 index", MESSAGE_INVALID_FORMAT);

        // zero
        assertParseFailure(parser, " 2 0", MESSAGE_INVALID_FORMAT);

        // negative
        assertParseFailure(parser, " 2 -1", MESSAGE_INVALID_FORMAT);
    }
}
