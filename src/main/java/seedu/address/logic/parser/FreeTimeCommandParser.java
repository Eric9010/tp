package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.commands.FreeTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FreeTimeCommand object.
 */
public class FreeTimeCommandParser implements Parser<FreeTimeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FreeTimeCommand
     * and returns an FreeTimeCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FreeTimeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HOURS, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_HOURS, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOURS, PREFIX_DATE);

        int hours;
        LocalDate date;

        try {
            hours = Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_HOURS).get());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get(), formatter);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        }

        if (hours <= 0 || hours > 16) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeTimeCommand.MESSAGE_USAGE));
        }

        return new FreeTimeCommand(hours, date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
