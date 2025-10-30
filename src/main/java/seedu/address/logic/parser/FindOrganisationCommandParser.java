package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindOrganisationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OrganisationContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOrganisationCommand object
 */
public class FindOrganisationCommandParser implements Parser<FindOrganisationCommand> {
    @Override
    public FindOrganisationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrganisationCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        return new FindOrganisationCommand(new OrganisationContainsKeywordsPredicate(Arrays.asList(keywords)));
    }
}
