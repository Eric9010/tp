package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Event;

/**
 * Parses input arguments and creates a new EventCommand object.
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand
     * and returns an EventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_START, PREFIX_END,
                PREFIX_MODE, PREFIX_LOCATION, PREFIX_REMARKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_START, PREFIX_END, PREFIX_MODE, PREFIX_LOCATION,
                PREFIX_REMARKS);

        String title = argMultimap.getValue(PREFIX_TITLE).get();
        String start = argMultimap.getValue(PREFIX_START).get();
        String end = argMultimap.getValue(PREFIX_END).get();
        String mode = null;
        String location = null;
        String remarks = null;

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            mode = argMultimap.getValue(PREFIX_MODE).get();
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            location = argMultimap.getValue(PREFIX_LOCATION).get();
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            remarks = argMultimap.getValue(PREFIX_REMARKS).get();
        }

        Event event;
        try {
            event = new Event(title, start, end, mode, location, remarks);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        return new EventCommand(index, event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
