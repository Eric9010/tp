package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Comparators.PIN_COMPARATOR;

import java.util.Comparator;

import seedu.address.logic.parser.SortType;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all recruiters in the address book by alphabetical order or time added.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted contact list by %s.";
    public static final String MESSAGE_USAGE = "Try:\n" + COMMAND_WORD
            + " name (sort by name in alphabetical order)\n"
            + COMMAND_WORD + " timestamp (sort in order added)";

    public final SortType sortType;

    /**
     * Creates an SortCommand with the specified {@code sortType}.
     */
    public SortCommand(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> combinedComparator =
                PIN_COMPARATOR.thenComparing(sortType.getComparator());

        model.updateSortedPersonList(combinedComparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortType.name().toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortType.equals(otherSortCommand.sortType);
    }
}
