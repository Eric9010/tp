package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindOrganisationCommand;
import seedu.address.model.person.OrganisationContainsKeywordsPredicate;

public class FindOrganisationCommandParserTest {

    private final FindOrganisationCommandParser parser = new FindOrganisationCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrganisationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindOrgCommand() {
        // no leading and trailing whitespaces
        FindOrganisationCommand expectedFindOrgCommand =
                new FindOrganisationCommand(
                        new OrganisationContainsKeywordsPredicate(Arrays.asList("Google", "Microsoft")));
        assertParseSuccess(parser, "Google Microsoft", expectedFindOrgCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Google \n \t Microsoft  \t", expectedFindOrgCommand);
    }
}
