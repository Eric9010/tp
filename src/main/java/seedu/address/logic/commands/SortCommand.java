package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.ModelManager.PIN_COMPARATOR;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by name in alphabetical order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted all persons by name";

    public static final Comparator<Person> NAME_COMPARATOR =
            Comparator.comparing(person -> person.getName().fullName.toLowerCase());

    public static final Comparator<Person> TIMESTAMP_COMPARATOR =
            Comparator.comparing(Person::getDateAdded);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> combinedComparator =
                PIN_COMPARATOR.thenComparing(secondaryComparator);
        model.updateSortedPersonList(combinedComparator);

        model.updateSortedPersonList(Comparator.comparing(person ->
                person.getName().fullName, String.CASE_INSENSITIVE_ORDER));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
