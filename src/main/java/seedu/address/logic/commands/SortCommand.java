package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

/**
 * Sorts all persons in the address book by name in alphabetical order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted all persons by name";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedPersonList(Comparator.comparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
