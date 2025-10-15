package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewAllCommand extends Command {
    public static final String COMMAND_WORD = "viewall";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all persons in the address book with complete details.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Viewing all persons:\n\n";

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
            output.append("No contacts found in address book.");
        } else {
            output.append(String.format("Total contacts: %d\n\n", persons.size()));

            int index = 1;
            for (Person person : persons) {
                output.append(String.format("%d. Name: %s\n", index, person.getName()));
                output.append(String.format("   Phone: %s\n", person.getPhone()));
                output.append(String.format("   Email: %s\n", person.getEmail()));
                output.append(String.format("   Address: %s\n", person.getAddress()));

                //tags
                output.append("   Tags: ");
                person.getTags().forEach(tag -> output.append(tag).append(" "));
                output.append("\n");

                //events 
                if (person.getEvents() != null && !person.getEvents().isEmpty()) {
                    output.append("   Events:\n");
                    int eventIndex = 1;
                    for (Event event : person.getEvents()) {
                        output.append(String.format("      %d) %s\n", eventIndex, event));
                        eventIndex++;
                    }
                } else {
                    output.append("   Events: (none)\n");
                }
                output.append("\n\n");
                index++;
            }
        }

        return new CommandResult(output.toString());
    }

    @Override
    public boolean equals(Object other) {
        // All ViewAllCommand instances are equal
        return other == this || (other instanceof ViewAllCommand);
    }
}

