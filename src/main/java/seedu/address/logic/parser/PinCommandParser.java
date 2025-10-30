package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new PinCommand object
 */
public class PinCommandParser implements Parser<PinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PinCommand
     * and returns a PinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
        }

        try {
            // Parse as an index first.
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new PinCommand(index);
        } catch (ParseException peIndex) {
            // If parsing as an index fails, check if number was invalid.
            if (trimmedArgs.matches("-?\\d+")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE), peIndex);
            }

            // Otherwise, parse it as a name.
            try {
                Name name = ParserUtil.parseName(trimmedArgs);
                return new PinCommand(name);
            } catch (ParseException peName) {
                // If both fail, throw invalid command format error.
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE), peName);
            }
        }
    }
}
