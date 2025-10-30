package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Pins a person identified using its displayed index or name to the top of the address book.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number or by their full name.\n"
            + "Pinned contacts move to the top of the list, up to a maximum of 3.\n"
            + "Parameters: INDEX (must be a positive integer) OR NAME\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " Alex Yeoh";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_PINNED = "This person is already pinned.";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates a PinCommand to pin the person at the specified {@code targetIndex}.
     */
    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a PinCommand to pin the person with the specified {@code targetName}.
     */
    public PinCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToPin;

        if (targetIndex != null) {
            // Logic for finding by index.
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToPin = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // Logic for finding by name.
            personToPin = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst()
                    .orElse(null);

            if (personToPin == null) {
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
            }
        }

        if (personToPin.isPinned()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_PINNED);
        }

        model.pinPerson(personToPin);
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PinCommand)) {
            return false;
        }
        PinCommand otherPinCommand = (PinCommand) other;
        return Objects.equals(targetIndex, otherPinCommand.targetIndex)
                && Objects.equals(targetName, otherPinCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();
    }
}
