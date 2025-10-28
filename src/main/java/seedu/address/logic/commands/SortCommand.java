package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Comparators.PIN_COMPARATOR;

import java.util.Comparator;

import seedu.address.logic.parser.SortType;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by name in alphabetical order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted person list by %s.";
    public static final String MESSAGE_USAGE =
            "sort: Sorts the person list by the given field.\n"
            + "Parameters: FIELD (name/timestamp)\n"
            + "Example: sort name";

    public final SortType sortType;

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
}
