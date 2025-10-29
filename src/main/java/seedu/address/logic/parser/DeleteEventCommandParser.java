package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteEventCommand object.
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] indices = args.trim().split(" ");

        if (indices.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }

        Index recruiterIndex;
        Index eventIndex;

        try {
            recruiterIndex = ParserUtil.parseIndex(indices[0]);
            eventIndex = ParserUtil.parseIndex(indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE),
                    pe);
        }

        return new DeleteEventCommand(recruiterIndex, eventIndex);
    }
}
