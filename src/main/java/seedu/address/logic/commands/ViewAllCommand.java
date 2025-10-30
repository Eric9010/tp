package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all recruiters in the address book to the user.
 */
public class ViewAllCommand extends Command {
    public static final String COMMAND_WORD = "viewall";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all recruiters in the address book with complete details.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Viewing all recruiters\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Update filter to show all persons
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Get all persons
        ObservableList<Person> persons = model.getFilteredPersonList();

        // Build detailed output message
        StringBuilder output = new StringBuilder(MESSAGE_SUCCESS);

        if (persons.isEmpty()) {
            output.append("No contacts found in CareerConnect.");
        } else {
            output.append(String.format("Total contacts: %d", persons.size()));
        }

        return new CommandResult(output.toString());
    }

    @Override
    public boolean equals(Object other) {
        // All ViewAllCommand instances are equal
        return other == this || (other instanceof ViewAllCommand);
    }
}

