package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterTagCommand;

/**
 * Unit tests for {@code FilterTagCommandParser}.
 */
public class FilterTagCommandParserTest {

    private final FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_validArgs_returnsFilterTagCommand() {
        // single tag
        assertParseSuccess(parser, "friends",
                new FilterTagCommand(Arrays.asList("friends")));

        // multiple tags
        assertParseSuccess(parser, "friends colleagues",
                new FilterTagCommand(Arrays.asList("friends", "colleagues")));

        // case-insensitive test
        assertParseSuccess(parser, "FrIeNdS",
                new FilterTagCommand(Arrays.asList("FrIeNdS")));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void equals() {
        FilterTagCommandParser parser1 = new FilterTagCommandParser();
        FilterTagCommandParser parser2 = new FilterTagCommandParser();

        // same object -> true
        assertTrue(parser1.equals(parser1));

        // different object, same type -> false (since no state but different instance)
        assertFalse(parser1.equals(parser2));

        // null -> false
        assertFalse(parser1.equals(null));

        // different type -> false
        assertFalse(parser1.equals("some string"));
    }
}
