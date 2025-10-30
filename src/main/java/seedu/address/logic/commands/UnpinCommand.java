package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Unpins a person identified using its displayed index from the address book.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the person identified by the index number or name.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME\n"
            + "Example: " + COMMAND_WORD + " 1"
            + "Example: " + COMMAND_WORD + " Alex Yeoh";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_PINNED = "This person is not pinned.";

    private final Index targetIndex;
    private final Name targetName;

    /**
     * Creates an UnpinCommand to unpin the person at the specified {@code targetIndex}.
     */
    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates an UnpinCommand to unpin the person with the specified {@code targetName}.
     */
    public UnpinCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToUnpin;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToUnpin = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personToUnpin = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst()
                    .orElse(null);

            if (personToUnpin == null) {
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
            }
        }

        if (!personToUnpin.isPinned()) {
            throw new CommandException(MESSAGE_PERSON_NOT_PINNED);
        }

        model.unpinPerson(personToUnpin);
        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnpinCommand)) {
            return false;
        }
        UnpinCommand otherUnpinCommand = (UnpinCommand) other;

        if (targetIndex == null) {
            return targetName.equals(otherUnpinCommand.targetName);
        }
        return targetIndex.equals(otherUnpinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
